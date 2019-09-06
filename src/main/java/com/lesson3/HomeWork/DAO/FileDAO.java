package com.lesson3.HomeWork.DAO;

import com.lesson3.HomeWork.model.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;

public class FileDAO implements DAO<File> {

    private SessionFactory sessionFactory;
    private File file;


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


            session.save(file);

            System.out.println("File has been saved");


            transaction.commit();

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

        try(Session session = createSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            file = findById(id);
            session.delete(file);

            System.out.println("File with id " + id + " has been deleted");
            tr.commit();

        } catch (HibernateException e){
            System.err.println("Can't delete file with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }

    }

    @Override
    public File update(File fileToUpdate, long id) throws HibernateException {

        Transaction tr = null;

        try(Session session = createSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("UPDATE FILES SET NAME=?, FILEFORMAT = ?, FILESIZE = ? WHERE FILEID = ?");
            query.setParameter(1, fileToUpdate.getName());
            query.setParameter(2, fileToUpdate.getFormat());
            query.setParameter(3, fileToUpdate.getSize());
            query.setParameter(4, id);

            int res = query.executeUpdate();

            System.out.println("File " + id + " has been updated with result " + res);

            tr.commit();

        } catch (HibernateException e){
            System.err.println("Can't update file with id " + id);
            e.printStackTrace();
            if (tr != null) tr.rollback();
        }



        return fileToUpdate;
    }

    @Override
    public File findById(long id) {

        Transaction tr = null;

        try(Session session = createSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            file = session.get(File.class, id);

            tr.commit();

        }catch (HibernateException e){
            System.err.println("Can't find file with id " + id);
            e.printStackTrace();
            if( tr!= null) tr.rollback();
        }


        return file;
    }
}
