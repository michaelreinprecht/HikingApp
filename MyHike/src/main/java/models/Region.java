package models;

import javax.persistence.*;

@Entity
@Table(name = "MyHike.region")
public class Region {
    private String regionName;

    @Id
    @Column(name = "region_name", length = 40)
    public  String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}