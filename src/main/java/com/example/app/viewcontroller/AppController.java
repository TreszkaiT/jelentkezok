package com.example.app.viewcontroller;

import com.example.app.data.dto.CityDTO;
import com.example.app.data.dto.LanguageDTO;
import com.example.app.data.dto.PersonDTO;
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


    //COUNT
    public long countPersons(){
        return appService.countPersons();
    }
    public long countCities(){
        return appService.countCities();
    }
    public long countLanguage(){
        return appService.countLanguage();
    }

    //DELETE
    public void deletePerson(PersonDTO personDTO) { appService.deletePerson(personDTO); }

    //SAVE
    public  void saveCities(List<CityDTO> cities) { appService.saveCities(cities); }
    public void saveLanguage(List<LanguageDTO> languages) { appService.saveLanguage(languages); }
    public void savePerson(List<PersonDTO> persons){ appService.savePerson(persons); }
    public void savePerson(PersonDTO personDTO){ appService.savePerson(personDTO); }

    // Find
    public List<PersonDTO> findAllPersons(){ return appService.findAllPersons(); }
    public List<CityDTO> findAllCities(){ return appService.findAllCities(); }
    public List<LanguageDTO> findAllLanguage(){ return appService.findAllLanguage(); }
    public List<PersonDTO> findAllPersons(String filterName, LocalDate dt, String filterLang){
        return appService.findAllPersons(filterName, dt, filterLang);
    }

}
