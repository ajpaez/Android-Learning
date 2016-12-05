package com.apr.dbflowexample.dao;

import java.util.List;

/**
 * Created by AntonioPC on 04/12/2016.
 */

public interface GenericDao<T> {

    void addUser(T t);

    void updateUser(T t);

    void deleteUser(T t);

    List<T> findAll();
}
