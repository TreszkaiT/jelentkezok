package com.example.app.views.pages;

import com.example.app.data.entity.*;
import com.example.app.data.excel.ExcelXlsAndXlsxRead;
import com.example.app.data.properties.GetProperties;
import com.example.app.service.AppService;
import com.example.app.service.DataService;
import com.example.app.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.math3.random.RandomDataGenerator;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.data.properties.SetProperties.SetButtonAppPropertyValue;

@Route(value = "startingdataupload", layout = MainLayout.class)
@PageTitle("Adatszótárak feltöltése")
@PermitAll          // login security miatt
public class StartingDataUpload extends VerticalLayout {

    private AppService service;
    private DataService serviceData;

    Button button1 = new Button("Nyelvek feltöltése");
    Button button2 = new Button("Városok feltöltése");
    Button button3 = new Button("Emberek feltöltése");
    ProgressBar progressBar1 = new ProgressBar();
    ProgressBar progressBar2 = new ProgressBar();
    ProgressBar progressBar3 = new ProgressBar();

    public static int ConfigLanguageButton;
    public static int ConfigCityButton;
    public static int ConfigPersonButton;

    public StartingDataUpload(AppService service, DataService serviceData) {
        this.service = service;
        this.serviceData = serviceData;

        List<Language> languages = serviceData.getLanguage();
        List<Person> persons = serviceData.getPersonSerializer();

        addClassName("starting-data-upload");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(LanguageFillButton(), CityFillButton(), PersonFillButton());


        GetProperties properties = new GetProperties();
        properties.getAppPropValues();
        // kezdeti értékek beállítása a proerties fileba, ha az adatbázisba még nincs beírva leglább 10 sor -> biztos még nem nyomott a gombra hogy írja be
        PropertiesNull();

        // Nyelv adatok beírása az adatbázisba
        LangButtonClick(languages);

        // City adatok beírása az adatbázisba
        CityButtonClick();

        // Person adatok beírása az adatbázisba
        PersonButtonClick(persons);

    }

    private void LangButtonClick(List<Language> languages) {
        if (ConfigLanguageButton == 2) button1.setEnabled(false);
        button1.addClickListener(event -> {
            progressBar1.setIndeterminate(true);

            new Thread(() -> {

                // ide jön a feltöltés

                service.saveLanguage(languages);

                //this.getUI()..access(() -> {
                //progressBar1.se
                //
                //});

            }).start();
            progressBar1.setIndeterminate(false);
            button1.setEnabled(false);
            if (!button1.isEnabled() && !button2.isEnabled()) button3.setEnabled(true);
        });
    }

    private void CityButtonClick() {
        if (ConfigCityButton == 2) button2.setEnabled(false);
        button2.addClickListener(event -> {
            progressBar2.setIndeterminate(true);
            new Thread(() -> {

                String[][] dataTable = ExcelXlsAndXlsxRead.getEXlsXlsx("telepulesek.xlsx", 0);        // read from cells -- .xls
                List<City> cities = new ArrayList<>();
                for (int i = 0; i < dataTable.length; i++) {
                    if (dataTable[i][1] == null || dataTable[i][1].isEmpty() || dataTable[i][1].equals("")) {
                    } else {
                        cities.add(new City(dataTable[i][1], dataTable[i][0]));
                    }
                }

                //if(cities==null) System.out.println("null"); else service.saveCities(cities);
                if (cities != null) service.saveCities(cities);

            }).start();
            progressBar2.setIndeterminate(false);
            button2.setEnabled(false);
            if (!button1.isEnabled() && !button2.isEnabled()) button3.setEnabled(true);
        });
    }

    private void PersonButtonClick(List<Person> persons) {
        if (ConfigCityButton == 1 || ConfigLanguageButton == 1)
            button3.setEnabled(false);      // addig nem lehet aktív, míg a másik két táblát fel nem töltöttük, foreign key-ek miatt
        if (ConfigPersonButton == 2) button3.setEnabled(false);
        button3.addClickListener(event -> {
            progressBar3.setIndeterminate(true);
            List<Person> personsok = new ArrayList<>();
            new Thread(() -> {
                for (Person pers : persons) {
                    long rndCity = new RandomDataGenerator().nextLong(0, service.countCities());
                    long rndlang = new RandomDataGenerator().nextLong(0, service.countLanguage());
                    long rndlang2 = new RandomDataGenerator().nextLong(0, service.countLanguage());
                    //if(pers.getcity()==null) pers.System.out.println("null city");
                    //if(pers.getlanguage()==null) System.out.println("null lang");
                    pers.setcity(service.findAllCities().get((int) rndCity));//findByCity("1"));
                    pers.getlanguage().add(service.findAllLanguage().get((int) rndlang));
                    pers.getlanguage().add(service.findAllLanguage().get((int) rndlang2));

                    {
                        Study study = new Study();
                        study.setNameSchool("Test1 Iskola");
                        study.setFormDate(LocalDate.of(1977, 11, 12));
                        study.setToDate(LocalDate.of(1978, 11, 12));
                        pers.getstudies().add(study);
                        study.setPerson(pers);
                    }
                    {
                        Study study = new Study();
                        study.setNameSchool("Maosidik Iskola");
                        study.setFormDate(LocalDate.of(1979, 9, 12));
                        study.setToDate(LocalDate.of(1981, 11, 12));
                        study.setComment("ez egy megjegyzes");
                        pers.getstudies().add(study);
                        study.setPerson(pers);
                    }

                    {
                        ProfExperience proof = new ProfExperience();
                        proof.setNameWork("Első");
                        proof.setFromDate(LocalDate.of(1977,11,12));
                        proof.setToDate(LocalDate.of(1978,11,12));
                        proof.setComment("Valami");
                        pers.getProfExperiences().add(proof);
                        proof.setPerson(pers);
                    }
                    {
                        ProfExperience proof = new ProfExperience();
                        proof.setNameWork("Második");
                        proof.setFromDate(LocalDate.of(1979,9,12));
                        proof.setToDate(LocalDate.of(1981,11,12));
                        proof.setComment("Valami2");
                        pers.getProfExperiences().add(proof);
                        proof.setPerson(pers);
                    }
                    //pers.setcity(service.findCityByName("Nyíregyháza"));
                    //pers.setlanguage(service.findLanguageByName("English"));
                    personsok.add(pers);
                }
                service.savePerson(personsok);
            }).start();
            progressBar3.setIndeterminate(false);
            button3.setEnabled(false);
        });
    }

    /**
     * kezdeti értékek beállítása a proerties fileba, ha az adatbázisba még nincs beírva leglább 10 sor -> biztos még nem nyomott a gombra hogy írja be
     */
    private void PropertiesNull() {
        if (service.findAllLanguage().size() < 10) ConfigLanguageButton = 1;
        else ConfigLanguageButton = 2;
        if (service.findAllCities().size() < 10) ConfigCityButton = 1;
        else ConfigCityButton = 2;
        if (service.findAllPersons().size() < 2) ConfigPersonButton = 1;
        else ConfigPersonButton = 2;
        SetButtonAppPropertyValue(ConfigLanguageButton, ConfigCityButton, ConfigPersonButton);

    }

    private Component LanguageFillButton() {
        HorizontalLayout progresLayout = new HorizontalLayout();
        //progressBar.setVisible(false);
        button1.setDisableOnClick(true);
        progresLayout.add(button1, progressBar1);
        progresLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return progresLayout;
    }

    private Component CityFillButton() {
        HorizontalLayout layout2 = new HorizontalLayout();
        button2.setDisableOnClick(true);
        layout2.add(button2, progressBar2);
        layout2.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout2;
    }

    private Component PersonFillButton() {
        HorizontalLayout layout3 = new HorizontalLayout();
        button3.setDisableOnClick(true);
        layout3.add(button3, progressBar3);
        layout3.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout3;
    }


}
