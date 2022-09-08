package com.example.app.viewcontroller;

import com.example.app.data.dto.LanguageDTO;
import com.example.app.data.dto.PersonDTO;
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

    public static List<LanguageDTO> getLanguage() { return StartingDataService.getLanguage(); }

    public static List<PersonDTO> getPersonSerializer() { return StartingDataService.getPersonSerializer(); }
}

