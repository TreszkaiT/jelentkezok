package com.example.app.service;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.data.repository.CityRepository;
import com.example.app.data.repository.LanguageRepository;
import com.example.app.data.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ez API- adatbázishoz kapcsolódhatunk az Applikációból
 */
@Service
public class AppService {

    private final PersonRepository personRepository;
    private final CityRepository cityRepository;
    private final LanguageRepository languageRepository;

    public AppService(PersonRepository personRepository,
                      CityRepository cityRepository,
                      LanguageRepository languageRepository) {

        this.personRepository = personRepository;
        this.cityRepository = cityRepository;
        this.languageRepository = languageRepository;

        //PeldaadatokHozzaadasa();
    }



    // Person Repository-s dolgok-

    public List<Person> findAllPersons(String filterText, LocalDate dt, String why){
        if(filterText == null || filterText.isEmpty() || dt == null){
            return personRepository.findAll();
        }else if(why.equals("LANG")){
            /*List<Language> nyel = languageRepository.searchByName(filterText);
            List<Person> pers2 = new ArrayList<>();
            for(Language ny: nyel){
                List<Person> pers3 =personRepository.searchByLanguage(ny);
                for(Person p: pers3){
                    pers2.add(p);
                }
            }*/ // person -> person.getlanguage().stream().map(Language::getName
            List<Language> nyel = languageRepository.searchByName(filterText);
           /* List<Person> pers2 = new ArrayList<>();
            for(Language ny: nyel){
                List<Person> pers3 =personRepository.searchByLanguage(ny);
                for(Person p: pers3){
                    pers2.add(p);
                }
            }*/
            List<Person> pers2 = new ArrayList<>();
            /*for(Language ny: nyel){
                List<Person> pers3 =personRepository.searchByLanguage(ny);
                for(Person p: pers3){
                    pers2.add(p);
                }
            }*/
            //pers2 = language -> language.getPerson().steam().map(nyel);
            //System.out.println(nyel.size());
            return personRepository.findAll();
            //return pers2;
        }else if(why.equals("DATE")){
            return personRepository.searchByDate(dt);
        }else {
            return personRepository.searchByName(filterText);
        }
    }

    public Optional<City> findByCity(String id) {
        return cityRepository.findById(UUID.fromString(id));
    }

    public City findCityByName(String name){
        List<City> cityFindAll = findAllCities();
        for(City city: cityFindAll){
            if(city.getName().equals(name)) return city;
        }
        return null;
    }

    public Language findLanguageByName(String name){
        List<Language> nyelvFindAll = findAllLanguage();
        for(Language nyelv: nyelvFindAll){
            if(nyelv.getName().equals(name)) return nyelv;
        }
        return null;
    }

    public long countPersons(){
        return personRepository.count();
    }

    public long countCities(){
        return cityRepository.count();
    }

    public long countLanguage(){
        return languageRepository.count();
    }

    public void deletePerson(Person person){
        personRepository.delete(person);
    }

    public void savePerson(Person person){
        if(person == null){
            System.out.println("Nincs Személy");
        }

        personRepository.save(person);
    }

    // City Repository-s dolgok-
    public List<City> findAllCities(){
        return cityRepository.findAll();
    }

    /*
    public static List<Country> getCountries() {
        return Arrays.asList(getItems(Country[].class, "countries.json"));
    }*/

    // Language Repository-s dolgok-
    public List<Language> findAllLanguage(){
        return languageRepository.findAll();
    }

    public List<Person> findAllPersons(){return personRepository.findAll();};

    public  void saveCities(List<City> cities){
        if(cities == null){
            System.out.println("Nincsenek városok!");
        }

        for(City cit: cities){
            cityRepository.save(cit);
        }
    }

    public void saveLanguage(List<Language> languages){
        if(languages == null){
            System.out.println("Nincsenek nyelvek");
        }

        //languages.stream()
        for (Language ny: languages) {
            languageRepository.save(ny);
            //System.out.println("1");
        }
    }

    public void savePerson(List<Person> persons){
        if(persons == null) System.out.println("Nincsennek személyek");

        for(Person per: persons) personRepository.save(per);
    }

   /* private void PeldaadatokHozzaadasa() {

        City city1 = new City();
        city1.setName("Nyíregyháza");
        city1.setZip("4400");
        City city2 = new City();
        city2.setName("Budapest");
        city2.setZip("1000");

        cityRepository.save(city1);
        cityRepository.save(city2);

        Language nyelv1 = new Language();
        nyelv1.setName("Angol");
        Language nyelv2 = new Language();
        nyelv2.setName("Német");
        Language nyelv3 = new Language();
        nyelv3.setName("Francia");

        languageRepository.save(nyelv1);
        languageRepository.save(nyelv2);
        languageRepository.save(nyelv3);

        Person person1 = new Person();
        person1.setfirstName("Kiss");
        person1.setlastName("Géza");
        person1.setemail("valami@valami.hu");

        String sDate1="31/12/1998";
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1=formatter1.parse(sDate1);
            person1.setszulDatum(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        person1.setphone("0630-3564-874");
        person1.setaddress("Vasgyár utca 7/a");
        person1.setcity(city1);
        person1.setkozMedia("facebook");
        person1.setmessageApps("messenger");
        person1.setwebSite("google.com");
        person1.setpicture("1.jpg");
        person1.settanulmanyok("magyar, matek");
        person1.setszakmaiTap("programozó, semmi");
        person1.setegyebKeszsegek("vezetés, olvasás");
        person1.setmotivaciosLevel("Egyszer volt hol nem volt");
        person1.setlanguage(nyelv1);

        Person person2 = new Person();
        person2.setfirstName("Nagy");
        person2.setlastName("István");
        person2.setemail("nagyi@valami.hu");

        String sDate2="31/12/1998";
        SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date2=formatter2.parse(sDate2);
            person2.setszulDatum(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        person2.setphone("0630-3564-874");
        person2.setaddress("Vasgyár utca 7/a");
        person2.setcity(city2);
        person2.setkozMedia("facebook");
        person2.setmessageApps("messenger");
        person2.setwebSite("google.com");
        person2.setpicture("2.jpg");
        person2.settanulmanyok("magyar, matek");
        person2.setszakmaiTap("programozó, semmi");
        person2.setegyebKeszsegek("vezetés, olvasás");
        person2.setmotivaciosLevel("Egyszer volt hol nem volt");
        person2.setlanguage(nyelv2);

        personRepository.save(person1);
        personRepository.save(person2);
    }*/
}
