import model.util.Util;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws Throwable {

        System.out.println(Util.encodePassword("admin"));
    }
}
