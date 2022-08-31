package com.example.app.service;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.data.entity.Study;
import com.example.app.data.repository.CityRepository;
import com.example.app.data.repository.LanguageRepository;
import com.example.app.data.repository.PersonRepository;
import com.example.app.data.repository.StudyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * ez API- adatbázishoz kapcsolódhatunk az Applikációból
 */
@Service
public class AppService {

    private final PersonRepository personRepository;
    private final CityRepository cityRepository;
    private final LanguageRepository languageRepository;
    private final StudyRepository studyRepository;

    public AppService(PersonRepository personRepository,
                      CityRepository cityRepository,
                      LanguageRepository languageRepository,
                      StudyRepository studyRepository) {

        this.personRepository = personRepository;
        this.cityRepository = cityRepository;
        this.languageRepository = languageRepository;
        this.studyRepository = studyRepository;

        //PeldaadatokHozzaadasa();
        Study study = new Study();
        study.setNameSchool("fdfd");
    }



    // Person Repository-s dolgok-

    public List<Person> findAllPersons(String filterName, LocalDate dt, String filterLang) {
        /*if (filterName == null) System.out.println("filtername null");
        if (filterName.isEmpty()) System.out.println("filtename empty");
        if (filterLang == null) System.out.println("filterLang null");
        if (filterLang.isEmpty()) System.out.println("filterLang empty");
        if (dt == null) System.out.println("dt null");*/

        if ((filterName == null || filterName.isEmpty()) && dt == null && (filterLang == null || filterLang.isEmpty())) {
            return personRepository.findAll();
        } else {

            List<Person> containerName = new ArrayList<>();
            if((filterName != null && !filterName.isEmpty())){
                containerName.addAll(personRepository.searchByFirstNameLikeOrLastNameLike("%" + filterName + "%", "%" + filterName + "%"));
            }

            List<Person> containerPerson = new ArrayList<>();
            if(dt != null){
                containerPerson.addAll(personRepository.searchByDate(dt));
            }

            List<Person> containerLanguage = new ArrayList<>();
            if((filterLang != null && !filterLang.isEmpty())){

                List<Language> nyel = languageRepository.searchByName(filterLang);
                Set<Language> set1 = new HashSet<>();
                for (Language t : nyel)
                    set1.add(t);

                containerLanguage.addAll(personRepository.findAllByLanguageIn(set1));
            }

            if((containerName.size()>0) && (containerPerson.size()>0) && (containerLanguage.size()>0)){
                containerName.retainAll(containerPerson);
                containerName.retainAll(containerLanguage);
                return containerName;
            } else if((containerName.size()>0) && (containerPerson.size()>0)){
                containerName.retainAll(containerPerson);
                return containerName;
            } else if ((containerPerson.size()>0) && (containerLanguage.size()>0)) {
                containerPerson.retainAll(containerLanguage);
                return containerPerson;
            } else if ((containerName.size()>0) && (containerLanguage.size()>0)) {
                containerName.retainAll(containerLanguage);
                return containerName;
            } else{
                if(containerName.size()>0) return containerName;
                else if (containerPerson.size()>0) return containerPerson;
                else return containerLanguage;
            }



           /* List<Language> nyel = languageRepository.searchByName(filterLang);
            Set<Language> set1 = new HashSet<>();
            for (Language t : nyel)
                set1.add(t);

            if ((filterName != null && !filterName.isEmpty()) && dt != null && (filterLang != null && !filterLang.isEmpty()))
                return personRepository.searchByFirstNameLikeOrLastNameLikeOrBornDateLike("%" + filterName + "%", "%" + filterName + "%", dt);
            else if ((filterName != null && !filterName.isEmpty()) && (filterLang != null && !filterLang.isEmpty()))
                //return personRepository.searchByFirstNameLikeOrLastNameLikeOrBornDateLike("%" + filterName + "%", "%" + filterName + "%", dt);
                return personRepository.findAllByLanguageInOrFirstNameLikeOrLastNameLike(set1,"%" + filterName + "%", "%" + filterName + "%");
            else if ((filterName != null && !filterName.isEmpty()) && dt != null) //ok
                return personRepository.searchByFirstNameLikeOrLastNameLikeAndBornDateEquals("%" + filterName + "%", "%" + filterName + "%", dt);
            else if (dt != null && (filterLang != null && !filterLang.isEmpty()))
                return personRepository.searchByFirstNameLikeOrLastNameLikeOrBornDateLike("%" + filterName + "%", "%" + filterName + "%", dt);
            else if (!filterName.isEmpty()) // ok
                return personRepository.searchByFirstNameLikeOrLastNameLike("%" + filterName + "%", "%" + filterName + "%");
            else if (dt != null)
                return personRepository.searchByDate(dt);   //ok
            else if (!filterLang.isEmpty()) {

                return personRepository.findAllByLanguageIn(set1);
            }
            //return  personRepository.searchByFirstNameLikeOrLastNameLikeOrBornDateLike("%"+filterName+"%", "%"+filterName+"%", dt);

            else return personRepository.findAll();*/
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
        List<Language> langFindAll = findAllLanguage();
        for(Language lang: langFindAll){
            if(lang.getName().equals(name)) return lang;
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

        Language lang1 = new Language();
        lang1.setName("Angol");
        Language lang2 = new Language();
        lang2.setName("Német");
        Language lang3 = new Language();
        lang3.setName("Francia");

        languageRepository.save(lang1);
        languageRepository.save(lang2);
        languageRepository.save(lang3);

        Person person1 = new Person();
        person1.setfirstName("Kiss");
        person1.setlastName("Géza");
        person1.setemail("valami@valami.hu");

        String sDate1="31/12/1998";
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1=formatter1.parse(sDate1);
            person1.setbornDate(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        person1.setphone("0630-3564-874");
        person1.setaddress("Vasgyár utca 7/a");
        person1.setcity(city1);
        person1.setsocialMedia("facebook");
        person1.setmessageApps("messenger");
        person1.setwebSite("google.com");
        person1.setpicture("1.jpg");
        person1.setstudies("magyar, matek");
        person1.setprofExperience("programozó, semmi");
        person1.setotherSkill("vezetés, olvasás");
        person1.setcoverLetter("Egyszer volt hol nem volt");
        person1.setlanguage(lang1);

        Person person2 = new Person();
        person2.setfirstName("Nagy");
        person2.setlastName("István");
        person2.setemail("nagyi@valami.hu");

        String sDate2="31/12/1998";
        SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date2=formatter2.parse(sDate2);
            person2.setbornDate(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        person2.setphone("0630-3564-874");
        person2.setaddress("Vasgyár utca 7/a");
        person2.setcity(city2);
        person2.setsocialMedia("facebook");
        person2.setmessageApps("messenger");
        person2.setwebSite("google.com");
        person2.setpicture("2.jpg");
        person2.setstudies("magyar, matek");
        person2.setprofExperience("programozó, semmi");
        person2.setotherSkill("vezetés, olvasás");
        person2.setcoverLetter("Egyszer volt hol nem volt");
        person2.setlanguage(lang2);

        personRepository.save(person1);
        personRepository.save(person2);
    }*/
}
