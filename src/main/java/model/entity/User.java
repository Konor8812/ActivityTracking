package model.entity;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private int activitiesAmount;
    private int totalPoints;

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

    public int getTotalPoints() {
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
