package com.example.app.data.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Language extends AbstractEntity {

    private String name;
    private String code;

    // azért, hogy az így létrehozott kapcsolótáblába ezen oldal felől is tujunk keresni
    @ManyToMany(fetch = FetchType.EAGER)
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
