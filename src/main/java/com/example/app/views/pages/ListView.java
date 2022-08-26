package com.example.app.views.pages;

import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.entity.Person;
import com.example.app.service.AppService;
import com.example.app.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Route(value="", layout = MainLayout.class)
@PageTitle("Személyek listája")
public class ListView extends VerticalLayout {

    Grid<Person> grid = new Grid<>(Person.class);
    TextField filterTextName = new TextField();
    TextField filterTextNyelv = new TextField();
    DatePicker getFilterDateDate = new DatePicker();
    PersonForm form;
    private AppService service;

    public ListView(AppService service) {   // Autowire kell, hogy elérjük a nézetünket : (AppService service), ehhez kell még az updateList() metódust megírni itt

        this.service = service;

        addClassName("list-view");  // CSS osztálynév hozzáadása
        setSizeFull();

        configureGrid();        // grid létrehozása
        configureForm();

        // gridbe Components-ek pakolása
        add(
                getToolbar(),
                getContent()
        );

        updateList("");
        closeEditor();      // először bezárja a jobb oldali formot, mert nem kattintottunk semmilyen elemre a listában

    }

    private void closeEditor() {
        form.setPerson(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    // hogy frissítse a formot .. ekkor bemegyünk az adatbázisba, és fetch-eljük onnan az új adatokat
        // ezt a Toolbar-ba kell beírni
    private void updateList(String why) {
        LocalDate date = LocalDate.of(2022, 8, 23);

        if(why=="") grid.setItems(service.findAllPersons(filterTextName.getValue(), date, why));
        else if(why=="LANG") grid.setItems(service.findAllPersons(filterTextNyelv.getValue(), date, why));
        else if(why=="DATE") grid.setItems(service.findAllPersons("Date", getFilterDateDate.getValue(), why));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new PersonForm(service.findAllCities(), service.findAllNyelvismeret());//, service);    // Collections.emptyList(), Collections.emptyList());  -- az elején ez volt itt, mert még semmi nem volt az adatbázisba
        form.setWidth("25em");

        form.addListener(PersonForm.SaveEvent.class, this::savePerson);
        form.addListener(PersonForm.DeleteEvent.class, this::deletePerson);
        form.addListener(PersonForm.CloseEvent.class, e -> closeEditor());
    }

    private void savePerson(PersonForm.SaveEvent event){
        service.savePerson(event.getPerson());
        updateList("");
        closeEditor();
    }

    private void deletePerson(PersonForm.DeleteEvent event){
        service.deletePerson(event.getPerson());
        updateList("");
        closeEditor();
    }

    private Component getToolbar() {
        filterTextName.setPlaceholder("Keresés névre...");
        filterTextName.setClearButtonVisible(true);
        filterTextName.setPrefixComponent(VaadinIcon.SEARCH.create());
        filterTextName.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextName.addValueChangeListener(e -> updateList(""));       // ha beírok valami, akkor tegye azt be az adatbázsiba, ezért kell előtte a LAZY lassú figyelés, hogy legyen idő a lassú gépelésnél beírni az adatokat

        filterTextNyelv.setPlaceholder("Keresés nyelvre...");
        filterTextNyelv.setClearButtonVisible(true);
        filterTextNyelv.setPrefixComponent(VaadinIcon.SEARCH.create());
        filterTextNyelv.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextNyelv.addValueChangeListener(event -> updateList("LANG"));

        getFilterDateDate.setPlaceholder("Keresés dátumra...");
        getFilterDateDate.setClearButtonVisible(true);
        getFilterDateDate.addValueChangeListener(event -> updateList("DATE"));

        Button addPersonButton = new Button("Új önéletrajz");
        addPersonButton.addClickListener(e -> addPerson());

        HorizontalLayout toolbar = new HorizontalLayout(filterTextName, filterTextNyelv, getFilterDateDate, addPersonButton);
        toolbar.addClassName("toolbar");    // CSS stílus miatt CSS osztálynév hozzáadása
        return toolbar;
    }

    private void addPerson() {
        grid.asSingleSelect().clear();
        editPerson(new Person());
    }

    private void configureGrid() {
        grid.addClassName("person-grid");  // CSS osztálynév hozzáadása
        grid.setSizeFull();
        //grid.setColumns("firstName", "lastName", "email", "phone");
        grid.setColumns();
        grid.addColumn(person -> person.getfirstName()).setHeader("Vezetéknév");
        grid.addColumn(person -> person.getlastName()).setHeader("Keresztnév");
        grid.addColumn(person -> person.getemail()).setHeader("Email");
        grid.addColumn(person -> person.getphone()).setHeader("Telefonszám");
        grid.addColumn(new LocalDateRenderer<>(Person::getszulDatum, "YYYY. MM .dd.")).setHeader("Születési idő");
        grid.addColumn(person -> person.getnyelvIsmeret().stream().map(Nyelvismeret::getName).collect(Collectors.joining(", "))).setHeader("Nyelvismeret"); // LAMBDA ->
        grid.getColumns().forEach(col -> col.setAutoWidth(true));   // show the contents

        grid.asSingleSelect().addValueChangeListener(e -> editPerson(e.getValue()));            // egy sorra kattintáskor
    }

    private void editPerson(Person person) {
        if(person == null){
            closeEditor();
        }else {
            form.setPerson(person);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
