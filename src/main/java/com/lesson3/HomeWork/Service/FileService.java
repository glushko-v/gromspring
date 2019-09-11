package com.lesson3.HomeWork.Service;

import com.lesson3.HomeWork.DAO.FileDAO;
import com.lesson3.HomeWork.model.File;
import com.lesson3.HomeWork.model.NullFieldsException;
import com.lesson3.HomeWork.model.Storage;
import com.lesson3.HomeWork.model.WrongIdException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class FileService implements Service<File> {
    FileDAO fileDAO = new FileDAO();
    private SessionFactory sessionFactory;

    private SessionFactory createSessionFactory() {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory;
    }



    @Override
    public File save(File file) throws NullFieldsException, HibernateException {
        if (isNullFields(file)) return fileDAO.save(file);
        else throw new NullFieldsException ("File contains null fields");
    }

    @Override
    public void delete(long id) throws WrongIdException, HibernateException {

        if (isIdExists(id))fileDAO.delete(id);
        else throw new WrongIdException ("There's no file with ID " + id);

    }

    @Override
    public File update(File file, long id) throws NullFieldsException, WrongIdException, HibernateException {
        if (isIdExists(id) && isNullFields(file)) return fileDAO.update(file, id);
        else if (!isNullFields(file)) throw new NullFieldsException("File contains null fields");
        else throw new WrongIdException ("There's no file with ID " + id);
    }

    @Override
    public File findById(long id) throws WrongIdException, HibernateException {
        if (isIdExists(id)) return fileDAO.findById(id);
        else throw new WrongIdException ("There's no file with ID " + id);
    }

    private boolean isIdExists(long id) { //true если id есть в базе

        Transaction tr = null;
        int count = 0;

        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query query = session.createSQLQuery("SELECT FILEID FROM FILES");
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

    public boolean isFormatSupported(Storage storage, File file) {

        int count = 0;

        String[] formatsSupported = storage.getFormatsSupported().split(", ");

        for (String format : formatsSupported) {
            if (format.equals(file.getFormat())) count++;
        }


        return count > 0;
    }

    private boolean isNullFields(File file){ //true если нет пустых полей

        return (file.getFormat() != null && file.getName() != null && file.getSize() != 0);
    }


}
