package com.example.app.data.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="ADT_PROF_EXPERIENCE")
public class ProfExperience extends AbstractEntity{

    @Column(name = "STD_NAME_WORK")
    private String nameWork;
    @Column(name = "STD_FROM")
    private LocalDate fromDate;

    @Column(name = "STD_TO")
    private LocalDate toDate;
    @Column(name = "STD_COMMENT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "STD_PERSON_ID")
    private Person person;

    public ProfExperience() {
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
