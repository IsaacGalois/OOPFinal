package com.astontech.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySQL {

    protected static String dbHost = "localhost";
    protected static String dbName = "OOPFinal";
    protected static String dbUser = "consoleUser";
    protected static String dbPass = "qwe123$!";
    protected static String useSSL = "false";
    protected static String procBod = "true";
    protected static Connection connection = null;

    protected static final int GET_BY_ID = 10;
    protected static final int GET_COLLECTION = 20;
    protected static final int INSERT = 10;
    protected static final int UPDATE = 20;
    protected static final int DELETE = 30;

    protected static void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL Driver not found! " + ex);
        }

//        System.out.println("MySQL Driver Registered.");


        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":3306/" + dbName + "?useSSL=" + useSSL + "&noAccessToProcedureBodies=" + procBod+"&zeroDateTimeBehavior=convertToNull", dbUser, dbPass);
        } catch (SQLException ex) {
            System.out.println("Connection Failed! " + ex);
        }

//        if (connection != null)
//            System.out.println("Successfully connected to MySQL database");
//        else
//            System.out.println("Connection Failed");
    }

}
