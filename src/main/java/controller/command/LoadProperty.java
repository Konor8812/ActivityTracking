package controller.command;

import controller.InputDataValidator;
import model.exception.PropertyAlreadyExistsException;
import model.util.Util;

import javax.servlet.http.HttpServletRequest;

public class LoadProperty implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String key = req.getParameter("key");
        String value = req.getParameter("value");

        if(InputDataValidator.validateProperty(key, value)) {
            try {
                req.getSession().removeAttribute("propertyExists");
                Util.loadProperty(key, value, "ru");
                Util.loadProperty(key, key, "en");
                req.setAttribute("isSorted", false);
            }catch(PropertyAlreadyExistsException e){
                req.getSession().setAttribute("propertyExists", true);
            }

        }else{
            req.getSession().setAttribute("wrongTranslation", true);
        }
        return CommandFactory.getInstance().getCommand("showActivities").execute(req);
    }
}
