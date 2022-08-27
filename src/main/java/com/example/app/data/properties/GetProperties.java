package com.example.app.data.properties;

import com.example.app.views.pages.StartingDataUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    public void getAppPropValues() {

        String path = "src/main/resources/data/conf.properties";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        try (InputStream input = new FileInputStream(absolutePath)){    //"D:\\java_intelliJ\\adatok\\src\\main\\resources\\conf.properties")) {

            Properties prop = new Properties();

            String propFileName = "conf.properties";

            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            StartingDataUpload.ConfigLanguageButton = Integer.parseInt(prop.getProperty("feltoltes_nyelv_button").trim());
            StartingDataUpload.ConfigCityButton  = Integer.parseInt(prop.getProperty("feltoltes_city_button").trim());
            StartingDataUpload.ConfigPersonButton = Integer.parseInt(prop.getProperty("feltoltes_person_button").trim());


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            //input.close();
        }
    }
}
