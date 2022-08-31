package com.example.app.data.entity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class ProfExperience extends AbstractEntity{

    private String nameWork;
    private LocalDate from;
    private LocalDate to;
    private String comment;

    public ProfExperience() {
    }


    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
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
}
