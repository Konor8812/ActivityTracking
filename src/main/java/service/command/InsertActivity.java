package service.command;

import model.dao.ActivityDAO;

import javax.servlet.http.HttpServletRequest;

public class InsertActivity implements Command {


    public InsertActivity() {
    }


    @Override
    public String execute(HttpServletRequest req) {
        String name = req.getParameter("name");
        String duration  = req.getParameter("duration");
        double reward = Double.parseDouble(req.getParameter("reward"));

        ActivityDAO ad = ActivityDAO.getInstance();
        ad.insertActivity(name, duration, reward);

        return "Admin.jsp";
    }
}
