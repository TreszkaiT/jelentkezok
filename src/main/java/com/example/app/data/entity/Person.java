package com.example.app.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Person extends AbstractEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)             // azt mondja meg, hogy hogyan generálódjon egy ilyen Entitás Id-ja. Megmondjuk, hogy milyen stratégiát használjon ehhez. Auto=AutoIncrement lesz
    //private Long id;
    @NotEmpty
    private String firstName = "";
    @NotEmpty
    private String lastName = "";
    @Email
    @NotEmpty
    private String email = "";
    //@NotEmpty
    private LocalDate bornDate;
    @NotEmpty
    private String phone = "";

    @NotEmpty
    private String address = "";
    @NotNull
    @ManyToOne
    private City city;

    //@NotEmpty
    private String socialMedia = "";
    //@NotEmpty
    private String messageApps = "";
    //@NotEmpty
    private String webSite = "";

    @NotEmpty
    private String picture = "";

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Study> studies = new ArrayList<>();

    @NotEmpty
    private String profExperience = "";

    //@NotEmpty
    private String otherSkill = "";

    @NotEmpty
    private String coverLetter = "";

    // magától a Set Stringek halmazát nem tudja betenni az adatbázisba, sőt Exceptionnal el is száll az alkalmazás
    // így ezt az Annotációt rá kell tenni. Azaz csinál hozzá egy külön kis táblát, és abból lesznek hozzácsatolva az egyes műfajok a Movies tábla adott filmjéhez
    //@ElementCollection
    //private Set<String> language = new HashSet<>();
    //@NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    // hogy előtöltse a kapcsolatot. Mert ha betöltődik az Entitás, azaz inkább egy proxy. Ha itt ráhívok egy language-re, akkro a proxy elpattint egy lekérdezést, és belekérdez, hogy mi tartozik ehhez. De ahhoz ennek egy élő Session-nak kell lennie. De a View felület kívül van. LAZY nélkül no Session hiba lesz
    private Set<Language> language = new HashSet<>();

    //@ElementCollection
    //private Set<String> profExperience;

    public Person() {
    }

    @Override
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

    public String getprofExperience() {
        return profExperience;
    }

    public void setprofExperience(String profExperience) {
        this.profExperience = profExperience;
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
