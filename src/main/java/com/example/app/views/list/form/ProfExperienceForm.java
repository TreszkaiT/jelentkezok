package com.example.app.views.list.form;

import com.example.app.data.entity.ProfExperience;
import com.example.app.exception.InvalidBeanWriteException;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfExperienceForm extends FormLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfExperienceForm.class);

    Binder<ProfExperience> binder = new BeanValidationBinder<>(ProfExperience.class);
    TextField nameWork = new TextField("Szakmai Tapasztalat neve");
    DatePicker formDate = new DatePicker("Tól");
    DatePicker toDate = new DatePicker("ig");
    TextArea comment = new TextArea("Megjegyzés");
    ProfExperience profExperience;

    public ProfExperienceForm(ProfExperience profExperience) {
        this.profExperience = profExperience;

        nameWork.focus();

        add(nameWork, formDate, toDate, comment);
        binder.bindInstanceFields(this);
        binder.readBean(profExperience);
    }

    public void save() {
        try {
            binder.writeBean(profExperience);
        } catch (ValidationException e) {
            //LOGGER.error(e.getMessage(), e);
            throw new InvalidBeanWriteException("A szkamai tapasztalatok mentése közben hiba történt!");
        }
    }
}

