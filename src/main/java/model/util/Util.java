package model.util;

import model.entity.Activity;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Util {

    private static Logger logger = Logger.getLogger(Util.class);

    public static void close(AutoCloseable... ac) {
        for (AutoCloseable autoCloseable : ac) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public static String getFormattedTime(long millis) {

        StringBuilder sb = new StringBuilder();
        long second = 1000;
        long minute = 60 * second;
        long hour = 60 * minute;
        long day = 24 * hour;


        long days = millis / day;
        long leftOver = millis % day;
        long hours = leftOver / hour;
        leftOver = leftOver % hour;
        long minutes = leftOver / minute;
        leftOver = leftOver % minute;
        long seconds = leftOver / second;
        if (days != 0) {
            sb.append(days).append(" days ");
        }
        if (hours != 0) {
            sb.append(hours).append(" hours ");
        }
        if (minutes != 0) {
            sb.append(minutes).append(" minutes ");
        }
        if (seconds != 0) {
            sb.append(seconds).append(" seconds ");
        }
        System.out.println(sb);
        return sb.toString();
    }

    public static String encodePassword(String pass) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] bytes = md.digest(pass.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static List<Activity> sortBy(List<Activity> activities, String sortBy, String lang) {

        switch (sortBy) {
            case "name":
                activities = activities.stream()
                        .sorted(Comparator.comparing(Activity::getName))
                        .collect(Collectors.toList());
                break;
            case "reward":
                activities = activities.stream()
                        .sorted(Comparator.comparing(Activity::getReward))
                        .collect(Collectors.toList());
                break;
            case "duration":
                activities = sortByDuration(activities, lang);

                break;
            case "numberOfTakes":
                activities = activities.stream()
                        .sorted((x1, x2) -> x2.getTakenByAmount() - x1.getTakenByAmount())
                        .collect(Collectors.toList());
                break;
        }
        return activities;
    }

    private static List<Activity> sortByDuration(List<Activity> activities, String lang) {
        List<Activity> hoursDuration = new ArrayList<>();
        List<Activity> daysDuration = new ArrayList<>();

        List<Activity> sorted = new ArrayList<>();

        for (Activity activity : activities) {

            if (activity.getDuration().contains(lang.equals("en") ? "days" : "дней")) {
                daysDuration.add(activity);
            } else {
                hoursDuration.add(activity);
            }
        }

        hoursDuration = hoursDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());
        daysDuration = daysDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());

        for (Activity activity : hoursDuration) {
            sorted.add(activity);
        }
        for (Activity activity : daysDuration) {
            sorted.add(activity);
        }

        return sorted;
    }

    public static void removeUnneededAttributes(HttpServletRequest req) {
        req.getSession().removeAttribute("loginError");
        req.getSession().removeAttribute("regError");
        req.getSession().removeAttribute("userIsBlocked");
        req.getSession().removeAttribute("wrongData");
        req.getSession().removeAttribute("shouldShowUsersActivities");
        req.getSession().removeAttribute("activityTaken");
        req.getSession().removeAttribute("wrongDataFormat");
        req.getSession().removeAttribute("activityExists");
        req.getSession().removeAttribute("wrongTranslationFormat");
        req.getSession().removeAttribute("wrongTagName");
        req.getSession().removeAttribute("wrongTranslation");
    }


    public static void removeActivityRelatedAttributes(HttpServletRequest req) {
        req.getSession().removeAttribute("wrongDurationFormat");
        req.getSession().removeAttribute("numberOfSeries");
        req.getSession().removeAttribute("activities");
        req.getSession().removeAttribute("sorted");
        req.getSession().removeAttribute("activityExists");
    }

    public static String getDescriptionAccordingToLang(String description, String lang) {
        String rbLang = "loc_" + lang;
        ResourceBundle rb = ResourceBundle.getBundle(rbLang);

        StringBuilder sb = new StringBuilder();
        String[] words = description.split(", ");

        for (int i = 0; i < words.length; i++) {
            try {
                sb.append(rb.getString(words[i]))
                        .append(", ");
            }catch(MissingResourceException e){
                sb.append(words[i]).append(", ");
                try(BufferedWriter br = new BufferedWriter(new FileWriter("needs_localization.txt", true))){
                    br.write("\n");
                    br.write(words[i]);
                    logger.info("successfully added to needs_localization.txt");
                } catch(IOException ioe){
                    logger.error("wasn't able to write in needs_localization.txt", ioe);
                }
            }
        }
        StringBuilder temp = new StringBuilder(sb.toString().trim());

        return temp.toString().endsWith(",") ? temp.deleteCharAt(temp.length() - 1).toString() : temp.toString();
    }

    public static String getNameAccordingToLang(String name, String language) {
        String rbLang = "loc_" + language;
        ResourceBundle rb = ResourceBundle.getBundle(rbLang);
        String formattedName = name.replaceAll(" ", ".");
        while(formattedName.endsWith(".")){
            formattedName = new StringBuilder(formattedName).deleteCharAt(formattedName.length()-1).toString();
        }
        try {
            return rb.getString(formattedName);
        }catch(MissingResourceException e){
            try(BufferedWriter br = new BufferedWriter(new FileWriter("needs_localization.txt", true))){
                br.write("\n");
                br.write(name);
            } catch(IOException ioe){
                logger.error("wasn't able to write in needs_localization.txt", ioe);
            }finally {
                logger.info("successfully added to needs_localization.txt");
                return name;
            }
        }
    }

    public static String getDurationAccordingToLang(String duration, String language) {

        String rbLang = "loc_" + language;
        ResourceBundle rb = ResourceBundle.getBundle(rbLang);
        Pattern p = Pattern.compile(duration.contains(".") ? "([0-9]+.[0-9]) (hours|days)" : "([0-9])+ (hours|days)");
        Matcher m = p.matcher(duration);

        StringBuilder sb = new StringBuilder();
        if(m.find()){
            sb.append(m.group(1)).append(" ").append(rb.getString(m.group(2)));
        }

        return sb.toString().trim();

    }

    public static void loadProperty(String key, String value, String loc){
        String bundleName = "loc_" + loc;
        if(!propExists(key, bundleName)) {
            try {
                Configurations configs = new Configurations();

                FileBasedConfigurationBuilder<PropertiesConfiguration> propBuilder = configs.propertiesBuilder(bundleName + ".properties");

                PropertiesConfiguration config = propBuilder.getConfiguration();
                String formattedKey = key.trim().replaceAll(" ", ".");
                config.addProperty(formattedKey, value);

                propBuilder.save();
                System.out.println("added");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean propExists(String key, String bundleName){

        ResourceBundle rb = ResourceBundle.getBundle(bundleName);
        String formattedKey = key.trim().replaceAll(" ", ".");

        System.out.print(formattedKey);
        if(rb.containsKey(key)){
            System.out.print("   exists\n");
            return true;
        }
        System.out.print("   not exists\n");
        return false;
    }
}
