package model.entity;

public class User {

    private final String login;
    private String password;
    private String role;

    public User(String login, String password, String role) {
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

    public String getRole() {
        return role;
    }


    public User(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" + "login='" + login + "'}";
    }
}
