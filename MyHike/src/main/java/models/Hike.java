package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

@SuppressWarnings("unused")
@Entity
@Table(name = "hike", schema = "MyHike")
public class Hike {
    private String hikeId;
    private String hikeName;
    private String hikeDescription;
    private String hikeRouteCoordinates;
    private Time hikeDuration;
    private Integer hikeAltitude;
    private BigDecimal hikeDistance;
    private Integer hikeStamina;
    private Integer hikeStrength;
    private Integer hikeDifficulty;
    private Integer hikeLandscape;
    private String hikeImage;
    private String hikeMonths;
    private Region hikeRegion;
    private List<PointOfInterest> hikePointsOfInterest;
    private List<Comment> hikeComments;
    private boolean isDeleted;
    private User hikeOfUser;

    public Hike() {}

    public Hike(String hikeId, String hikeName, String hikeDescription, String hikeRouteCoordinates, Time hikeDuration, Integer hikeAltitude, BigDecimal hikeDistance, Integer hikeStamina, Integer hikeStrength, Integer hikeDifficulty, Integer hikeLandscape, String hikeImage, String hikeMonths, Region hikeRegion, List<Comment> hikeComments, List<PointOfInterest> hikePointsOfInterest, boolean isDeleted, User hikeOfUser) {
        this.hikeId = hikeId;
        this.hikeName = hikeName;
        this.hikeDescription = hikeDescription;
        this.hikeRouteCoordinates = hikeRouteCoordinates;
        this.hikeDuration = hikeDuration;
        this.hikeAltitude = hikeAltitude;
        this.hikeDistance = hikeDistance;
        this.hikeStamina = hikeStamina;
        this.hikeStrength = hikeStrength;
        this.hikeDifficulty = hikeDifficulty;
        this.hikeLandscape = hikeLandscape;
        this.hikeImage = hikeImage;
        this.hikeMonths = hikeMonths;
        this.hikeRegion = hikeRegion;
        this.hikeComments = hikeComments;
        this.hikePointsOfInterest = hikePointsOfInterest;
        this.isDeleted = isDeleted;
        this.hikeOfUser = hikeOfUser;
    }

    @Id
    @Column(name = "hike_id")
    public String getHikeId() {
        return hikeId;
    }
    public void setHikeId(String hikeId) {
        this.hikeId = hikeId;
    }

    @Column(name = "hike_name", length = 100, nullable = false)
    public String getHikeName() {
        return hikeName;
    }
    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    @Column(name = "hike_description", length = 1000, nullable = false)
    public String getHikeDescription() {
        return hikeDescription;
    }

    public void setHikeDescription(String hikeDescription) {
        this.hikeDescription = hikeDescription;
    }

    @Column(name = "hike_route_coordinates", nullable = false)
    public String getHikeRouteCoordinates() {
        return hikeRouteCoordinates;
    }
    public void setHikeRouteCoordinates(String hikeRouteCoordinates) {
        this.hikeRouteCoordinates = hikeRouteCoordinates;
    }

    @Column(name = "hike_duration")
    public Time getHikeDuration() {
        return hikeDuration;
    }
    public void setHikeDuration(Time hikeDuration) {
        this.hikeDuration = hikeDuration;
    }

    @Column(name = "hike_altitude")
    public Integer getHikeAltitude() {
        return hikeAltitude;
    }
    public void setHikeAltitude(Integer hikeAltitude) {
        this.hikeAltitude = hikeAltitude;
    }

    @Column(name = "hike_distance", precision = 5, scale = 2)
    public BigDecimal getHikeDistance() {
        return hikeDistance;
    }
    public void setHikeDistance(BigDecimal hikeDistance) {
        this.hikeDistance = hikeDistance;
    }

    @Column(name = "hike_stamina")
    public Integer getHikeStamina() {
        return hikeStamina;
    }
    public void setHikeStamina(Integer hikeStamina) {
        this.hikeStamina = hikeStamina;
    }

    @Column(name = "hike_strength")
    public Integer getHikeStrength() {
        return hikeStrength;
    }
    public void setHikeStrength(Integer hikeStrength) {
        this.hikeStrength = hikeStrength;
    }

    @Column(name = "hike_difficulty")
    public Integer getHikeDifficulty() {
        return hikeDifficulty;
    }
    public void setHikeDifficulty(Integer hikeDifficulty) {
        this.hikeDifficulty = hikeDifficulty;
    }

    @Column(name = "hike_landscape")
    public Integer getHikeLandscape() {
        return hikeLandscape;
    }
    public void setHikeLandscape(Integer hikeLandscape) {
        this.hikeLandscape = hikeLandscape;
    }

    @Column(name = "hike_image")
    public String getHikeImage() {
        return hikeImage;
    }
    public void setHikeImage(String hikeImage) {
        this.hikeImage = hikeImage;
    }

    @Column(name = "hike_months")
    public String getHikeMonths() {
        return hikeMonths;
    }
    public void setHikeMonths(String hikeMonths) {
        this.hikeMonths = hikeMonths;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "pointOfInterestHike", cascade = CascadeType.ALL)
    public List<PointOfInterest> getHikePointsOfInterest() {
        return hikePointsOfInterest;
    }
    public void setHikePointsOfInterest(List<PointOfInterest> hikePointsOfInterest) {
        this.hikePointsOfInterest = hikePointsOfInterest;
    }

    @Column(name = "is_deleted")
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToOne
    @JoinColumn(name = "hike_region", referencedColumnName = "region_name")
    public Region getHikeRegion() {
        return hikeRegion;
    }
    public void setHikeRegion(Region hikeRegion) {
        this.hikeRegion = hikeRegion;
    }

    @ManyToOne
    @JoinColumn(name = "hike_of_user", referencedColumnName = "user_name")
    public User getHikeOfUser() {
        return hikeOfUser;
    }

    public void setHikeOfUser(User hikeOfUser) {
        this.hikeOfUser = hikeOfUser;
    }

    @OneToMany(mappedBy = "commentHike", fetch = FetchType.EAGER)
    public List<Comment> getHikeComments() {
        return hikeComments;
    }
    public void setHikeComments(List<Comment> hikeComments) {
        this.hikeComments = hikeComments;
    }

    @Override
    public String toString() {
        return "Hike{" +
                "hikeId='" + hikeId + '\'' +
                ", hikeName='" + hikeName + '\'' +
                ", hikeDescription='" + hikeDescription + '\'' +
                ", hikeRouteCoordinates='" + hikeRouteCoordinates + '\'' +
                ", hikeDuration=" + hikeDuration +
                ", hikeAltitude=" + hikeAltitude +
                ", hikeDistance=" + hikeDistance +
                ", hikeStamina=" + hikeStamina +
                ", hikeStrength=" + hikeStrength +
                ", hikeDifficulty=" + hikeDifficulty +
                ", hikeLandscape=" + hikeLandscape +
                ", hikeImage='" + hikeImage + '\'' +
                ", hikeMonths='" + hikeMonths + '\'' +
                ", hikeRegion=" + hikeRegion.getRegionName() +
                ", hikePointsOfInterest=" + hikePointsOfInterest +
                ", hikeComments=" + hikeComments +
                ", isDeleted=" + isDeleted +
                ", hikeOfUser=" + hikeOfUser.getUserName() +
                '}';
    }
}