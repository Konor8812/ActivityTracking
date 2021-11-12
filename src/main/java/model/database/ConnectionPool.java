package model.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

    private ConnectionPool(){ }

    private static ConnectionPool instance = null;

    public static synchronized ConnectionPool getInstance(){
        if (instance==null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection(){
        Context ctx;
        Connection con = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FPDatabase");
            con = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}