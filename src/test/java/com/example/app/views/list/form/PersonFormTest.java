package com.example.app.views.list.form;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.data.entity.ProfExperience;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonFormTest {
    private List<City> cities;
    private Set<Language> languages;
    private List<Language> languagesList;
    private List<ProfExperience> profExperiences;
    private Person person;
    private City city1;
    private City city2;
    private Language language1;
    private Language language2;

    private ProfExperience profExperience1;

    @Before
    public void setupData() {
        cities = new ArrayList<>();
        city1 = new City();
        city1.setName("Vaadin Ltd");
        city2 = new City();
        city2.setName("IT Mill");
        cities.add(city1);
        cities.add(city2);


        languages = new HashSet<>();
        language1 = new Language();
        language1.setName("Status 1");
        language2 = new Language();
        language2.setName("Status 2");
        languages.add(language1);
        languages.add(language2);

        languagesList = new ArrayList<>();
        languagesList.addAll(languages);

        profExperiences = new ArrayList<>();
        profExperience1 = new ProfExperience();
        profExperience1.setNameWork("Programozó");
        profExperiences.add(profExperience1);

        person = new Person();
        person.setfirstName("Sanyi");
        person.setlastName("Kiss");
        person.setemail("kiss@sanyi.com");
        person.setlanguage(languages);
        person.setcity(city2);
        person.setProfExperiences(profExperiences);
    }

    public void formFieldsPopulated(){
        PersonForm form = new PersonForm(cities, languagesList);
        form.setPerson(person);

        Assert.assertEquals("Sanyi", form.firstName.getValue());
        Assert.assertEquals("Kiss", form.lastName.getValue());
        Assert.assertEquals("kiss@sanyi.com", form.email.getValue());

    }
}