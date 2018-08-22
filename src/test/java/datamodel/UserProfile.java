package datamodel;

public class UserProfile {
    private final String username;
    private final String password;

    public UserProfile(
           String username,
           String password
    ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {

    }

    public void setPassword() {

    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
