package com.example.app.views.pages;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.entity.Person;
//import com.example.app.data.repository.CityRepository;
//import com.example.app.data.repository.PersonRepository;
//import com.example.app.data.service.AppService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import org.vaadin.gatanaso.MultiselectComboBox;


import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;


public class PersonForm extends FormLayout {

    Binder<Person> binder = new BeanValidationBinder<>(Person.class);             // összekapcsol egy modul objektumor egy Components-el    BeanValidationBinder:a Person osztály validation Annotációit használja a formelemkre is, így már nekünk ezt nem kell megtenni a nézetnél külön

    TextField firstName         = new TextField("Vezetéknév");              // automatikus Bind-elés miatt a nevük itt egyezzen meg a Person osztályban lévő nevekkel!!!
    TextField lastName          = new TextField("Keresztnév");
    EmailField email            = new EmailField("Email");
    DatePicker szulDatum        = new DatePicker ("Születési dátum");
    TextField phone             = new TextField("Telefonszám");
    TextField address           = new TextField("Lakcím");
    TextField kozMedia          = new TextField("Közösségi média");
    TextField messageApps       = new TextField("Üzenetküldő appok");
    TextField webSite           = new TextField("Website");
    TextField tanulmanyok       = new TextField("Tanulmányok");
    TextField szakmaiTap        = new TextField("Szakmai Tapasztalat");
    TextField egyebKeszsegek    = new TextField("Egyéb készségek");
    TextField motivaciosLevel   = new TextField("Motivációs levél");
    TextField picture           = new TextField("Fénykép");

    ComboBox<Nyelvismeret>  nyelvIsmeret    = new ComboBox<>("Nyelvismeret");
    //MultiselectComboBox <Nyelvismeret>  nyelvIsmeret    = new MultiselectComboBox<>("Nyelvismeret");
    ComboBox<City>          city            = new ComboBox<>("Város");
    //MultiselectComboBox<City>          city            = new MultiselectComboBox<>("Város");

    Button save     = new Button("Save");
    Button delete   = new Button("Delete");
    Button cancel   = new Button("Cancel");

    private Person person;
    //private AppService service;

    public PersonForm(List<City> cities, List<Nyelvismeret> nyelvIsmeretek){//}, AppService services) {
        //this.service = services;
        addClassName("contact-form");
        binder.bindInstanceFields(this);        // a binder meghívása itt van. És this elég !!! azért, mert az itt lévő fenti nevek megyegyeznek a Person osztályban lévő nevekkel!!!

        city.setItems(cities);//service.findAllCities());//cities);
        city.setItemLabelGenerator(City::getName);                      // mit jelenítsünk meg a ComboBoxban

        nyelvIsmeret.setItems(nyelvIsmeretek);
        nyelvIsmeret.setItemLabelGenerator(Nyelvismeret::getName);

        add(
          firstName,
          lastName,
          email,
          szulDatum,
          phone,
          address,
          kozMedia,
          messageApps,
          webSite,
          tanulmanyok,
          szakmaiTap,
          egyebKeszsegek,
          motivaciosLevel,
          picture,
          city,
          nyelvIsmeret,
          createButtonLayout()
        );
    }

    public void setPerson(Person person){
        this.person = person;
        binder.readBean(person);        // Binder beolvassa ezt a persont, és ezek a fentebbi mezők az add( firstName, ...  ez alapján töltődnek fel
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, person)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
       // try{
            binder.readBean(person);
            fireEvent(new SaveEvent(this, person));
       // } catch (ValidationException e){
        //    e.printStackTrace();
       // }
    }

    // Events
    public static abstract class PersonFormEvent extends ComponentEvent<PersonForm> {
        private Person person;

        protected PersonFormEvent(PersonForm source, Person person) {
            super(source, false);
            this.person = person;
        }

        public Person getPerson() {
            return person;
        }
    }

    public static class SaveEvent extends PersonFormEvent {
        SaveEvent(PersonForm source, Person person) {
            super(source, person);
        }
    }

    public static class DeleteEvent extends PersonFormEvent {
        DeleteEvent(PersonForm source, Person person) {
            super(source, person);
        }

    }

    public static class CloseEvent extends PersonFormEvent {
        CloseEvent(PersonForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
