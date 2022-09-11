package com.example.app.views.list.form;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.data.entity.ProfExperience;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
        city1.setName("Budapest");
        city2 = new City();
        city2.setName("Nyíregyháza");
        cities.add(city1);
        cities.add(city2);


        languages = new HashSet<>();
        language1 = new Language();
        language1.setName("Alban");
        language2 = new Language();
        language2.setName("English");
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

    @Test
    public void formFieldsPopulated(){
        PersonForm form = new PersonForm(cities, languagesList);
        form.setPerson(person);

        Assert.assertEquals("Sanyi", form.firstName.getValue());
        Assert.assertEquals("Kiss", form.lastName.getValue());
        Assert.assertEquals("kiss@sanyi.com", form.email.getValue());
        Assert.assertEquals(city2, form.city.getValue());
        Assert.assertEquals(language1, form.language.getValue());

    }

    @Test
    public void saveEventHasCorrectValues(){
        PersonForm form = new PersonForm(cities, languagesList);
        Person person = new Person();
        form.setPerson(person);

        form.firstName.setValue("Sanyi");
        form.lastName.setValue("Kiss");
        form.email.setValue("kiss@sanyi.com");
        form.city.setValue(city2);
        form.language.setValue(languages);
        form.bornDate.setValue(LocalDate.of(2022,9,11));
        form.phone.setValue("06-30-3457-852");
        form.address.setValue("Vass utca 5");
        form.socialMedia.setValue("facebook");
        form.messageApps.setValue("messenger");
        form.webSite.setValue("www.sg.hu");
        form.otherSkill.setValue("scrum master");
        form.coverLetter.setValue("Ez a levelem");
        form.picture.setValue("jo21.png");

        AtomicReference<Person> savePerson = new AtomicReference<>(null);
        form.addListener(PersonForm.SaveEvent.class, e -> savePerson.set(e.getPerson()));

        form.save.click();

        Person saved = savePerson.get();

        Assert.assertEquals("Sanyi", saved.getfirstName());

    }
}