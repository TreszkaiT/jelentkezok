package com.example.app.views.pages;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
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

    public static int ConfigNyelvButton;
    public static int ConfigCityButton;
    public static int ConfigPersonButton;

    public StartingDataUpload(AppService service, DataService serviceData) {
        this.service = service;
        this.serviceData = serviceData;

        List<Language> nyelvismerets = serviceData.getNyelvismeret();
        List<Person> persons = serviceData.getPersonSerializer();

        addClassName("starting-data-upload");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(LanguageFillButton(), CityFillButton(), PersonFillButton());


        GetProperties properties = new GetProperties();
        properties.getAppPropValues();
        // kezdeti értékek beállítása a proerties fileba, ha az adatbázisba még nincs beírva leglább 10 sor -> biztos még nem nyomott a gombra hogy írja be
        PropertiesNull();

        // Nyelv adatok beírása az adatbázisba
        NyelvGombrakattintas(nyelvismerets);

        // City adatok beírása az adatbázisba
        VarosGombraKattintas();

        // Person adatok beírása az adatbázisba
        PersonGombraKattintas(persons);

    }

    private void NyelvGombrakattintas(List<Language> nyelvismerets) {
        if(ConfigNyelvButton==2) button1.setEnabled(false);
        button1.addClickListener(event -> {
            progressBar1.setIndeterminate(true);

            new Thread(() -> {

                // ide jön a feltöltés

                service.saveNyelvismeret(nyelvismerets);

                //this.getUI()..access(() -> {
                //progressBar1.se
                //
                //});

            }).start();
            progressBar1.setIndeterminate(false);
            button1.setEnabled(false);
            if(!button1.isEnabled() && !button2.isEnabled()) button3.setEnabled(true);
        });
    }

    private void VarosGombraKattintas() {
        if(ConfigCityButton==2) button2.setEnabled(false);
        button2.addClickListener(event -> {
            progressBar2.setIndeterminate(true);
            new Thread(() -> {

                String[][] dataTable = ExcelXlsAndXlsxRead.getEXlsXlsx("telepulesek.xlsx", 0);		// read from cells -- .xls
                List<City> cities = new ArrayList<>();
                for(int i=0; i<dataTable.length; i++){
                    if(dataTable[i][1]==null || dataTable[i][1].isEmpty() || dataTable[i][1].equals("")) {
                    }else{
                        cities.add(new City(dataTable[i][1], dataTable[i][0]));
                    }
                }

                //if(cities==null) System.out.println("null"); else service.saveCities(cities);
                if(cities!=null) service.saveCities(cities);

            }).start();
            progressBar2.setIndeterminate(false);
            button2.setEnabled(false);
            if(!button1.isEnabled() && !button2.isEnabled()) button3.setEnabled(true);
        });
    }

    private void PersonGombraKattintas(List<Person> persons) {
        if(ConfigCityButton==1 || ConfigNyelvButton==1) button3.setEnabled(false);      // addig nem lehet aktív, míg a másik két táblát fel nem töltöttük, foreign key-ek miatt
            if(ConfigPersonButton==2) button3.setEnabled(false);
        button3.addClickListener(event -> {
            progressBar3.setIndeterminate(true);
            List<Person> personsok = new ArrayList<>();
            new Thread(()->{
                for(Person pers : persons){
                    long rndCity = new RandomDataGenerator().nextLong(0, service.countCities());
                    long rndNyelv= new RandomDataGenerator().nextLong(0, service.countNyelvismeret());
                    long rndNyelv2= new RandomDataGenerator().nextLong(0, service.countNyelvismeret());
                    //if(pers.getcity()==null) pers.System.out.println("null city");
                    //if(pers.getnyelvIsmeret()==null) System.out.println("null nyelv");
                    pers.setcity(service.findAllCities().get((int)rndCity));//findByCity("1"));
                    pers.getnyelvIsmeret().add(service.findAllNyelvismeret().get((int)rndNyelv));
                    pers.getnyelvIsmeret().add(service.findAllNyelvismeret().get((int)rndNyelv2));
                    //pers.setcity(service.findCityByName("Nyíregyháza"));
                    //pers.setnyelvIsmeret(service.findNyelvismeretByName("English"));
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
    private void PropertiesNull(){
        if(service.findAllNyelvismeret().size()<10) ConfigNyelvButton=1; else ConfigNyelvButton=2;
        if(service.findAllCities().size()<10) ConfigCityButton=1; else ConfigCityButton=2;
        if(service.findAllPersons().size()<2) ConfigPersonButton=1; else ConfigPersonButton=2;
        SetButtonAppPropertyValue(ConfigNyelvButton,ConfigCityButton,ConfigPersonButton);

    }

    private Component LanguageFillButton() {
        HorizontalLayout progresLayout = new HorizontalLayout();
        //progressBar.setVisible(false);
        button1.setDisableOnClick(true);
        progresLayout.add(button1, progressBar1);
        progresLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return progresLayout;
    }

    private Component CityFillButton(){
        HorizontalLayout layout2 = new HorizontalLayout();
        button2.setDisableOnClick(true);
        layout2.add(button2, progressBar2);
        layout2.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout2;
    }

    private Component PersonFillButton(){
        HorizontalLayout layout3 = new HorizontalLayout();
        button3.setDisableOnClick(true);
        layout3.add(button3, progressBar3);
        layout3.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout3;
    }


}
