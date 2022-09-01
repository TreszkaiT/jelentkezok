package com.example.app.viewcontroller;

import com.example.app.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);        // az osztályhoz elkérek egy loggert,

    private static AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;

    }

}
