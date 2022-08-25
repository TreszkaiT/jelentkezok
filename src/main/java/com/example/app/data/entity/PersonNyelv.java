package com.example.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PersonNyelv extends AbstractEntity{

    @NotNull
    @ManyToOne
    private Person person;

    @NotNull
    @ManyToOne
    private Nyelvismeret nyelvIsmeret;

    public PersonNyelv() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Nyelvismeret getNyelvIsmeret() {
        return nyelvIsmeret;
    }

    public void setNyelvIsmeret(Nyelvismeret nyelvIsmeret) {
        this.nyelvIsmeret = nyelvIsmeret;
    }
}
