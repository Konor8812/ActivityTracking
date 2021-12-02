import model.util.Util;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws Throwable {

//        String s = Util.encodePassword("asd");
//        ResourceBundle rb = ResourceBundle.getBundle("loc_ru");
//
//        System.out.println(rb.getString("login"));
//
//        System.out.println(Util.getDescriptionAccordingToLang("sport, free, entertainment", "ru"));
//        System.out.println(Util.getDescriptionAccordingToLang("sport, free, entertainment", "en"));
//        System.out.println(InputDataValidator.validateActivity("name", "5 hours", "description", "5."));
//        System.out.println(InputDataValidator.validatePass("test_p@ss"));
//        System.out.println(InputDataValidator.validateActivity("dancing", "1 hours", "entertainment, relaxing, free", "3"));
        try{
//            ResourceBundle rb = ResourceBundle.getBundle("loc_ru");
//            System.out.println(rb.getString("do.tasks"));
//                PropertiesConfiguration config = new PropertiesConfiguration();

            Util.loadProperty("grind", "лыбиться", "ru");
            Util.loadProperty("do tasks", "сделать задание", "ru");
//            Util.loadProperty("do tasks", "сделать задание", "en");
        } catch(Exception e){
            e.printStackTrace();
        }


//        System.out.println(Util.getNameAccordingToLang("digging", "ru"));

    }
}
