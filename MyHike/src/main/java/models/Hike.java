package models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;

@Entity
@Table(name = "hike", schema = "MyHike")
public class Hike {
    private Integer hikeId;
    private String hikeName;
    private String hikeDescription;
    private BigDecimal hikeStart;
    private BigDecimal hikeEnd;
    private Time hikeDuration;
    private Integer hikeAltitude;
    private BigDecimal hikeDistance;
    private Integer hikeStamina;
    private Integer hikeStrength;
    private Integer hikeDifficulty;
    private Integer hikeLandscape;
    private Month hikeMonths;
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

    @Column(name = "hike_start", precision = 9, scale = 6, nullable = false)
    public BigDecimal getHikeStart() {
        return hikeStart;
    }
    public void setHikeStart(BigDecimal hikeStart) {
        this.hikeStart = hikeStart;
    }

    @Column(name = "hike_end", precision = 9, scale = 6, nullable = false)
    public BigDecimal getHikeEnd() {
        return hikeEnd;
    }
    public void setHikeEnd(BigDecimal hikeEnd) {
        this.hikeEnd = hikeEnd;
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

    @ManyToOne
    @JoinColumn(name = "hike_months", referencedColumnName = "month_id")
    public Month getHikeMonths() {
        return hikeMonths;
    }
    public void setHikeMonths(Month hikeMonths) {
        this.hikeMonths = hikeMonths;
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
                ", hikeStart=" + hikeStart +
                ", hikeEnd=" + hikeEnd +
                ", hikeDuration=" + hikeDuration +
                ", hikeAltitude=" + hikeAltitude +
                ", hikeDistance=" + hikeDistance +
                ", hikeStamina=" + hikeStamina +
                ", hikeStrength=" + hikeStrength +
                ", hikeDifficulty=" + hikeDifficulty +
                ", hikeLandscape=" + hikeLandscape +
                ", hikeMonths=" + hikeMonths +
                ", hikeRegion=" + hikeRegion +
                '}';
    }
}