package com.lesson3.HomeWork.Service;


import com.lesson3.HomeWork.DAO.StorageDAO;
import com.lesson3.HomeWork.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class StorageService implements Service<Storage> {


    private SessionFactory sessionFactory;
    private StorageDAO storageDAO = new StorageDAO();


    private SessionFactory createSessionFactory() {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory;
    }


    @Override
    public Storage save(Storage storage) throws Exception {

        if (!isNullFields(storage)) return storageDAO.save(storage);
        else throw new Exception("Storage contains null fields");
    }

    @Override
    public void delete(long id) throws Exception {

        if (isIdExists(id)) storageDAO.delete(id);
        else throw new Exception("There's no storage with ID " + id);

    }

    @Override
    public Storage update(Storage storage, long id) throws Exception {

        if (isIdExists(id) && !isNullFields(storage)) return storageDAO.update(storage, id);

        else if (isNullFields(storage)) throw new Exception("Storage contains null fields");
        else throw new Exception("There's no storage with ID " + id);
    }

    @Override
    public Storage findById(long id) throws Exception {

        if (isIdExists(id)) return storageDAO.findById(id);

        else throw new Exception("There's no storage with ID " + id);
    }

    private boolean isNullFields(Storage storage) { //true если есть пустые поля

        return (storage.getFormatsSupported() == null || storage.getStorageCountry() == null || storage.getStorageSize() == 0);

    }


    private boolean isIdExists(long id) { //true если id есть в базе

        Transaction tr = null;
        int count = 0;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("SELECT STORAGEID FROM STORAGES");
            List<BigDecimal> ids = query.getResultList();

            for (BigDecimal idFromDb : ids) {
                if (idFromDb.longValue() == id) count++;
            }


            tr.commit();

        } catch (HibernateException e) {
            System.err.println("Id " + id + " can not be checked");
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


        return count > 0;
    }


}
