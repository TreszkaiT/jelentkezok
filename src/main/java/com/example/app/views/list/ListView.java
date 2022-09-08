package com.example.app.views.list;

import com.example.app.data.dto.LanguageDTO;
import com.example.app.data.dto.PersonDTO;
import com.example.app.viewcontroller.AppController;
import com.example.app.views.MainLayout;
import com.example.app.views.list.form.PersonForm;
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

import javax.annotation.security.PermitAll;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Személyek listája")
@PermitAll          // login security miatt
public class ListView extends VerticalLayout {

    Grid<PersonDTO> grid = new Grid<>(PersonDTO.class);
    TextField filterTextName = new TextField();
    TextField filterTextLang = new TextField();
    DatePicker getFilterDateDate = new DatePicker();
    PersonForm form;

    private AppController appController;

    public ListView(AppController appController) {   // Autowire kell, hogy elérjük a nézetünket : (AppService service), ehhez kell még az updateList() metódust megírni itt
        this.appController = appController;

        addClassName("list-view");  // CSS osztálynév hozzáadása
        setSizeFull();

        configureGrid();        // grid létrehozása
        configureForm();

        // gridbe Components-ek pakolása
        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();      // először bezárja a jobb oldali formot, mert nem kattintottunk semmilyen elemre a listában

        //this.getElement().getThemeList().add("dark");
    }

    private void configureGrid() {
        grid.addClassName("person-grid");  // CSS osztálynév hozzáadása
        grid.setSizeFull();
        //grid.setColumns("firstName", "lastName", "email", "phone");
        grid.setColumns();
        grid.addColumn(personDTO -> personDTO.getFirstName()).setHeader("Vezetéknév");
        grid.addColumn(personDTO -> personDTO.getLastName()).setHeader("Keresztnév");
        grid.addColumn(personDTO -> personDTO.getEmail()).setHeader("Email");
        grid.addColumn(personDTO -> personDTO.getPhone()).setHeader("Telefonszám");
        grid.addColumn(new LocalDateRenderer<>(PersonDTO::getBornDate, "YYYY. MM .dd.")).setHeader("Születési idő");
        grid.addColumn(personDTO -> personDTO.getLanguageDTO().stream().map(LanguageDTO::getName).collect(Collectors.joining(", "))).setHeader("Nyelvismeret"); // LAMBDA ->
        grid.getColumns().forEach(col -> col.setAutoWidth(true));   // show the contents

        grid.asSingleSelect().addValueChangeListener(e -> editPerson(e.getValue()));            // egy sorra kattintáskor
    }
    private void editPerson(PersonDTO personDTO) {
        if (personDTO == null) {
            closeEditor();
        } else {
            form.setPersonDTO(personDTO);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureForm() {
        form = new PersonForm(appController.findAllCities(), appController.findAllLanguage());    // Collections.emptyList(), Collections.emptyList());  -- az elején ez volt itt, mert még semmi nem volt az adatbázisba
        form.setWidth("25em");

        form.addListener(PersonForm.SaveEvent.class, this::savePerson);                         // Mentes 2.: itt egy listener, ami a SaveEvent-re van rárakva. És ha bejön ez, akkor this::savePerson ezt a metódust futtassa le itt, lentebb -->>
        form.addListener(PersonForm.DeleteEvent.class, this::deletePerson);
        form.addListener(PersonForm.CloseEvent.class, e -> closeEditor());
    }
    private void savePerson(PersonForm.SaveEvent event) {
        appController.savePerson(event.getPerson());                                            // Mentes 3.: és ebben van végül is a service. save  -->> appservice / savePerson()
        updateList();
        closeEditor();
        form.profExpNameClass.clear();
        form.studyNameClass.clear();
    }

    private void deletePerson(PersonForm.DeleteEvent event) {
        appController.deletePerson(event.getPerson());
        updateList();
        closeEditor();
    }
    private void closeEditor() {
        form.setPersonDTO(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    // hogy frissítse a formot .. ekkor bemegyünk az adatbázisba, és fetch-eljük onnan az új adatokat
    // ezt a Toolbar-ba kell beírni
    private void updateList() {
        //LocalDate date = LocalDate.of(2022, 8, 23);

        grid.setItems(appController.findAllPersons(filterTextName.getValue(), getFilterDateDate.getValue(), filterTextLang.getValue()));

        /*if(why=="") grid.setItems(appController.findAllPersons(filterTextName.getValue(), date, why));
        else if(why=="LANG") grid.setItems(appController.findAllPersons(filterTextLang.getValue(), date, why));
        else if(why=="DATE") grid.setItems(appController.findAllPersons("Date", getFilterDateDate.getValue(), why));*/
    }


    private Component getToolbar() {
        filterTextName.setPlaceholder("Keresés névre...");
        filterTextName.setClearButtonVisible(true);
        filterTextName.setPrefixComponent(VaadinIcon.SEARCH.create());
        filterTextName.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextName.addValueChangeListener(e -> updateList());       // ha beírok valami, akkor tegye azt be az adatbázsiba, ezért kell előtte a LAZY lassú figyelés, hogy legyen idő a lassú gépelésnél beírni az adatokat

        filterTextLang.setPlaceholder("Keresés nyelvre...");
        filterTextLang.setClearButtonVisible(true);
        filterTextLang.setPrefixComponent(VaadinIcon.SEARCH.create());
        filterTextLang.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextLang.addValueChangeListener(event -> updateList());

        getFilterDateDate.setPlaceholder("Keresés dátumra...");
        getFilterDateDate.setClearButtonVisible(true);
        getFilterDateDate.addValueChangeListener(event -> updateList());

        Button addPersonButton = new Button("Új önéletrajz");
        addPersonButton.addClickListener(e -> addPerson());

        HorizontalLayout toolbar = new HorizontalLayout(filterTextName, filterTextLang, getFilterDateDate, addPersonButton);
        toolbar.addClassName("toolbar");    // CSS stílus miatt CSS osztálynév hozzáadása
        return toolbar;
    }
    private void addPerson() {
        grid.asSingleSelect().clear();
        editPerson(new PersonDTO());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }














}
