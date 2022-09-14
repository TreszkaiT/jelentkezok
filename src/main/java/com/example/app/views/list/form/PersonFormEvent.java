package com.example.app.views.list.form;

import com.example.app.data.entity.Person;
import com.vaadin.flow.component.ComponentEvent;

public class PersonFormEvent extends ComponentEvent<PersonForm> {
    private Person person;

    protected PersonFormEvent(PersonForm source, Person person) {
        super(source, false);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}

