package com.example.app.data.dto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonDTO extends AbstractDTO {

    private String firstName = "";

    private String lastName = "";

    private String email = "";

    private LocalDate bornDate;

    private String phone = "";


    private String address = "";

    @ManyToOne
    private CityDTO cityDTO;


    private String socialMedia = "";

    private String messageApps = "";

    private String webSite = "";


    private String picture = "";


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StudyDTO> studiesDTO = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProfExperienceDTO> profExperiencesDTO = new ArrayList<>();


    private String otherSkill = "";


    private String coverLetter = "";

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<LanguageDTO> languageDTO = new HashSet<>();


    public PersonDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CityDTO getCityDTO() {
        return cityDTO;
    }

    public void setCityDTO(CityDTO cityDTO) {
        this.cityDTO = cityDTO;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getMessageApps() {
        return messageApps;
    }

    public void setMessageApps(String messageApps) {
        this.messageApps = messageApps;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<StudyDTO> getStudiesDTO() {
        return studiesDTO;
    }

    public void setStudiesDTO(List<StudyDTO> studiesDTO) {
        this.studiesDTO = studiesDTO;
    }

    public List<ProfExperienceDTO> getProfExperiencesDTO() {
        return profExperiencesDTO;
    }

    public void setProfExperiencesDTO(List<ProfExperienceDTO> profExperiencesDTO) {
        this.profExperiencesDTO = profExperiencesDTO;
    }

    public String getOtherSkill() {
        return otherSkill;
    }

    public void setOtherSkill(String otherSkill) {
        this.otherSkill = otherSkill;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Set<LanguageDTO> getLanguageDTO() {
        return languageDTO;
    }

    public void setLanguageDTO(Set<LanguageDTO> languageDTO) {
        this.languageDTO = languageDTO;
    }
}
