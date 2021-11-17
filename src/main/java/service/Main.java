package service;


import model.dao.UserDAO;
import model.database.DBManager;

import java.sql.Connection;


public class Main {
    public static void main(String[] args) throws Exception {
        UserDAO ud = UserDAO.getInstance();


        DBManager dm = DBManager.getInstance();
        Connection con = dm.getConnection();
        con.createStatement().execute("INSERT INTO user (login, password, role) values ('admin', 'admin', 'admin')");

    }

}
