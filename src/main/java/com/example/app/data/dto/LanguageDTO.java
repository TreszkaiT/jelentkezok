package com.example.app.data.dto;

import com.example.app.data.entity.Person;

import java.util.HashSet;
import java.util.Set;

public class LanguageDTO {

    private String name;

    private String code;

    private Set<Person> person = new HashSet<>();

    public LanguageDTO() {

    }

    public LanguageDTO(String name) {
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
