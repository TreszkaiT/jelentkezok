package com.example.app.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADT_CITY")
public class City extends AbstractEntity {

    @Column(name = "CTY_NAME")
    private String name;
    @Column(name = "CTY_ZIP")
    private String zip;

    public City() {

    }

    public City(String name, String zip) {
        this.name = name;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
