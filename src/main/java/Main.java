import controller.InputDataValidator;
import model.util.Util;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws Throwable {

//        String s = Util.encodePassword("asd");
//        ResourceBundle rb = ResourceBundle.getBundle("loc_ru");
//
//        System.out.println(rb.getString("login"));
//
//        System.out.println(Util.getDescriptionAccordingToLang("sport, free, entertainment", "ru"));
//
//        System.out.println(InputDataValidator.validateLogin("abobus"));
//        System.out.println(InputDataValidator.validateLogin("abobus bus"));
//        System.out.println(InputDataValidator.validatePass("test_p@ss"));
//        System.out.println(InputDataValidator.validateActivity("dancing", "1 hours", "entertainment, relaxing, free", "3"));
//        System.out.println("~~~~");
//        System.out.println(InputDataValidator.validateActivity("going smoking", "5 mins", "rampart", "5.5"));
//        System.out.println(InputDataValidator.validateActivity("going smoking", "5 mins", "rampart", "2b"));


//        System.out.println(Util.checkIfPropExists("free"));
//        Properties props = new Properties();
//        props.setProperty("doing yoga","занятие йогой");
//        props.setProperty("other, free", "прочее, бесплатно");
////            FileOutputStream fr = new FileOutputStream(file, true);
//            PrintWriter fr = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
//            props.store(fr, "Properties");
//            fr.close();

//        System.out.println("validate tag 'other -- чееее'" + InputDataValidator.validateTag("other -- чееее"));
//
//
//            System.out.println("Util.check if prop exists "+Util.checkIfPropExists("other"));
        if(Util.checkIfPropExists("")) {
            Util.loadProperty("other", "чееее", "ru");
        }
//        if (InputDataValidator.validateTag("easy -- легко")) {
//            System.out.println("valid");
//            System.out.println(Util.checkIfPropExists("doing yoga"));
//            Util.loadProperty("doing yoga", "занятие йогой", "ru");
//        }
    }
}
