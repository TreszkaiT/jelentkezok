package com.example.app.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="ADT_STUDY")
public class Study extends AbstractEntity{

    @Column(name = "STD_NAME_SCHOOL")
    private String nameSchool;
    @Column(name = "STD_FROM")
    private LocalDate from;

    @Column(name = "STD_TO")
    private LocalDate to;
    @Column(name = "STD_COMMENT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "STD_PERSON_ID")
    private Person person;

    public Study() {
    }

    public String getNameSchool() {
        return nameSchool;
    }

    public void setNameSchool(String nameSchool) {
        this.nameSchool = nameSchool;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
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
