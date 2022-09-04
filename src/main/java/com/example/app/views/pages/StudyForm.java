package com.example.app.views.pages;

import com.example.app.data.entity.Study;
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

public class StudyForm extends FormLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyForm.class);

    Binder<Study> binder = new BeanValidationBinder<>(Study.class);                             // StudyForm létrehozása, mert a Binderhez kell, hogy egy FormLayout-unk legyen

    TextField nameSchool = new TextField("Iskola neve");                                    // vagyis a Layout-on amin a binder dolgozik, be legyenek állítva ezek a fieldek!!  Azaz hogy default propertiek legyenek, mint ezek itt
    DatePicker formDate = new DatePicker("Tól");
    DatePicker toDate = new DatePicker("ig");
    TextArea comment = new TextArea("Megjegyzés");

    Study study;

    public StudyForm(Study study) {
        add(nameSchool, formDate, toDate, comment);
        binder.bindInstanceFields(this);                                    // itt történik meg a tényleges összecsatolás. Itt tudja meg ezt, hogy ő melyik Vaadin-os elemen fog dolgozni
        this.study = study;
        binder.readBean(study);                                                                 // aztán, mikor rácsatlakozott ő a formra, ézrékelte, leolvasta a mezőket, beolvassuk magát a Beant (study), amit itt fentebb konstruktorba kaptuk. És ezt a study-t kirakja ő a formra
    }

    public void save() {
        try {
            binder.writeBean(study);                                                            // a a binder a study-ba visszaírja itt a form értékét. Itt a study a Person egy csatolt eleme volt, a Study-nak is benne van a Person beállítva. és ---->> PersonForm validateAndSave()
        } catch (ValidationException e) {                                                       // lehetnek itt problémák -> try catch blokk
            //LOGGER.error(e.getMessage(), e);
            throw new InvalidBeanWriteException("A tanulmányok mentése közben hiba történt!");
        }
    }
}
