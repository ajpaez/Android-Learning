package com.apr.dbflowexample.dao;

import com.apr.dbflowexample.model.User;
import com.apr.dbflowexample.model.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by AntonioPC on 04/12/2016.
 */

public class UserDao implements GenericDao<User> {


    @Override
    public void addUser(User user) {
        user.insert();
    }

    @Override
    public void updateUser(User user) {
        user.update();
    }

    @Override
    public void deleteUser(User user) {
        user.delete();
    }

    @Override
    public List<User> findAll() {
        // Query all organizations
        List<User> userList = SQLite.select().
                from(User.class).queryList();
        return userList;
    }

    public List<User> findByAge(int age) {
        // Query all organizations
        List<User> userList = SQLite.select()
                .from(User.class)
                .where(User_Table.age.eq(age))
                .queryList();
        return userList;
    }
}
