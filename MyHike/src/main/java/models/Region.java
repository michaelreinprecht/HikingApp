package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "MyHike.region")
public class Region {
    @Id
    @Column(name = "region_name", length = 40)
    private String regionName;

    public Region() {}

    public Region(String regionName) {
        this.regionName = regionName;
    }
}