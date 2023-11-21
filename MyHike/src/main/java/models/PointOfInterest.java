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
    private Double pointOfInterestLon;
    private Double pointOfInterestLat;

    public PointOfInterest() {}

    public PointOfInterest(String pointOfInterestId, String pointOfInterestName, Double pointOfInterestLon, Double pointOfInterestLat) {
        this.pointOfInterestId = pointOfInterestId;
        this.pointOfInterestName = pointOfInterestName;
        this.pointOfInterestLon = pointOfInterestLon;
        this.pointOfInterestLat = pointOfInterestLat;
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

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "pointOfInterestId='" + pointOfInterestId + '\'' +
                ", pointOfInterestName='" + pointOfInterestName + '\'' +
                ", pointOfInterestLon=" + pointOfInterestLon +
                ", pointOfInterestLat=" + pointOfInterestLat +
                '}';
    }
}