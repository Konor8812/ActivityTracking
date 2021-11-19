package service.entityExtention;

import model.entity.Activity;
import service.factory.ItemService;

public interface ActivityExtension extends ItemService<Integer, Activity> {
    void wasTakenOrCompleted(int activityId, boolean increment);
    double getRewardForActivity(int activityId);
    Activity getActivityByName(String name);
}
