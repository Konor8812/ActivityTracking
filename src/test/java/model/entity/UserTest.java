package model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    public void gettersSettersTest(){
        User user = new User();

        int id = 1;
        String login = "test";
         String password = "testPass";
         String role = "user";
         int activitiesAmount = 1;
         double totalPoints = 4.5;
         String status = "blocked";
         int requestsAmount = 2;


        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        user.setActivitiesAmount(activitiesAmount);
        user.setTotalPoints(totalPoints);
        user.setStatus(status);
        user.setRequestsAmount(requestsAmount);

        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(login, user.getLogin());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(role, user.getRole());
        Assertions.assertEquals(activitiesAmount, user.getActivitiesAmount());
        Assertions.assertEquals(totalPoints, user.getTotalPoints());
        Assertions.assertEquals(status, user.getStatus());
        Assertions.assertEquals(requestsAmount, user.getRequestsAmount());


    }

}