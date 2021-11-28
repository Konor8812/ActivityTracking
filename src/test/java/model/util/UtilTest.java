package model.util;

import model.entity.Activity;
import model.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class UtilTest {

    @Test
    void getFormattedTime() {
        String expected = "4 days 5 hours 12 minutes 54 seconds ";

        String actual = Util.getFormattedTime(54*1000 + 12*1000*60 + 5*60*60*1000 + 4*24*60*60*1000);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void encodePassword() {
        String givenString = "admin";
        String encrypted = Util.encodePassword(givenString);

        Assertions.assertNotEquals(givenString, encrypted);
        Assertions.assertEquals(64, encrypted.length());

    }

    @Test
    void sortBy() {

        List<Activity> activityList = new ArrayList<>();
        Activity activity = new Activity();
        Activity activity2 = new Activity();
        Activity activity3 = new Activity();
        Activity activity4 = new Activity();
        Activity activity5 = new Activity();

        String expectedNamesConcatenation = "abcde";
        activity.setName("a");
        activity2.setName("d");
        activity3.setName("e");
        activity4.setName("b");
        activity5.setName("c");

        String expectedDurationConcatenation = "1 hours2 hours5 hours1 days2 days";
        activity.setDuration("1 hours");
        activity2.setDuration("1 days");
        activity3.setDuration("5 hours");
        activity4.setDuration("2 days");
        activity5.setDuration("2 hours");

        String expectedRewardConcatenation = "0.52.53.05.022.0";
        activity.setReward(2.5);
        activity2.setReward(3);
        activity3.setReward(22);
        activity4.setReward(5);
        activity5.setReward(0.5);

        activityList.add(activity);
        activityList.add(activity2);
        activityList.add(activity3);
        activityList.add(activity4);
        activityList.add(activity5);


        activityList = Util.sortBy(activityList, "name");;

        StringBuilder actual = new StringBuilder();
        for(Activity a: activityList){
            actual.append(a.getName());
        }

        Assertions.assertEquals(expectedNamesConcatenation, actual.toString());
        actual = new StringBuilder();

        activityList = Util.sortBy(activityList, "duration");

        for(Activity a: activityList){
            actual.append(a.getDuration());
        }
        Assertions.assertEquals(expectedDurationConcatenation, actual.toString());
        actual = new StringBuilder();

        activityList = Util.sortBy(activityList, "reward");

        for(Activity a: activityList){
            actual.append(a.getReward());
        }
        Assertions.assertEquals(expectedRewardConcatenation, actual.toString());
    }

    @Test
    public void descriptionTest(){
        String given = "sport, free, entertainment";
        String expectedEng = "sport, free, entertainment";
        String expectedRu = "спорт, бесплатно, развлечения";

        String actualEng = Util.getDescriptionAccordingToLang(given, "en");
        String actualRu = Util.getDescriptionAccordingToLang(given, "ru");
        Assertions.assertEquals(expectedEng, actualEng);
        Assertions.assertEquals(expectedRu, actualRu);

    }
}