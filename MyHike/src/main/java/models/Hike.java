package models;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "hike", schema = "MyHike")
public class Hike {
    private Integer hikeId;
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
    private List<Month> recommendedMonths;
    private Region hikeRegion;

    @Id
    @Column(name = "hike_id")
    public Integer getHikeId() {
        return hikeId;
    }
    public void setHikeId(Integer hikeId) {
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



    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} ,fetch = FetchType.EAGER)
    @JoinTable(name = "recommended_in", joinColumns = @JoinColumn(name="hike_id"), inverseJoinColumns = @JoinColumn(name="month_id"))
    public List<Month> getRecommendedMonths() {
        return recommendedMonths;
    }
    public void setRecommendedMonths(List<Month> recommendedMonths) {
        this.recommendedMonths = recommendedMonths;
    }

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
                "hikeId=" + hikeId +
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
                ", recommendedMonths=" + recommendedMonths +
                ", hikeRegion=" + hikeRegion +
                '}';
    }
}