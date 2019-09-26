package com.lesson3.HomeWork.Service;

import com.lesson3.HomeWork.DAO.FileDAO;
import com.lesson3.HomeWork.model.Exceptions.FileAlreadyInStorageException;
import com.lesson3.HomeWork.model.Exceptions.FormatNotSupportedException;
import com.lesson3.HomeWork.model.File;
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
import java.math.BigDecimal;
import java.util.List;

public class FileService {

    private FileDAO fileDAO;

    private SessionFactory sessionFactory;

    private SessionFactory createSessionFactory() {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory;
    }

    @Autowired
    public FileService(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }


    public File save(File file) throws NullFieldsException, HibernateException {
        if (isNullFields(file)) return fileDAO.save(file);
        else throw new NullFieldsException("File contains null fields");
    }


    public void delete(long id) throws WrongIdException, HibernateException {

        if (isIdExists(id)) fileDAO.delete(id);
        else throw new WrongIdException("There's no file with ID " + id);

    }

    public File update(File file, long id) throws NullFieldsException, WrongIdException, HibernateException {
        if (isIdExists(id) && isNullFields(file)) return fileDAO.update(file, id);
        else if (!isNullFields(file)) throw new NullFieldsException("File contains null fields");
        else throw new WrongIdException("There's no file with ID " + id);
    }


    public File findById(long id) throws WrongIdException, HibernateException {
        if (isIdExists(id)) return fileDAO.findById(id);
        else throw new WrongIdException("There's no file with ID " + id);
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

    public boolean isFormatSupported(Storage storage, File file) {//true, если формат поддерживается

        int count = 0;

        String[] formatsSupported = storage.getFormatsSupported().split(", ");

        for (String format : formatsSupported) {
            if (format.equals(file.getFormat())) count++;
        }


        return count > 0;
    }

    private boolean isNullFields(File file) { //true если нет пустых полей

        return (file.getFormat() != null && file.getName() != null && file.getSize() != 0);
    }

    public void put(Storage storage, File file) throws FileAlreadyInStorageException, FormatNotSupportedException {

        if(isFormatSupported(storage, file) && file.getStorage() == null) fileDAO.put(storage, file);
        else if (file.getStorage() != null ) throw new FileAlreadyInStorageException("File " + file.getId() + " is already in storage " + file.getStorage().getId());
        else if (!isFormatSupported(storage, file)) throw new FormatNotSupportedException("File format " + file.getFormat() + " is not supported by Storage " + storage.getId());

    }

    public void deleteFromStorage(Storage storage, File file) throws NullFieldsException, FileAlreadyInStorageException {

        if (file.getStorage().equals(storage)) fileDAO.deleteFromStorage(storage, file);
        else if (file.getStorage() == null) throw new NullFieldsException("File " + file.getId() + " is not stored in any storage");
        else if (file.getStorage() != null && !file.getStorage().equals(storage)) throw new FileAlreadyInStorageException("File " + file.getId() + " is in storage " + file.getStorage().getId());

    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws FileAlreadyInStorageException, NullFieldsException, WrongIdException {

//        if (storageFrom != null && storageTo != null && !storageFrom.equals(storageTo)) {
//            if (isIdExists(id)) fileDAO.transferFile(storageTo, id);
//            else throw new WrongIdException("There is no file with ID " + id);
//        } else if (storageFrom == null || storageTo == null) throw new NullFieldsException("One of the storages does not exist, can not transfer file");
//        else if (storageFrom.equals(storageTo)) throw new FileAlreadyInStorageException("File " + id + " is already in storage " + storageFrom.getId());

        fileDAO.transferFile(storageTo, id);


//        if (file.getStorage() != null && !file.getStorage().equals(storageTo)) fileDAO.put(storageTo, file);
//        else if (file.getStorage() != null && file.getStorage().equals(storageTo))
//            throw new FileAlreadyInStorageException("File " + file.getId() + " is already in storage " + file.getStorage().getId());
//        else if (file.getStorage() == null || storageTo == null) throw new NullFieldsException("File has no storage or storage does not exist");


    }





}
