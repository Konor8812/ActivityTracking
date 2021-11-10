package model.database;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.util.logging.*;

public class DBManager {

    private String user;
    private String pass;

    private DBManager() {
    }

    public static DBManager getInstance() {
        return new DBManager();
    }

    public String getConnectionUrl() {
        String url = null;
        try (InputStream input = new FileInputStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("connection.url");
        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.WARNING, "Exception: ", ex);
        }
        return url;
    }

    public Connection getConnection() throws SQLException {
        Connection conn;
        try (InputStream input = new FileInputStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            user = prop.getProperty("user");
            pass = prop.getProperty("password");
        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.WARNING, "Exception: ", ex);
        }
        conn = DriverManager.getConnection(getConnectionUrl(), user, pass);
        return conn;
    }

}
