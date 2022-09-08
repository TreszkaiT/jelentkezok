package com.example.app.data.dto;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class StudyDTO {


    private String nameSchool;

    private LocalDate formDate;


    private LocalDate toDate;

    private String comment;

    @ManyToOne
    private PersonDTO personDTO;

    public StudyDTO() {
    }

    public String getNameSchool() {
        return nameSchool;
    }

    public void setNameSchool(String nameSchool) {
        this.nameSchool = nameSchool;
    }

    public LocalDate getFormDate() {
        return formDate;
    }

    public void setFormDate(LocalDate from) {
        this.formDate = from;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate to) {
        this.toDate = to;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }
}
