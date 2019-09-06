package com.lesson3.HomeWork.Service;

public interface Service<T> {

    T save(T t) throws Exception;

    void delete(long id) throws Exception;

    T update(T t, long id) throws Exception;

    T findById(long id) throws Exception;



}
