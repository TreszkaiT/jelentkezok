package com.example.app.data.dto;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

public class LanguageDTO extends AbstractDTO {

    private String name;

    private String code;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<PersonDTO> personDTO = new HashSet<>();

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

    public Set<PersonDTO> getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(Set<PersonDTO> personDTO) {
        this.personDTO = personDTO;
    }
}
