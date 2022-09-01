package com.example.app.viewcontroller;

import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.service.StartingDataService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StartingDataController {

    private static StartingDataService startingDataService;

    public StartingDataController(StartingDataService startingDataService) {
        this.startingDataService = startingDataService;
    }

    public static List<Language> getLanguage() { return StartingDataService.getLanguage(); }

    public static List<Person> getPersonSerializer() { return StartingDataService.getPersonSerializer(); }
}

