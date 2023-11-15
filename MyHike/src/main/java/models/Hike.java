package models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;

@Entity
@Table(name = "hike", schema = "MyHike")
public class Hike {
    private String hikeId;
    private String hikeName;
    private String hikeDescription;
    private BigDecimal hikeStartLon;
    private BigDecimal hikeStartLat;
    private BigDecimal hikeEndLon;
    private BigDecimal hikeEndLat;
    private Time hikeDuration;
    private Integer hikeAltitude;
    private BigDecimal hikeDistance;
    private Integer hikeStamina;
    private Integer hikeStrength;
    private Integer hikeDifficulty;
    private Integer hikeLandscape;
    private String hikeImage;
    //private List<Recommended> recommenedList;
    private String hikeMonths;
    private Region hikeRegion;

    public Hike() {}

    public Hike(String hikeId, String hikeName, String hikeDescription, BigDecimal hikeStartLon, BigDecimal hikeStartLat, BigDecimal hikeEndLon, BigDecimal hikeEndLat, Time hikeDuration, Integer hikeAltitude, BigDecimal hikeDistance, Integer hikeStamina, Integer hikeStrength, Integer hikeDifficulty, Integer hikeLandscape, String hikeImage, String hikeMonths, Region hikeRegion) {
        this.hikeId = hikeId;
        this.hikeName = hikeName;
        this.hikeDescription = hikeDescription;
        this.hikeStartLon = hikeStartLon;
        this.hikeStartLat = hikeStartLat;
        this.hikeEndLon = hikeEndLon;
        this.hikeEndLat = hikeEndLat;
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

    @Column(name = "hike_start_lon", precision = 9, scale = 6, nullable = false)
    public BigDecimal getHikeStartLon() {
        return hikeStartLon;
    }
    public void setHikeStartLon(BigDecimal hikeStartLon) {
        this.hikeStartLon = hikeStartLon;
    }

    @Column(name = "hike_start_lat", precision = 9, scale = 6, nullable = false)
    public BigDecimal getHikeStartLat() {
        return hikeStartLat;
    }
    public void setHikeStartLat(BigDecimal hikeStartLat) {
        this.hikeStartLat = hikeStartLat;
    }

    @Column(name = "hike_end_lon", precision = 9, scale = 6, nullable = false)
    public BigDecimal getHikeEndLon() {
        return hikeEndLon;
    }
    public void setHikeEndLon(BigDecimal hikeEndLon) {
        this.hikeEndLon = hikeEndLon;
    }

    @Column(name = "hike_end_lat", precision = 9, scale = 6, nullable = false)
    public BigDecimal getHikeEndLat() {
        return hikeEndLat;
    }
    public void setHikeEndLat(BigDecimal hikeEndLat) {
        this.hikeEndLat = hikeEndLat;
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

    /*
    @OneToMany(mappedBy = "hike", fetch = FetchType.EAGER)
    public List<Recommended> getRecommendedList() {
        return recommenedList;
    }
    public void setRecommendedList(List<Recommended> recommenedList) {
        this.recommenedList = recommenedList;
    }*/

    @ManyToOne
    @JoinColumn(name = "hike_region", referencedColumnName = "region_name")
    public Region getHikeRegion() {
        return hikeRegion;
    }
    public void setHikeRegion(Region hikeRegion) {
        this.hikeRegion = hikeRegion;
    }

    @Override
    public String toString() {
        return "Hike{" +
                "hikeId='" + hikeId + '\'' +
                ", hikeName='" + hikeName + '\'' +
                ", hikeDescription='" + hikeDescription + '\'' +
                ", hikeStartLon=" + hikeStartLon +
                ", hikeStartLat=" + hikeStartLat +
                ", hikeEndLon=" + hikeEndLon +
                ", hikeEndLat=" + hikeEndLat +
                ", hikeDuration=" + hikeDuration +
                ", hikeAltitude=" + hikeAltitude +
                ", hikeDistance=" + hikeDistance +
                ", hikeStamina=" + hikeStamina +
                ", hikeStrength=" + hikeStrength +
                ", hikeDifficulty=" + hikeDifficulty +
                ", hikeLandscape=" + hikeLandscape +
                ", hikeImage='" + hikeImage + '\'' +
                ", hikeMonths=" + hikeMonths +
                ", hikeRegion=" + hikeRegion +
                '}';
    }
}