package com.lesson3.HomeWork.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson3.HomeWork.DAO.StorageDAO;
import com.lesson3.HomeWork.model.Exceptions.NullFieldsException;
import com.lesson3.HomeWork.model.Storage;
import com.lesson3.HomeWork.model.Exceptions.WrongIdException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class StorageService  {


    private SessionFactory sessionFactory;
    private StorageDAO storageDAO;


    private SessionFactory createSessionFactory() {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory;
    }

    @Autowired
    public StorageService(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }


    public Storage save(Storage storage) throws HibernateException, NullFieldsException {

        if (!isNullFields(storage)) return storageDAO.save(storage);
        else throw new NullFieldsException("Storage contains null fields");
    }


    public void delete(long id) throws WrongIdException, HibernateException {

        if (isIdExists(id)) storageDAO.delete(id);
        else throw new WrongIdException("There's no storage with ID " + id);

    }


    public Storage update(Storage storage, long id) throws NullFieldsException, HibernateException, WrongIdException {

        if (isIdExists(id) && !isNullFields(storage)) return storageDAO.update(storage, id);

        else if (isNullFields(storage)) throw new NullFieldsException("Storage contains null fields");
        else throw new WrongIdException("There's no storage with ID " + id);
    }


    public Storage findById(long id) throws WrongIdException, HibernateException {

        if (isIdExists(id)) return storageDAO.findById(id);

        else throw new WrongIdException("There's no storage with ID " + id);
    }


    public Storage mapJSONtoStorage(BufferedReader br) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Storage storage = mapper.readValue(br, Storage.class);
            return storage;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
