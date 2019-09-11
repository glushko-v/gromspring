package com.lesson3.HomeWork.DAO;

import com.lesson3.HomeWork.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;


public class StorageDAO implements DAO<Storage> {

    private SessionFactory sessionFactory;
    private Storage storage;


    private SessionFactory createSessionFactory() {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory;
    }

    @Override
    public Storage save(Storage storage) throws HibernateException {

        Transaction transaction = null;

        try (Session session = createSessionFactory().openSession()) {

            transaction = session.getTransaction();

            transaction.begin();


            session.save(storage);




            transaction.commit();

        } catch (HibernateException e) {
            System.err.println("Can't save storage");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();

        }

        System.out.println("Storage has been saved");

        return storage;
    }

    @Override
    public void delete(long id) throws HibernateException {


        Transaction tr = null;


        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            storage = findById(id);
            session.delete(storage);



            tr.commit();

        } catch (HibernateException e) {
            System.err.println("Can't delete storage with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }

        System.out.println("Storage with id " + id + " has been deleted");
    }

    @Override
    public Storage update(Storage storage, long id) throws HibernateException {

        Transaction transaction = null;
        int res = 0;

        try(Session session = createSessionFactory().openSession()){

            transaction = session.getTransaction();

            transaction.begin();

            Query query = session.createSQLQuery("UPDATE STORAGES SET FORMATSSUPPORTED = ?, STORAGECOUNTRY = ?, STORAGEMAXSIZE = ? WHERE STORAGEID = ?");
            query.setParameter(1, storage.getFormatsSupported());
            query.setParameter(2, storage.getStorageCountry());
            query.setParameter(3, storage.getStorageSize());
            query.setParameter(4, id);

            res = query.executeUpdate();



            transaction.commit();

        }catch (HibernateException e){
            System.err.println("Can't update storage with id + " + id);
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }

        System.out.println("Update of storage " + id + " completed with result " + res);

        return storage;
    }

    @Override
    public Storage findById(long id) throws HibernateException {

        Transaction tr = null;


        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            storage = session.get(Storage.class, id);

            tr.commit();

        } catch (HibernateException e) {
            System.err.println("Can't find storage with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


        return storage;
    }
}
