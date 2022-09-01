package com.example.app.viewcontroller;

import com.example.app.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class DataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);        // az osztályhoz elkérek egy loggert,

    private static DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }


}

