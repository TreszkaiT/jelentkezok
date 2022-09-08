package com.example.app.views.dataup;

import com.example.app.data.dto.*;
import com.example.app.data.entity.*;
import com.example.app.data.excel.ExcelXlsAndXlsxRead;
import com.example.app.data.properties.GetProperties;
import com.example.app.viewcontroller.AppController;
import com.example.app.viewcontroller.StartingDataController;
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
public class StartingDataUploadView extends VerticalLayout {

    private StartingDataController startingDataController;
    private AppController appController;

    Button buttonLanguageUpload = new Button("Nyelvek feltöltése");
    Button buttonCityUpload = new Button("Városok feltöltése");
    Button buttonPersonUpload = new Button("Emberek feltöltése");
    ProgressBar progressBarLanguage = new ProgressBar();
    ProgressBar progressBarCity = new ProgressBar();
    ProgressBar progressBarPerson = new ProgressBar();

    public static int ConfigLanguageButton;
    public static int ConfigCityButton;
    public static int ConfigPersonButton;

    public StartingDataUploadView(AppController appController, StartingDataController startingDataController) {
        this.appController = appController;
        this.startingDataController = startingDataController;


        List<LanguageDTO> languages = startingDataController.getLanguage();
        List<PersonDTO> persons = startingDataController.getPersonSerializer();

        addClassName("starting-data-upload");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(LanguageFillButton(), CityFillButton(), PersonFillButton());


        GetProperties properties = new GetProperties();
        properties.getAppPropValues();

        PropertiesNull();                           // kezdeti értékek beállítása a proerties fileba, ha az adatbázisba még nincs beírva leglább 10 sor -> biztos még nem nyomott a gombra hogy írja be

        LangButtonClick(languages);                 // Nyelv adatok beírása az adatbázisba

        CityButtonClick();                          // City adatok beírása az adatbázisba

        PersonButtonClick(persons);                 // Person adatok beírása az adatbázisba

    }

    private void LangButtonClick(List<LanguageDTO> languages) {
        if (ConfigLanguageButton == 2) buttonLanguageUpload.setEnabled(false);

        buttonLanguageUpload.addClickListener(event -> {
            progressBarLanguage.setIndeterminate(true);

            new Thread(() -> {

                appController.saveLanguage(languages);

            }).start();
            progressBarLanguage.setIndeterminate(false);
            buttonLanguageUpload.setEnabled(false);

            if (!buttonLanguageUpload.isEnabled() && !buttonCityUpload.isEnabled()) buttonPersonUpload.setEnabled(true);
        });
    }

    private void CityButtonClick() {
        if (ConfigCityButton == 2) buttonCityUpload.setEnabled(false);

        buttonCityUpload.addClickListener(event -> {
            progressBarCity.setIndeterminate(true);
            new Thread(() -> {

                String[][] dataTable = ExcelXlsAndXlsxRead.getEXlsXlsx("telepulesek.xlsx", 0);        // read from cells -- .xls
                List<CityDTO> cities = new ArrayList<>();
                for (int i = 0; i < dataTable.length; i++) {
                    if (dataTable[i][1] == null || dataTable[i][1].isEmpty() || dataTable[i][1].equals("")) {
                    } else {
                        cities.add(new CityDTO(dataTable[i][1], dataTable[i][0]));
                    }
                }

                //if(cities==null) System.out.println("null"); else service.saveCities(cities);
                if (cities != null) appController.saveCities(cities);

            }).start();
            progressBarCity.setIndeterminate(false);
            buttonCityUpload.setEnabled(false);

            if (!buttonLanguageUpload.isEnabled() && !buttonCityUpload.isEnabled()) buttonPersonUpload.setEnabled(true);
        });
    }

    private void PersonButtonClick(List<PersonDTO> persons) {
        if (ConfigCityButton == 1 || ConfigLanguageButton == 1)
            buttonPersonUpload.setEnabled(false);      // addig nem lehet aktív, míg a másik két táblát fel nem töltöttük, foreign key-ek miatt
        if (ConfigPersonButton == 2) buttonPersonUpload.setEnabled(false);

        buttonPersonUpload.addClickListener(event -> {
            progressBarPerson.setIndeterminate(true);
            List<PersonDTO> personsok = new ArrayList<>();
            new Thread(() -> {
                for (PersonDTO pers : persons) {
                    long rndCity = new RandomDataGenerator().nextLong(0, appController.countCities());
                    long rndlang = new RandomDataGenerator().nextLong(0, appController.countLanguage());
                    long rndlang2 = new RandomDataGenerator().nextLong(0, appController.countLanguage());
                    //if(pers.getcity()==null) pers.System.out.println("null city");
                    //if(pers.getlanguage()==null) System.out.println("null lang");
                    pers.setCityDTO(appController.findAllCities().get((int) rndCity));//findByCity("1"));
                    pers.getLanguageDTO().add(appController.findAllLanguage().get((int) rndlang));
                    pers.getLanguageDTO().add(appController.findAllLanguage().get((int) rndlang2));

                    {
                        StudyDTO study = new StudyDTO();
                        study.setNameSchool("Test1 Iskola");
                        study.setFormDate(LocalDate.of(1977, 11, 12));
                        study.setToDate(LocalDate.of(1978, 11, 12));
                        pers.getStudiesDTO().add(study);
                        study.setPersonDTO(pers);
                    }
                    {
                        StudyDTO study = new StudyDTO();
                        study.setNameSchool("Maosidik Iskola");
                        study.setFormDate(LocalDate.of(1979, 9, 12));
                        study.setToDate(LocalDate.of(1981, 11, 12));
                        study.setComment("ez egy megjegyzes");
                        pers.getStudiesDTO().add(study);
                        study.setPersonDTO(pers);
                    }

                    {
                        ProfExperienceDTO proof = new ProfExperienceDTO();
                        proof.setNameWork("Első");
                        proof.setFromDate(LocalDate.of(1977,11,12));
                        proof.setToDate(LocalDate.of(1978,11,12));
                        proof.setComment("Valami");
                        pers.getProfExperiencesDTO().add(proof);
                        proof.setPersonDTO(pers);
                    }
                    {
                        ProfExperienceDTO proof = new ProfExperienceDTO();
                        proof.setNameWork("Második");
                        proof.setFromDate(LocalDate.of(1979,9,12));
                        proof.setToDate(LocalDate.of(1981,11,12));
                        proof.setComment("Valami2");
                        pers.getProfExperiencesDTO().add(proof);
                        proof.setPersonDTO(pers);
                    }
                    //pers.setcity(service.findCityByName("Nyíregyháza"));
                    //pers.setlanguage(service.findLanguageByName("English"));
                    personsok.add(pers);
                }
                appController.savePerson(personsok);
            }).start();
            progressBarPerson.setIndeterminate(false);
            buttonPersonUpload.setEnabled(false);
        });
    }

    /**
     * kezdeti értékek beállítása a proerties fileba, ha az adatbázisba még nincs beírva leglább 10 sor -> biztos még nem nyomott a gombra hogy írja be
     */
    private void PropertiesNull() {
        if (appController.findAllLanguage().size() < 10) ConfigLanguageButton = 1;
        else ConfigLanguageButton = 2;
        if (appController.findAllCities().size() < 10) ConfigCityButton = 1;
        else ConfigCityButton = 2;
        if (appController.findAllPersons().size() < 2) ConfigPersonButton = 1;
        else ConfigPersonButton = 2;
        SetButtonAppPropertyValue(ConfigLanguageButton, ConfigCityButton, ConfigPersonButton);

    }

    private Component LanguageFillButton() {
        HorizontalLayout progresLayout = new HorizontalLayout();
        //progressBar.setVisible(false);
        buttonLanguageUpload.setDisableOnClick(true);
        progresLayout.add(buttonLanguageUpload, progressBarLanguage);
        progresLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return progresLayout;
    }

    private Component CityFillButton() {
        HorizontalLayout layout2 = new HorizontalLayout();
        buttonCityUpload.setDisableOnClick(true);
        layout2.add(buttonCityUpload, progressBarCity);
        layout2.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout2;
    }

    private Component PersonFillButton() {
        HorizontalLayout layout3 = new HorizontalLayout();
        buttonPersonUpload.setDisableOnClick(true);
        layout3.add(buttonPersonUpload, progressBarPerson);
        layout3.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout3;
    }


}
