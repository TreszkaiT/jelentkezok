package com.example.app.service;

import com.example.app.data.entity.*;
import com.example.app.data.excel.ExcelXlsAndXlsxRead;
import com.example.app.data.repository.CityRepository;
import com.example.app.data.repository.LanguageRepository;
import com.example.app.data.repository.PersonRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class StartingService {

    LanguageRepository languageRepository;
    CityRepository cityRepository;
    PersonRepository personRepository;

    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }
    @Autowired
    public void setCityRepository(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    @Autowired
    public void setPersonRepository(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    @PostConstruct                                                                          // automatikusan meghívódik, ha ez a Service is létrejön. És csakis egyszer futás elején
    public void init(){

        LangFill();                 // Nyelv adatok beírása az adatbázisba

        CityFill();                 // City adatok beírása az adatbázisba

        PersonFill();               // Person adatok beírása az adatbázisba

    }

    private void LangFill() {
        List<Language> languages = getLanguage();

        //languages.stream()
        for (Language ny: languages) {
            languageRepository.save(ny);
            //System.out.println("1");
        }
    }

    private void CityFill() {
        String[][] dataTable = ExcelXlsAndXlsxRead.getEXlsXlsx("telepulesek.xlsx", 0);        // read from cells -- .xls
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < dataTable.length; i++) {
            if (dataTable[i][1] == null || dataTable[i][1].isEmpty() || dataTable[i][1].equals("")) {
            } else {
                cities.add(new City(dataTable[i][1], dataTable[i][0]));
            }
        }

        //if(cities==null) System.out.println("null"); else service.saveCities(cities);
        if (cities != null) {

            for(City cit: cities){
                cityRepository.save(cit);
            }
        }

    }

    private void PersonFill() {

        List<Person> persons = getPersonSerializer();

        List<Person> personsok = new ArrayList<>();

            for (Person pers : persons) {
                long rndCity = new RandomDataGenerator().nextLong(0, cityRepository.count());
                long rndlang = new RandomDataGenerator().nextLong(0, languageRepository.count());
                long rndlang2 = new RandomDataGenerator().nextLong(0, languageRepository.count());
                //if(pers.getcity()==null) pers.System.out.println("null city");
                //if(pers.getlanguage()==null) System.out.println("null lang");
                pers.setcity(cityRepository.findAll().get((int) rndCity));//findByCity("1"));
                pers.getlanguage().add(languageRepository.findAll().get((int) rndlang));
                pers.getlanguage().add(languageRepository.findAll().get((int) rndlang2));

                {
                    Study study = new Study();
                    study.setNameSchool("Test1 Iskola");
                    study.setFormDate(LocalDate.of(1977, 11, 12));
                    study.setToDate(LocalDate.of(1978, 11, 12));
                    pers.getstudies().add(study);
                    study.setPerson(pers);
                }
                {
                    Study study = new Study();
                    study.setNameSchool("Maosidik Iskola");
                    study.setFormDate(LocalDate.of(1979, 9, 12));
                    study.setToDate(LocalDate.of(1981, 11, 12));
                    study.setComment("ez egy megjegyzes");
                    pers.getstudies().add(study);
                    study.setPerson(pers);
                }

                {
                    ProfExperience proof = new ProfExperience();
                    proof.setNameWork("Első");
                    proof.setFromDate(LocalDate.of(1977,11,12));
                    proof.setToDate(LocalDate.of(1978,11,12));
                    proof.setComment("Valami");
                    pers.getProfExperiences().add(proof);
                    proof.setPerson(pers);
                }
                {
                    ProfExperience proof = new ProfExperience();
                    proof.setNameWork("Második");
                    proof.setFromDate(LocalDate.of(1979,9,12));
                    proof.setToDate(LocalDate.of(1981,11,12));
                    proof.setComment("Valami2");
                    pers.getProfExperiences().add(proof);
                    proof.setPerson(pers);
                }
                //pers.setcity(service.findCityByName("Nyíregyháza"));
                //pers.setlanguage(service.findLanguageByName("English"));
                personsok.add(pers);
            }

            // save Persons
            if(personsok == null) System.out.println("Nincsennek személyek");
            else for(Person per: personsok) personRepository.save(per);

    }



    // Load data from files
    // GET datas
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
    public static List<Language> getLanguage() {
        return Arrays.asList(getItems(Language[].class, "languages.json"));
    }
    public static List<Language> getLanguage(int count) {
        return getLanguage().subList(0, count);
    }
    public static List<Person> getPerson() {
        return Arrays.asList(getItems(Person[].class, "person.json"));
    }


    /**
     * Mivel nem minden tartalom létezik a JSON fileba, mint a Person object-be, így Serializálni, sorosítani kell
     */
    public static <T> T getItemsSerializer(Class<T> clazz, String dataFileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream stream = new ClassPathResource("data/" + dataFileName).getInputStream();

            SimpleModule module =
                    new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Person.class, new StartingDataService.CustomPersonDeserializer());
            mapper.registerModule(module);

            return mapper.readValue(stream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Person> getPersonSerializer() {
        return Arrays.asList(getItemsSerializer(Person[].class, "person.json"));
    }

    public static class CustomPersonDeserializer extends StdDeserializer<Person> {

        public CustomPersonDeserializer() {
            this(null);
        }

        public CustomPersonDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Person deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException, JacksonException {
            Person pers = new Person();
            ObjectCodec codec = parser.getCodec();
            JsonNode node = null;
            try {
                node = codec.readTree(parser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //objectMapper.setDateFormat(df);
            // try catch block
            JsonNode firstNameNode = node.get("firstName");
            String firstName = firstNameNode.asText();
            pers.setfirstName(firstName);
            JsonNode lastNameNode = node.get("lastName");
            String lastName = lastNameNode.asText();
            pers.setlastName(lastName);
            JsonNode emailNode = node.get("email");
            String email = emailNode.asText();
            pers.setemail(email);

            JsonNode bornDateNode = node.get("bornDate");
            String bornDate = bornDateNode.asText();
            Date bornDateDate;
            try {
                bornDateDate = new SimpleDateFormat("yyyy-MM-dd").parse(bornDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            LocalDate bornDateDateUj = LocalDate.ofInstant(bornDateDate.toInstant(), ZoneId.systemDefault()); // import java.util.Date to java.time.LocalDate
            pers.setbornDate(bornDateDateUj);

            JsonNode phoneNode = node.get("phone");
            String phone = phoneNode.asText();
            pers.setphone(phone);
            JsonNode addressNode = node.get("address");
            String address = addressNode.asText();
            pers.setaddress(address);
            //JsonNode cityNode = node.get("city");     String city = cityNode.asText();      pers.setcity(city);
            JsonNode socialMediaNode = node.get("socialMedia");
            String socialMedia = socialMediaNode.asText();
            pers.setsocialMedia(socialMedia);
            JsonNode messageAppsNode = node.get("messageApps");
            String messageApps = messageAppsNode.asText();
            pers.setmessageApps(messageApps);
            JsonNode webSiteNode = node.get("webSite");
            String webSite = webSiteNode.asText();
            pers.setwebSite(webSite);
            JsonNode pictureNode = node.get("picture");
            String picture = pictureNode.asText();
            pers.setpicture(picture);
            JsonNode studiesNode = node.get("studies");     /*String studies = studiesNode.asText();      pers.setstudies(studies);*/
            JsonNode profExperienceNode = node.get("profExperience");     /*String profExperience = profExperienceNode.asText();      pers.setprofExperience(profExperience);*/
            JsonNode otherSkillNode = node.get("otherSkill");
            String otherSkill = otherSkillNode.asText();
            pers.setotherSkill(otherSkill);
            JsonNode coverLetterNode = node.get("coverLetter");
            String coverLetter = coverLetterNode.asText();
            pers.setcoverLetter(coverLetter);
            //JsonNode languageNode = node.get("language");     String language = languageNode.asText();      pers.setlanguage(language);


            return pers;
        }
    }
}
