package com.example.app.viewcontroller;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.service.AppService;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppController {

    private static AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    public List<Person> findAllPersons(String filterName, LocalDate dt, String filterLang){
        return appService.findAllPersons(filterName, dt, filterLang);
    }
    public List<Person> findAllPersons(){ return appService.findAllPersons(); }
    public List<City> findAllCities(){ return appService.findAllCities(); }
    public List<Language> findAllLanguage(){ return appService.findAllLanguage(); }


    public void deletePerson(Person person) { appService.deletePerson(person); }



    public  void saveCities(List<City> cities) { appService.saveCities(cities); }
    public void saveLanguage(List<Language> languages) { appService.saveLanguage(languages); }
    public void savePerson(List<Person> persons){ appService.savePerson(persons); }
    public void savePerson(Person person){ appService.savePerson(person); }


    public long countPersons(){
        return appService.countPersons();
    }
    public long countCities(){
        return appService.countCities();
    }
    public long countLanguage(){
        return appService.countLanguage();
    }

}
