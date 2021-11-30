package controller;

import java.util.regex.*;

public abstract class InputDataValidator {

    public static boolean validateLogin(String login) {
        Matcher loginMatcher = Pattern.compile("[a-zA-Z0-9]{4,16}").matcher(login);
        return loginMatcher.matches();
    }

    public static boolean validatePass(String pass) {
        Matcher passMatcher = Pattern.compile("[a-zA-Z0-9_!@#$%^&*()\\-+]{4,16}").matcher(pass);
        return passMatcher.matches();
    }


    public static boolean validateActivity(String name, String duration, String description, String reward) {
        Matcher nameMatcher = Pattern.compile("[a-zA-Z ]{3,50}").matcher(name);
        boolean nameMatches = nameMatcher.matches();

        Matcher durationMatcher = Pattern.compile(duration.contains(".") ? "([0-9]+.[0-9]) (hours|days)" : "([0-9])+ (hours|days)").matcher(duration);
        boolean durationMatches = durationMatcher.matches();



        Matcher descriptionMatcher = Pattern.compile("[a-zA-Z ,]{3,50}").matcher(description);
        boolean descriptionMatches = descriptionMatcher.matches();

        boolean rewardMatches = false;
        try {
            Double.parseDouble(reward);
            rewardMatches = true;
        } catch (Exception e) {
            //reward format is wrong, data not valid
        }

        return nameMatches && durationMatches && descriptionMatches && rewardMatches;
    }

}
