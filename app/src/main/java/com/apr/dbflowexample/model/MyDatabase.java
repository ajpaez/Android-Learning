package com.apr.dbflowexample.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by AntonioPC on 04/12/2016.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 1;
}
