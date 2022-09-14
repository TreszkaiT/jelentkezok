package com.example.app.views.list.form;

import com.example.app.data.entity.Person;

public final class SaveEvent extends PersonFormEvent {
    SaveEvent(PersonForm source, Person person) {
        super(source, person);
    }
}
