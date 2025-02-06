package models;

public class User {
    private int id;
    private String username;
    private String password;;
    private boolean gender;

    public User(String username, String password, boolean gender) {
        setUsername(username);
        setPassword(password);
        setGender(gender);
    }

    public User(int id, String username, String password, boolean gender) {
        this(username, password, gender);
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{id=" + id +
                ", username='" + username + '\'' +
                ", gender=" + (gender ? "Male" : "Female") + '}';
    }
}