package model.entity;

public class Activity {
    private int id;
    private String name;
    private String duration;
    private double reward;
    private String description;
    private int takenByAmount = 0;

    private String status;
    private String timeSpent = "";

    public String getStatus() {
        return status;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTakenByAmount() {
        return takenByAmount;
    }

    public void setTakenByAmount(int takenByAmount) {
        this.takenByAmount = takenByAmount;
    }

    public Activity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;

    }

}
