package model.entity;

public class User {

    private final String login;
    private String password;

    private String role;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.role = "user";
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
