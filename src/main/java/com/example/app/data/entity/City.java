package com.example.app.data.entity;

import javax.persistence.Entity;

@Entity
public class City extends AbstractEntity {

    private String name;
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
