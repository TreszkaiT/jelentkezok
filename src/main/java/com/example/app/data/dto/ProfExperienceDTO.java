package com.example.app.data.dto;

import com.example.app.data.entity.Person;

import java.time.LocalDate;

public class ProfExperienceDTO {


    private String nameWork;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String comment;

    private Person person;

    public ProfExperienceDTO() {
    }


    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
