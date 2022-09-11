package com.example.app.data.entity;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ADT_LANGUAGE")
public class Language extends AbstractEntity {

    @Column(name = "LNG_NAME")
    private String name;
    @Column(name = "LNG_CODE")
    private String code;

    // azért, hogy az így létrehozott kapcsolótáblába ezen oldal felől is tujunk keresni
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    //@Column(name = "LNG_PERSON_ID")
    private Set<Person> person = new HashSet<>();

    public Language() {

    }

    public Language(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPerson() {
        return person;
    }

    public void setPerson(Set<Person> person) {
        this.person = person;
    }
}
