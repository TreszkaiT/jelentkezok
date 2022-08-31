package com.example.app.views.pages;

import com.example.app.data.entity.Study;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudyForm extends FormLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyForm.class);

    Binder<Study> binder = new BeanValidationBinder<>(Study.class);
    TextField nameSchool = new TextField("Iskola neve");
    DatePicker formDate = new DatePicker("Tól");
    DatePicker toDate = new DatePicker("ig");
    TextArea comment = new TextArea("Megjegyzés");
    Study study;

    public StudyForm(Study study) {
        add(nameSchool, formDate, toDate, comment);
        binder.bindInstanceFields(this);
        this.study = study;
        binder.readBean(study);
    }

    public void save() {
        try {
            binder.writeBean(study);
        } catch (ValidationException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
