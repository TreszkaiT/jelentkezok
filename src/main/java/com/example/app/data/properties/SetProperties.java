package com.example.app.data.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SetProperties {

    public static void SetButtonAppPropertyValue(int ConfigNyelvButton, int ConfigCityButton){
        String path = "src/main/resources/data/conf.properties";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        try (OutputStream output = new FileOutputStream(absolutePath)){     //"D:\\java_intelliJ\\adatok\\src\\main\\resources\\conf.properties")){//;this.getClass().getResourceAsStream("/conf.properties"))){

            Properties prop = new Properties();

            prop.setProperty("feltoltes_nyelv_button", String.valueOf(ConfigNyelvButton));
            prop.setProperty("feltoltes_city_button", String.valueOf(ConfigCityButton));

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
