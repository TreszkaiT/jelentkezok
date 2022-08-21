package com.example.app.data.service;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.entity.Person;
import com.example.app.data.repository.CityRepository;
import com.example.app.data.repository.NyelvismeretRepository;
import com.example.app.data.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ez API- adatbázishoz kapcsolódhatunk az Applikációból
 */
@Service
public class AppService {

    private final PersonRepository personRepository;
    private final CityRepository cityRepository;
    private final NyelvismeretRepository nyelvismeretRepository;

    public AppService(PersonRepository personRepository,
                      CityRepository cityRepository,
                      NyelvismeretRepository nyelvismeretRepository) {

        this.personRepository = personRepository;
        this.cityRepository = cityRepository;
        this.nyelvismeretRepository = nyelvismeretRepository;

        PeldaadatokHozzaadasa();
    }



    // Person Repository-s dolgok-

    public List<Person> findAllPersons(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return personRepository.findAll();
        }else{
            return personRepository.search(filterText);
        }
    }

    public long countPersons(){
        return personRepository.count();
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

    // Nyelvismeret Repository-s dolgok-
    public List<Nyelvismeret> findAllNyelvismeret(){
        return nyelvismeretRepository.findAll();
    }

    private void PeldaadatokHozzaadasa() {

        City city1 = new City();
        city1.setName("Nyíregyháza");
        city1.setZip("4400");
        City city2 = new City();
        city2.setName("Budapest");
        city2.setZip("1000");

        cityRepository.save(city1);
        cityRepository.save(city2);

        Nyelvismeret nyelv1 = new Nyelvismeret();
        nyelv1.setName("Angol");
        Nyelvismeret nyelv2 = new Nyelvismeret();
        nyelv2.setName("Német");
        Nyelvismeret nyelv3 = new Nyelvismeret();
        nyelv3.setName("Francia");

        nyelvismeretRepository.save(nyelv1);
        nyelvismeretRepository.save(nyelv2);
        nyelvismeretRepository.save(nyelv3);

        Person person1 = new Person();
        person1.setfirstName("Kiss");
        person1.setlastName("Géza");
        person1.setemail("valami@valami.hu");
        person1.setszulDatum("2003.08.05");
        person1.setphone("0630-3564-874");
        person1.setaddress("Vasgyár utca 7/a");
        person1.setcity(city1);
        person1.setkozMedia("facebook");
        person1.setmessageApps("messenger");
        person1.setwebSite("google.com");
        person1.setpicture("1.jpg");
        person1.setTanulmanyok("magyar, matek");
        person1.setszakmaiTap("programozó, semmi");
        person1.setegyebKeszsegek("vezetés, olvasás");
        person1.setmotivaciosLevel("Egyszer volt hol nem volt");
        person1.setnyelvIsmeret(nyelv1);

        Person person2 = new Person();
        person2.setfirstName("Nagy");
        person2.setlastName("István");
        person2.setemail("nagyi@valami.hu");
        person2.setszulDatum("2000.08.05");
        person2.setphone("0630-3564-874");
        person2.setaddress("Vasgyár utca 7/a");
        person2.setcity(city2);
        person2.setkozMedia("facebook");
        person2.setmessageApps("messenger");
        person2.setwebSite("google.com");
        person2.setpicture("2.jpg");
        person2.setTanulmanyok("magyar, matek");
        person2.setszakmaiTap("programozó, semmi");
        person2.setegyebKeszsegek("vezetés, olvasás");
        person2.setmotivaciosLevel("Egyszer volt hol nem volt");
        person2.setnyelvIsmeret(nyelv2);

        personRepository.save(person1);
        personRepository.save(person2);
    }
}
