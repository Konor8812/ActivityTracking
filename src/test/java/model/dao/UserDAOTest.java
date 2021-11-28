package model.dao;

import model.entity.User;
import model.exception.WrongLoginData;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    public void testUserDaoInsert() throws WrongLoginData {
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        User admin = new User();
        admin.setRole("admin");

        Mockito.when(userDAO.getUserById(1)).thenReturn(admin);
        User user = userDAO.getUserById(1);

        Assertions.assertEquals(admin, user);


    }

}
