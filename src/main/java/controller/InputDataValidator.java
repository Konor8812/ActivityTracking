package controller;

import model.util.Util;

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
        System.out.println(nameMatches);
        Matcher durationMatcher = Pattern.compile("[0-9]+(.[0-9])? hours|days").matcher(duration);
        boolean durationMatches = durationMatcher.matches();
        System.out.println(durationMatches);
        Matcher descriptionMatcher = Pattern.compile("[a-zA-Z ,]{3,50}").matcher(description);
        boolean descriptionMatches = descriptionMatcher.matches();
        System.out.println(descriptionMatches);
        boolean rewardMatches = false;

        try {
            Double.parseDouble(reward);
            rewardMatches = true;
        } catch (Exception e) {
            //reward format is wrong, data no valid
        }
        System.out.println(rewardMatches);

        return nameMatches && durationMatches && descriptionMatches && rewardMatches;
    }

    public static boolean validateActivityNameRu(String activityName) {
        Matcher nameMatcher = Pattern.compile("[а-яА-Я ]{3,50}").matcher(activityName);

        return nameMatcher.matches();
    }

    public static boolean validateTag(String tag) {
        String tagRu;
        String tagEn;
        Matcher tagMatcher = Pattern.compile("([a-zA-z]+) -- ([а-яА-Я]+)").matcher(tag);
        if(tagMatcher.find()){
            tagEn = tagMatcher.group(1);
            tagRu = tagMatcher.group(2);
        }else{
            return false;
        }
        Matcher tagRuMatcher = Pattern.compile("[а-яА-Я]{3,50}").matcher(tagRu);
        Matcher tagEnMatcher = Pattern.compile("[a-zA-Z]{3,50}").matcher(tagEn);
        if(tagRuMatcher.matches() && tagEnMatcher.matches()){
            return !Util.checkIfPropExists(tagEn);
        } else{
            return false;
        }

    }
}
