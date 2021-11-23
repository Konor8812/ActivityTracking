package service;

import model.database.Util;
import model.entity.Activity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        List<Activity> activityList = new ArrayList<>();
        Activity activity = new Activity();
        Activity activity2 = new Activity();
        Activity activity3 = new Activity();
        Activity activity4 = new Activity();
        Activity activity5 = new Activity();


        activity.setName("aaa");
        activity2.setName("ddd");
        activity3.setName("eee");
        activity4.setName("bbbb");
        activity5.setName("ccc");


        activity.setDuration("1 hours");
        activity2.setDuration("1 days");
        activity3.setDuration("5 hours");
        activity4.setDuration("2 days");
        activity5.setDuration("2 hours");

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
        for(Activity a: activityList){
            System.out.println("name ==> " + a.getName());
        }
        System.out.println();
        activityList = Util.sortBy(activityList, "duration");
        for(Activity a: activityList){
            System.out.println("duration ==> " + a.getDuration());
        }
        System.out.println();
        activityList = Util.sortBy(activityList, "reward");
        for(Activity a: activityList){
            System.out.println("reward ==> " + a.getReward());
        }
    }

}