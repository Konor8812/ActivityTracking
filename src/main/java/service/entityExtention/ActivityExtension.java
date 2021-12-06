package service.entityExtention;

import model.entity.Activity;
import service.factory.ItemService;

import java.util.List;

public interface ActivityExtension extends ItemService<Integer, Activity> {
    void wasTakenOrCompleted(int activityId, boolean increment);
    double getRewardForActivity(int activityId);

    List<Activity> getFiveItemsAsList(int number);
}
