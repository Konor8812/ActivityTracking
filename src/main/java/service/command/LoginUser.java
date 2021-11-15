package service.command;

import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginUser implements Command {

    public LoginUser() { }

    @Override
    public String execute(HttpServletRequest req) {
        UserDAO ud = UserDAO.getInstance();
        User user = ud.getLogedInUser(req.getParameter("login"));
        if(user != null){
            String role = user.getRole();
            req.getSession().setAttribute("login", user.getLogin());
            req.getSession().setAttribute("role", role);
            if (role.equals("user")) {
                return "User.jsp";
            } else if (role.equals("admin")) {
                Command com = CommandFactory.getInstance().getCommand("showUsers", req, null);
                com.execute(req);
                return "Admin.jsp";
            }
        }else {
            req.getSession().setAttribute("loginError", true);
            return "index.jsp";
        }
        return "";
    }
    private void close(AutoCloseable... ac){
        try{
            for(AutoCloseable autoCloseable: ac){
                autoCloseable.close();
            }
        }  catch(Exception e){
            e.printStackTrace();
        }
    }

}
