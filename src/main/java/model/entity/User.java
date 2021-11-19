package model.entity;

public class User {
    private int id;
    private String login;
    private String password;
    private String role = "user";
    private int activitiesAmount = 0;
    private double totalPoints = 0;
    private String status = "available";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User() { }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String login) {
        this.login = login;
    }

    public int getActivitiesAmount() {
        return activitiesAmount;
    }

    public String getRole() {
        return role;
    }

    public void setActivitiesAmount(int activitiesAmount) {
        this.activitiesAmount = activitiesAmount;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void incrementActivitiesAmount(){
        ++activitiesAmount;
    }
    public void decrementActivitiesAmount(){
        --activitiesAmount;
    }

}
