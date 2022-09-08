package com.example.app.data.dto;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.ProfExperience;
import com.example.app.data.entity.Study;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonDTO {

    private String firstName = "";

    private String lastName = "";

    private String email = "";
    //@NotEmpty

    private LocalDate bornDate;

    private String phone = "";


    private String address = "";

    private City city;


    private String socialMedia = "";

    private String messageApps = "";

    private String webSite = "";


    private String picture = "";


    private List<Study> studies = new ArrayList<>();

    private List<ProfExperience> profExperiences = new ArrayList<>();


    private String otherSkill = "";


    private String coverLetter = "";

    private Set<Language> language = new HashSet<>();


    public PersonDTO() {
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public LocalDate getbornDate() {
        return bornDate;
    }

    public void setbornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public City getcity() {
        return city;
    }

    public void setcity(City city) {
        this.city = city;
    }

    public String getsocialMedia() {
        return socialMedia;
    }

    public void setsocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getmessageApps() {
        return messageApps;
    }

    public void setmessageApps(String messageApps) {
        this.messageApps = messageApps;
    }

    public String getwebSite() {
        return webSite;
    }

    public void setwebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getpicture() {
        return picture;
    }

    public void setpicture(String picture) {
        this.picture = picture;
    }

    public List<Study> getstudies() {
        return studies;
    }

    public void setstudies(List<Study> studies) {
        this.studies = studies;
    }

    public List<ProfExperience> getProfExperiences() {
        return profExperiences;
    }

    public void setProfExperiences(List<ProfExperience> profExperience) {
        this.profExperiences = profExperience;
    }

    public Set<Language> getlanguage() {
        return language;
    }

    public void setlanguage(Set<Language> language) {
        this.language = language;
    }

    public String getotherSkill() {
        return otherSkill;
    }

    public void setotherSkill(String otherSkill) {
        this.otherSkill = otherSkill;
    }

    public String getcoverLetter() {
        return coverLetter;
    }

    public void setcoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
}
