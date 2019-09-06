package com.lesson3.HomeWork.DAO;


import org.hibernate.SessionFactory;

public interface DAO<T> {

    T save(T t);

    void delete(long id);

    T update(T t, long id);

    T findById(long id);



}
