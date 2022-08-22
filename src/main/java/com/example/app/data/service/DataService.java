package com.example.app.data.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.app.data.entity.Nyelvismeret;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

//Kiolvasás .Json fileból, és beírás adatbázisba
@Service
public class DataService {

    public static <T> T getItems(Class<T> clazz, String dataFileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream stream = new ClassPathResource("data/" + dataFileName).getInputStream();
            return mapper.readValue(stream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Nyelvismeret> getNyelvismeret() {
        return Arrays.asList(getItems(Nyelvismeret[].class, "languages.json"));
        //List<Nyelvismeret> nyelvismerets = Arrays.asList(getItems(Nyelvismeret[].class, "languages.json"));
        //return nyelvismerets;
    }

    public static List<Nyelvismeret> getNyelvismeret(int count) {
        return getNyelvismeret().subList(0, count);
    }
}
