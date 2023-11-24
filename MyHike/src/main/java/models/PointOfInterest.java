package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MyHike.pointOfInterest")
public class PointOfInterest {
    private String pointOfInterestId;
    private String pointOfInterestName;
    private String pointOfInterestDescription;
    private Double pointOfInterestLon;
    private Double pointOfInterestLat;
    private String pointOfInterestImage;

    public PointOfInterest() {}

    public PointOfInterest(String pointOfInterestId, String pointOfInterestName, String pointOfInterestDescription, Double pointOfInterestLon, Double pointOfInterestLat, String pointOfInterestImage) {
        this.pointOfInterestId = pointOfInterestId;
        this.pointOfInterestName = pointOfInterestName;
        this.pointOfInterestDescription = pointOfInterestDescription;
        this.pointOfInterestLon = pointOfInterestLon;
        this.pointOfInterestLat = pointOfInterestLat;
        this.pointOfInterestImage = pointOfInterestImage;
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

    @Column(name = "pointOfInterest_description", nullable = false, length = 200)
    public String getPointOfInterestDescription() {
        return pointOfInterestName;
    }
    public void setPointOfInterestDescription(String pointOfInterestDescription) {
        this.pointOfInterestDescription = pointOfInterestDescription;
    }

    @Column(name = "pointOfInterest_lon", nullable = false)
    public Double getPointOfInterestLon() {
        return pointOfInterestLon;
    }
    public void setPointOfInterestLon(Double pointOfInterestLon) {
        this.pointOfInterestLon = pointOfInterestLon;
    }

    @Column(name = "pointOfInterest_lat", nullable = false)
    public Double getPointOfInterestLat() {
        return pointOfInterestLat;
    }
    public void setPointOfInterestLat(Double pointOfInterestLat) {
        this.pointOfInterestLat = pointOfInterestLat;
    }

    @Column(name = "pointOfInterest_image", nullable = false)
    public String getPointOfInterestImage() {
        return pointOfInterestImage;
    }
    public void setPointOfInterestImage(String pointOfInterestImage) {
        this.pointOfInterestImage = pointOfInterestImage;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "pointOfInterestId='" + pointOfInterestId + '\'' +
                ", pointOfInterestName='" + pointOfInterestName + '\'' +
                ", pointOfInterestLon=" + pointOfInterestLon +
                ", pointOfInterestLat=" + pointOfInterestLat +
                ", pointOfInterestImage=" + pointOfInterestImage +
                '}';
    }
}