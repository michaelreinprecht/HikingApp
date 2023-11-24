package models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "MyHike.pointOfInterest")
public class PointOfInterest {
    private String pointOfInterestId;
    private String pointOfInterestName;
    private String pointOfInterestDescription;
    private BigDecimal pointOfInterestLon;
    private BigDecimal pointOfInterestLat;
    private String pointOfInterestImage;
    private Hike pointOfInterestHike;

    public PointOfInterest() {}

    public PointOfInterest(String pointOfInterestId, String pointOfInterestName, String pointOfInterestDescription, BigDecimal pointOfInterestLon, BigDecimal pointOfInterestLat, String pointOfInterestImage, Hike pointOfInterestHike) {
        this.pointOfInterestId = pointOfInterestId;
        this.pointOfInterestName = pointOfInterestName;
        this.pointOfInterestDescription = pointOfInterestDescription;
        this.pointOfInterestLon = pointOfInterestLon;
        this.pointOfInterestLat = pointOfInterestLat;
        this.pointOfInterestImage = pointOfInterestImage;
        this.pointOfInterestHike = pointOfInterestHike;
    }

    @Id
    @Column(name = "pointOfInterest_id", nullable = false, length = 50)
    public String getPointOfInterestId() {
        return pointOfInterestId;
    }
    public void setPointOfInterestId(String pointOfInterestId) {
        this.pointOfInterestId = pointOfInterestId;
    }

    @Column(name = "pointOfInterest_name", nullable = false, length = 40)
    public String getPointOfInterestName() {
        return pointOfInterestName;
    }
    public void setPointOfInterestName(String pointOfInterestName) {
        this.pointOfInterestName = pointOfInterestName;
    }

    @Column(name = "pointOfInterest_description", length = 150)
    public String getPointOfInterestDescription() {
        return pointOfInterestDescription;
    }
    public void setPointOfInterestDescription(String pointOfInterestDescription) {
        this.pointOfInterestDescription = pointOfInterestDescription;
    }

    @Column(name = "pointOfInterest_lon", nullable = false)
    public BigDecimal getPointOfInterestLon() {
        return pointOfInterestLon;
    }
    public void setPointOfInterestLon(BigDecimal pointOfInterestLon) {
        this.pointOfInterestLon = pointOfInterestLon;
    }

    @Column(name = "pointOfInterest_lat", nullable = false)
    public BigDecimal getPointOfInterestLat() {
        return pointOfInterestLat;
    }
    public void setPointOfInterestLat(BigDecimal pointOfInterestLat) {
        this.pointOfInterestLat = pointOfInterestLat;
    }

    @Column(name = "pointOfInterest_image", nullable = false)
    public String getPointOfInterestImage() {
        return pointOfInterestImage;
    }
    public void setPointOfInterestImage(String pointOfInterestImage) {
        this.pointOfInterestImage = pointOfInterestImage;
    }

    @ManyToOne
    @JoinColumn(name = "pointOfInterest_hike", referencedColumnName = "hike_id")
    @JsonBackReference
    public Hike getPointOfInterestHike() {
        return pointOfInterestHike;
    }
    public void setPointOfInterestHike(Hike pointOfInterestHike) {
        this.pointOfInterestHike = pointOfInterestHike;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "pointOfInterestId='" + pointOfInterestId + '\'' +
                ", pointOfInterestName='" + pointOfInterestName + '\'' +
                ", pointOfInterestDescription='" + pointOfInterestDescription + '\'' +
                ", pointOfInterestLon=" + pointOfInterestLon +
                ", pointOfInterestLat=" + pointOfInterestLat +
                ", pointOfInterestImage='" + pointOfInterestImage + '\'' +
                ", pointOfInterestHike=" + pointOfInterestHike +
                '}';
    }
}