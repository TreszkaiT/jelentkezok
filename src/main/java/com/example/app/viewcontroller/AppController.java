package com.example.app.viewcontroller;

import com.example.app.data.entity.Person;
import com.example.app.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);        // az osztályhoz elkérek egy loggert,

    private static AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    public List<Person> findAllPersons(String filterName, LocalDate dt, String filterLang){
        return appService.findAllPersons(filterName, dt, filterLang);
    }

}
