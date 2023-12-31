package models;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("unused")
@Entity
@Table(name = "user", schema = "MyHike")
public class User {
    private String userName;
    private String userPassword;
    private boolean isAdmin;
    private List<Hike> userHikes;

    public User() {}

    public User(String userName, String userPassword, boolean isAdmin, List<Hike> userHikes) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
        this.userHikes = userHikes;
    }

    @Id
    @Column(name = "user_name", length = 100, nullable = false)
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_password", length = 100, nullable = false)
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Column(name = "is_admin")
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @OneToMany(mappedBy = "hikeOfUser", cascade = CascadeType.ALL)
    public List<Hike> getUserHikes() {
        return userHikes;
    }

    public void setUserHikes(List<Hike> userHikes) {
        this.userHikes = userHikes;
    }

}
