package service.command;


import model.dao.UserDAO;
import model.database.ConnectionPool;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        UserDAO ud = UserDAO.getInstance();

        ud.insertUser("login", "pass");

//        ConnectionPool cp = ConnectionPool.getInstance();
//        Connection con = cp.getConnection();
//        System.out.println(con);
    }

}
