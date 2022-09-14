package com.example.app.views.list.form;

import com.example.app.data.entity.Person;

public final class DeleteEvent extends PersonFormEvent {
    DeleteEvent(PersonForm source, Person person) {
        super(source, person);
    }

}
