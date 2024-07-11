package com.revature.rba.util;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
    Factory Design
        - Creational -- used to abstract the creation and instantiation of our class away from the user
        - churn out instances of connections to all the other objects in our API

    Singleton Design Pattern
        - Creational Design, RESTRICTS that there is only a single instance of the class that can be made
 */
public class ConnectionFactory {
    // Eagerly instantiating the object
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    private Properties props = new Properties();
    //Singleton Design via privatizing the Constructor to only be executable in the class itself
    private ConnectionFactory(){
        try {
            props.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Static block to CHECK that you have your intended driver available
    static { // this happens when class is loaded in not when an object is created
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }

    public Connection getConnection(){
        try {
            // WE NEED TO OBFUSCATE this info
            // ensure db.properties file is set up in directory
            return DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
