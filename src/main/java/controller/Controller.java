package controller;


import service.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ActivityTracker")
public class Controller extends HttpServlet {


    @Override
    public void init() { }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String command = req.getParameter("command");
        System.out.println("command ==> " + command);
        executeCommand(req, resp, command);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        System.out.println("command ==> " + command);
        executeCommand(req, resp, command);

    }

    private void executeCommand(HttpServletRequest req, HttpServletResponse resp, String command) {
        CommandFactory cf = CommandFactory.getInstance();
        try{
            System.out.println("Activity name ==>  "+req.getParameter("activityName"));
        } catch(Exception e){
            e.printStackTrace();
        }
        try {
            cf.executeCommand(req, resp, command);
        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
