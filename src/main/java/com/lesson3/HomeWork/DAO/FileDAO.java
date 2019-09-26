package com.lesson3.HomeWork.DAO;

import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDAO implements DAO<File> {

    private SessionFactory sessionFactory;
//    private File file;


    private SessionFactory createSessionFactory() {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory;
    }

    @Override
    public File save(File fileToSave) throws HibernateException {
        Transaction transaction = null;

        try (Session session = createSessionFactory().openSession()) {

            transaction = session.getTransaction();

            transaction.begin();


            session.save(fileToSave);


            transaction.commit();

            System.out.println("File has been saved");

        } catch (HibernateException e) {
            System.err.println("Can't save file");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();

        }


        return fileToSave;
    }

    @Override
    public void delete(long id) throws HibernateException {

        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            File file = findById(id);
            session.delete(file);

            System.out.println("File with id " + id + " has been deleted");
            tr.commit();

        } catch (HibernateException e) {
            System.err.println("Can't delete file with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }

    }

    @Override
    public File update(File fileToUpdate, long id) throws HibernateException {

        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE FILES SET FILENAME = ?, FILEFORMAT = ?, FILESIZE = ? WHERE FILEID = ?");
            query.setParameter(1, fileToUpdate.getName());
            query.setParameter(2, fileToUpdate.getFormat());
            query.setParameter(3, fileToUpdate.getSize());
            query.setParameter(4, id);

            int res = query.executeUpdate();

            System.out.println("File " + id + " has been updated with result " + res);

            tr.commit();

        } catch (HibernateException e) {
            System.err.println("Can't update file with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


        return fileToUpdate;
    }

    @Override
    public File findById(long id) {

        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            File file = session.get(File.class, id);

            tr.commit();

            return file;

        } catch (HibernateException e) {
            System.err.println("Can't find file with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


        return null;
    }

    public void put(Storage storage, File file) {

        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE FILES SET STORAGE_ID = ? WHERE FILEID = ?");
            query.setParameter(1, storage.getId());
            query.setParameter(2, file.getId());


            int res = query.executeUpdate();


            tr.commit();

            System.out.println("File " + file.getId() + " has been put to storage " + storage.getId() + " with result " + res);

        } catch (HibernateException e) {
            System.err.println("Can't put file  " + file.getId() + " to storage " + storage.getId());
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }


    }

    public void deleteFromStorage(Storage storage, File file) {

        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE FILES SET STORAGE_ID = NULL WHERE FILEID = ?");
            query.setParameter(1, file.getId());


            int res = query.executeUpdate();


            tr.commit();

            System.out.println("File " + file.getId() + " has been deleted from storage " + storage.getId() + " with result " + res);

        } catch (HibernateException e) {
            System.err.println("Can't delete file  " + file.getId() + " from storage " + storage.getId());
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }

    }

    public void transferFile(Storage storageTo, long fileID){

        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE FILES SET STORAGE_ID = ? WHERE FILEID = ?");
            query.setParameter(1, storageTo.getId());
            query.setParameter(2, fileID);


            int res = query.executeUpdate();


            tr.commit();

            System.out.println("File " + fileID + " has been put to storage " + storageTo.getId() + " with result " + res);

        } catch (HibernateException e) {
            System.err.println("Can't transfer file  " + fileID + " to storage " + storageTo.getId());
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }

    }

    public void transferAll(Storage storageFrom, Storage storageTo) {

        Transaction tr = null;


        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE FILES SET STORAGE_ID = ? WHERE STORAGE_ID = ?").addEntity(File.class);
            query.setParameter(1, storageTo.getId());
            query.setParameter(2, storageFrom.getId());

            int res = query.executeUpdate();



            tr.commit();

            System.out.println("Transferred from storage " + storageFrom.getId() + " to storage " + storageTo.getId() + " with result " + res);

        } catch (HibernateException e) {
            System.err.println("Can not transfer files from storage " + storageFrom.getId() + " to storage " + storageTo.getId());
            e.printStackTrace();
            if (tr != null) tr.rollback();


        }



    }
}
