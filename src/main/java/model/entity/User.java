package model.entity;

public class User {
    private int id;
    private final String login;
    private String password;
    private String role;
    private int activitiesAmount;
    private int totalPoints;

    public User(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
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

    @Override
    public String toString() {
        return "User{" + "login='" + login + "'}";
    }
}
