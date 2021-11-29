package model.util;

import model.entity.Activity;
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

    private static final Logger logger = Logger.getLogger(Util.class);

    private static File filePropsEn = new File("/loc_en.properties");
    private static File filePropsRu = new File("/loc_ru.properties");

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

    public static List<Activity> sortBy(List<Activity> activities, String sortBy) {

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
                activities = sortByDuration(activities);

                break;
            case "numberOfTakes":
                activities = activities.stream()
                        .sorted((x1, x2) -> x2.getTakenByAmount() - x1.getTakenByAmount())
                        .collect(Collectors.toList());
                break;
        }
        return activities;
    }

    private static List<Activity> sortByDuration(List<Activity> activities) {
        List<Activity> hoursDuration = new ArrayList<>();
        List<Activity> daysDuration = new ArrayList<>();

        List<Activity> sorted = new ArrayList<>();

        for (Activity activity : activities) {

            if (activity.getDuration().contains("days")) {
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

    public static String getDescriptionAccordingToLang(String description, String lang) {
        String rbLang = "loc_" + lang;
        ResourceBundle rb = ResourceBundle.getBundle(rbLang);

        StringBuilder sb = new StringBuilder();
        String[] words = description.split(", ");

        for (int i = 0; i < words.length; i++) {
            try {
                sb.append(rb.getString(words[i]))
                        .append(", ");
                if (i == words.length - 2) {
                    sb.append(rb.getString(words[++i]));
                    break;
                }
            }catch(MissingResourceException e){
                sb.append(words[i]).append(", ");
            }
        }

        return sb.toString();

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
    }

    public static void loadProperty(String nameEn, String nameRu, String loc) {
        File file = null;
        if(loc.equals("en")){
            file = filePropsEn;
        } else if(loc.equals("ru")){
            file = filePropsRu;
        }

        Properties props = new Properties();
        props.setProperty(getFormattedStringForProps(nameEn), nameRu);
        try {
            PrintWriter fr = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            props.store(fr, "");
            fr.close();
            logger.info("properties successfully loaded");
        }catch(IOException e){
            logger.error("Wasn't able to load properties for activity", e);
        }
    }

    private static String getFormattedStringForProps(String s){
        StringBuilder sb = new StringBuilder();
        String[] words;

        if(s.contains("--")){
            words = s.split(" -- ");
        } else{
            words = s.split(" ");
        }

        for(String str: words){
            sb.append(str).append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String[] splitTagName(String tagName){
        String[] words;
        words = tagName.split(" -- ");
        return words;
    }

    public static boolean checkIfPropExists(String tagEn) {
        Pattern p = Pattern.compile(getPattern(getFormattedStringForProps(tagEn)));

        try(BufferedReader br = new BufferedReader(new FileReader(filePropsEn))){
            while(br.ready()){
                System.out.println(br.readLine());
                System.out.println(tagEn);
//                String line = br.readLine();
//                Matcher m = p.matcher(line);
//                System.out.println(line);
//                if(m.matches()){
//                    System.out.println(line + " matches! ");
//                    return true;
//                }
            }
        } catch(IOException e){
            logger.error("error while reading properties", e);
        }
        System.out.println("no matches");
        return false;
    }

    private static String getPattern(String tagEn) {
        return tagEn + "=[a-zA-Z ]+";
    }
}
