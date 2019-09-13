package com.hw2;

import com.lesson3.HomeWork.model.NullFieldsException;
import com.lesson3.HomeWork.model.WrongIdException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemService {


    ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    private List<Item> items = new ArrayList<>();

    Item save(Item item) throws Exception {


        if (!isItemExists(item) && !isNullFields(item)) return itemDAO.save(item);
        else if (isItemExists(item)) throw new Exception ("Item " + item.getName() + " has been already saved");
        else throw new Exception ("Item " + item.getName() + " contains null fields");

    }

    void delete(long id) throws Exception {

        Item item = itemDAO.findById(id);

        if (isItemExists(item)) itemDAO.delete(id);
        else throw new Exception("There's no item with id " + id);
    }

    Item findById(long id) throws Exception {

        if (isIdExists(id)) return itemDAO.findById(id);
        else throw new Exception ("There's no item with id " + id);
    }

    Item update(Item item, long id) throws Exception {


        if (!isItemExists(item) && !isNullFields(item)) return itemDAO.update(item, id);

        else if (isItemExists(item))
            throw new Exception("There's no item " + item.getName());

        else throw new Exception("Item " + item.getName() + " contains null fields");
    }

    boolean isItemExists(Item item) {

        Transaction tr = null;


        try (Session session = itemDAO.getSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("SELECT * FROM ITEM").addEntity(Item.class);
            items = query.getResultList();

            int index = 0;
            for (Item itemFromDb : items) {

                if (itemFromDb.equals(item)) index++;

            }

            if (index > 0) return true;

            tr.commit();


        } catch (HibernateException e) {
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


        return false;
    }

    boolean isNullFields(Item item) {

        return (item.getDateCreated() == null || item.getLastUpdatedDate() == null || item.getName() == null || item.getDescription() == null);
    }

    boolean isIdExists(long id) {


        Transaction tr = null;

        try (Session session = itemDAO.getSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("SELECT ID FROM ITEM");
            List<BigDecimal> ids = query.getResultList();

            for (BigDecimal idFromDB : ids) {

                if (idFromDB.longValue() == id) return true;

            }


            tr.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


        return false;

    }

}
