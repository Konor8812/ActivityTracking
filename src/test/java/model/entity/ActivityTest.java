package model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ActivityTest {

    @Test
    public void gettersSettersTest(){
        Activity activity = new Activity();

        int id = 1;
        String name = "test";
        String duration = "1 hours";
        double reward = 3.4;
        String description = "testD";
        int takenByAmount = 1;
        String status = "requested";
        String timeSpent = "1 days";

        activity.setId(id);
        activity.setName(name);
        activity.setDuration(duration);
        activity.setReward(reward);
        activity.setDescription(description);
        activity.setTakenByAmount(takenByAmount);
        activity.setStatus(status);
        activity.setTimeSpent(timeSpent);

        Assertions.assertEquals(id, activity.getId());
        Assertions.assertEquals(name, activity.getName());
        Assertions.assertEquals(duration, activity.getDuration());
        Assertions.assertEquals(reward, activity.getReward());
        Assertions.assertEquals(description, activity.getDescription());
        Assertions.assertEquals(takenByAmount, activity.getTakenByAmount());
        Assertions.assertEquals(timeSpent, activity.getTimeSpent());
        Assertions.assertEquals(timeSpent, activity.getTimeSpent());
    }

}