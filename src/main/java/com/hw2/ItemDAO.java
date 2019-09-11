package com.hw2;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;

public class ItemDAO {

    SessionFactory sessionFactory;

    SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }

    public Item save(Item item)  {

        Transaction tr = null;

        try (Session session = getSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            session.save(item);

            tr.commit();
            System.out.println("Saved");

        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Save is failed");
            if (tr != null) tr.rollback();
        }


        return item;
    }

    public Item findById(long id) {

        Transaction tr = null;
        Item item = null;

        try (Session session = getSessionFactory().openSession()) {


            tr = session.getTransaction();
            tr.begin();

            item = session.get(Item.class, id);


            tr.commit();



        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("Error");
            if (tr != null) tr.rollback();
        }

        return item;
    }

    public void delete(long id){

        Transaction tr = null;

        try(Session session = getSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            Item item = findById(id);
            session.delete(item);

            tr.commit();
            System.out.println("Deleted");

        } catch (HibernateException e){
            e.printStackTrace();
            System.out.println("Delete is failed");
            if (tr!= null) tr.rollback();
        }
    }

    public Item update(Item item, long id) {

        Transaction tr = null;

        try (Session session = getSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE ITEM SET NAME = ?, DATE_CREATED = ?, DATE_UPDATED = ?, DESCRIPTION = ? WHERE ID =?");
            query.setParameter(1, item.getName());
            query.setParameter(2, item.getDateCreated());
            query.setParameter(3, item.getLastUpdatedDate());
            query.setParameter(4, item.getDescription());
            query.setParameter(5, id);

            int res = query.executeUpdate();


            tr.commit();
            System.out.println("Updated with result " + res);

        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("Update is failed");
            if (tr != null) tr.rollback();
        }


        return item;
    }

}
