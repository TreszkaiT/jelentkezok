package com.example.app.data.dto;

public class CityDTO extends AbstractDTO{


    private String name;
    private String zip;

    public CityDTO() {

    }

    public CityDTO(String name, String zip) {
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
