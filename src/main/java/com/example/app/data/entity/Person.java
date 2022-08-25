package com.example.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


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
    private LocalDate szulDatum;
    @NotEmpty
    private String phone = "";

    @NotEmpty
    private String address = "";
    @NotNull
    @ManyToOne
    private City city;

    @NotEmpty
    private String kozMedia = "";
    @NotEmpty
    private String messageApps = "";
    @NotEmpty
    private String webSite = "";

    @NotEmpty
    private String picture = "";

    @NotEmpty
    private String tanulmanyok = "";

    @NotEmpty
    private String szakmaiTap = "";

    @NotEmpty
    private String egyebKeszsegek = "";

    @NotEmpty
    private String motivaciosLevel = "";

    // magától a Set Stringek halmazát nem tudja betenni az adatbázisba, sőt Exceptionnal el is száll az alkalmazás
    // így ezt az Annotációt rá kell tenni. Azaz csinál hozzá egy külön kis táblát, és abból lesznek hozzácsatolva az egyes műfajok a Movies tábla adott filmjéhez
    //@ElementCollection
    //@NotNull
    //@ManyToOne
    //private Nyelvismeret nyelvIsmeret;
    @NotNull
    @OneToMany
    private List<PersonNyelv> personNyelvList;
    //private PersonNyelv personNyelv;

    //@ElementCollection
    //private Set<String> szakmaiTap;



    public Person() {}

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public List<PersonNyelv> getPersonNyelvList() {
        return personNyelvList;
    }

    public void setPersonNyelvList(List<PersonNyelv> personNyelvList) {
        this.personNyelvList = personNyelvList;
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

    public LocalDate getszulDatum() {
        return szulDatum;
    }

    public void setszulDatum(LocalDate szulDatum) {
        this.szulDatum = szulDatum;
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

    public String getkozMedia() {
        return kozMedia;
    }

    public void setkozMedia(String kozMedia) {
        this.kozMedia = kozMedia;
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

    public String gettanulmanyok() {
        return tanulmanyok;
    }

    public void settanulmanyok(String tanulmanyok) {
        this.tanulmanyok = tanulmanyok;
    }

    public String getszakmaiTap() {
        return szakmaiTap;
    }

    public void setszakmaiTap(String szakmaiTap) {
        this.szakmaiTap = szakmaiTap;
    }

  /*  public Nyelvismeret getnyelvIsmeret() {
        return nyelvIsmeret;
    }

    public void setnyelvIsmeret(Nyelvismeret nyelvIsmeret) {
        this.nyelvIsmeret = nyelvIsmeret;
    }
*/
    public String getegyebKeszsegek() {
        return egyebKeszsegek;
    }

    public void setegyebKeszsegek(String egyebKeszsegek) {
        this.egyebKeszsegek = egyebKeszsegek;
    }

    public String getmotivaciosLevel() {
        return motivaciosLevel;
    }

    public void setmotivaciosLevel(String motivaciosLevel) {
        this.motivaciosLevel = motivaciosLevel;
    }







}
