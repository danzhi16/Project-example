package models;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean gender;

    public User(String username, String password, String name, String surname, boolean gender) {
        setUsername(username);
        setPassword(password);
        setName(name);
        setSurname(surname);
        setGender(gender);
    }

    public User(int id, String username, String password, String name, String surname, boolean gender) {
        this(username, password, name, surname, gender);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + (gender ? "Male" : "Female") + '}';
    }
}