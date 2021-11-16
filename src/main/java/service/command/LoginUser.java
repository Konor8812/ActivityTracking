package service.command;

import model.dao.UserDAO;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class LoginUser implements Command {

    public LoginUser() { }

    @Override
    public String execute(HttpServletRequest req) {
        UserDAO ud = UserDAO.getInstance();
        User user = ud.getLogedInUser(req.getParameter("login"), req.getParameter("password"));
        if(user != null){
            req.getSession().setAttribute("loginError", false);
            String role = user.getRole();
            req.getSession().setAttribute("regedAs", user);
            if (role.equals("user")) {
                CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
                return "User.jsp";
            } else if (role.equals("admin")) {
                Command com = CommandFactory.getInstance().getCommand("showUsers", req, null);
                com.execute(req);
                return "Admin.jsp";
            }
        }else {
            req.getSession().setAttribute("loginError", true);
            req.getSession().setAttribute("regError", false);
            return "index.jsp";
        }
        return "";
    }
}
