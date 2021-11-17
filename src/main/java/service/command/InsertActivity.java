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
        String description  = req.getParameter("description");
        ActivityDAO ad = ActivityDAO.getInstance();
        ad.insertActivity(name, duration, reward, description);
        return CommandFactory.getInstance().getCommand("showActivities", req, null).execute(req);
    }
}
