package controller;

import controller.command.Command;
import controller.command.CommandFactory;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    List<Command> commands;
    HttpServletRequest req;
    List<String> results;
    List<String> expected;

    @BeforeEach
    void setUp() {
        results = new ArrayList<>();
        commands = new ArrayList<>();
        req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameter("showUsers")).thenReturn("showUsers");
        Mockito.when(req.getParameter("showProfile")).thenReturn("showProfile");

        expected = new ArrayList<>();
        expected.add("Admin.jsp");
        expected.add("Profile.jsp");
    }

    @Test
    void doGet() {
        Command showUsers = Mockito.mock(Command.class);
        Mockito.when(showUsers.execute(req)).thenReturn("Admin.jsp");

        Command showProfile = Mockito.mock(Command.class);
        Mockito.when(showProfile.execute(req)).thenReturn("Profile.jsp");

        commands.add(showUsers);
        commands.add(showProfile);

        for (Command command: commands){
            results.add(command.execute(req));
        }
        Assertions.assertEquals(expected, results);
    }

    @Test
    void doPost() {
        Command showUsers = Mockito.mock(Command.class);
        Mockito.when(showUsers.execute(req)).thenReturn("Admin.jsp");

        Command showProfile = Mockito.mock(Command.class);
        Mockito.when(showProfile.execute(req)).thenReturn("Profile.jsp");

        commands.add(showUsers);
        commands.add(showProfile);
        for (Command command: commands ){
            results.add(command.execute(req));
        }
        Assertions.assertEquals(expected, results);
    }
}