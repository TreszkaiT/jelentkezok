package com.example.app.views.pages;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.excel.ExcelXlsAndXlsxRead;
import com.example.app.data.properties.GetProperties;
import com.example.app.data.service.AppService;
import com.example.app.data.service.DataService;
import com.example.app.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.data.properties.SetProperties.SetButtonAppPropertyValue;

@Route(value = "startingdataupload", layout = MainLayout.class)
@PageTitle("Adatszótárak feltöltése")
public class StartingDataUpload extends VerticalLayout {

    private AppService service;
    private DataService serviceData;

    Button button1 = new Button("Nyelvek feltöltése");
    Button button2 = new Button("Városok feltöltése");
    ProgressBar progressBar1 = new ProgressBar();
    ProgressBar progressBar2 = new ProgressBar();

    public static int ConfigNyelvButton;
    public static int ConfigCityButton;

    public StartingDataUpload(AppService service, DataService serviceData) {
        this.service = service;
        this.serviceData = serviceData;

        List<Nyelvismeret> nyelvismerets = serviceData.getNyelvismeret();
        //DataService.getNyelvismeret();

        addClassName("starting-data-upload");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(LanguageFillButton(), CityFillButton());


        GetProperties properties = new GetProperties();
        properties.getAppPropValues();
        // kezdeti értékek beállítása a proerties fileba, ha az adatbázisba még nincs beírva leglább 10 sor -> biztos még nem nyomott a gombra hogy írja be
        PropertiesNull();

        // Nyelv adatok beírása az adatbázisba
        if(ConfigNyelvButton==2) button1.setEnabled(false);
        button1.addClickListener(event -> {
            //if(!button1.isEnabled()) return;

            progressBar1.setIndeterminate(true);

            new Thread(() -> {

                // ide jön a feltöltés

                service.saveNyelvismeret(nyelvismerets);

                //this.getUI()..access(() -> {
                    //progressBar1.se
                    //
                //});

            }).start();
            //SetButtonAppPropertyValue(2,ConfigCityButton);
            progressBar1.setIndeterminate(false);
            button1.setEnabled(false);
            //PropertiesNull();

        });

        // City adatok beírása az adatbázisba
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

                if(cities==null) System.out.println("null"); else service.saveCities(cities);

            }).start();
            //SetButtonAppPropertyValue(ConfigNyelvButton,2);
            progressBar2.setIndeterminate(false);
            button2.setEnabled(false);
            //PropertiesNull();
        });

    }

    private void PropertiesNull(){
        if(service.findAllNyelvismeret().size()<10) ConfigNyelvButton=1; else ConfigNyelvButton=2;
        if(service.findAllCities().size()<10) ConfigCityButton=1; else ConfigCityButton=2;
        SetButtonAppPropertyValue(ConfigNyelvButton,ConfigCityButton);

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


}
