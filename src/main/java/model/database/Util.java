package model.database;

import model.entity.Activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Util {

    public static void close(AutoCloseable... ac){
        for(AutoCloseable autoCloseable: ac) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }
    public static String getFormattedTime(long millis){

        StringBuilder sb = new StringBuilder();
        long second = 1000;
        long minute = 60*second;
        long hour = 60*minute;
        long day = 24*hour;


        long days = millis / day;
        long leftOver = millis % day;
        long hours = leftOver / hour;
        leftOver = leftOver % hour;
        long minutes = leftOver / minute;
        leftOver = leftOver % minute;
        long seconds = leftOver / second;
        if(days != 0){
            sb.append(days).append(" days ");
        }
        if(hours != 0){
            sb.append(hours).append(" hours ");
        }
        if(minutes != 0){
            sb.append(minutes).append(" minutes ");
        }
        if(seconds != 0){
            sb.append(seconds).append(" seconds ");
        }
        System.out.println(sb);
        return sb.toString();
    }

    public static String encodePassword(String pass){

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

        switch(sortBy){
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
        return  activities;
    }

    private static List<Activity> sortByDuration(List<Activity> activities) {
        List<Activity> hoursDuration = new ArrayList<>();
        List<Activity> daysDuration = new ArrayList<>();

        List<Activity> sorted = new ArrayList<>();

        for(Activity activity: activities){

            if(activity.getDuration().contains("days")){
                daysDuration.add(activity);
            }else{
                hoursDuration.add(activity);
            }
        }

        hoursDuration = hoursDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());
        daysDuration = daysDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());

        for(Activity activity: hoursDuration){
            sorted.add(activity);
        }
        for(Activity activity: daysDuration){
            sorted.add(activity);
        }

        return sorted;
    }
}
