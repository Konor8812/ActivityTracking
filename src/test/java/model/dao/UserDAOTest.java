package model.dao;

import model.entity.User;
import model.exception.WrongLoginData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class UserDAOTest {

    UserDAO userDAO;

    @BeforeEach
    void setUp() throws Exception{
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyInt())).thenReturn(new User());
    }


    @Test
    public void testUserDaoGet() throws WrongLoginData {

        User user = userDAO.getUserById(1);
        Assertions.assertEquals(User.class, user.getClass());

        user = userDAO.getUserById(7);
        Assertions.assertEquals(User.class, user.getClass());
    }

}
