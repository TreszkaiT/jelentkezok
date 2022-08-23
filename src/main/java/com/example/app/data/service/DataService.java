package com.example.app.data.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.entity.Person;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
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

    public static List<Person> getPerson(){
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
            module.addDeserializer(Person.class, new CustomPersonDeserializer());
            mapper.registerModule(module);

            return mapper.readValue(stream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Person> getPersonSerializer(){
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
        public Person deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException, JacksonException  {
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
            JsonNode firstNameNode = node.get("firstName");     String firstName = firstNameNode.asText();      pers.setfirstName(firstName);
            JsonNode lastNameNode = node.get("lastName");     String lastName = lastNameNode.asText();      pers.setlastName(lastName);
            JsonNode emailNode = node.get("email");     String email = emailNode.asText();      pers.setemail(email);

            JsonNode szulDatumNode = node.get("szulDatum");     String szulDatum = szulDatumNode.asText();
            Date szulDatumDate;
            try {
                szulDatumDate = new SimpleDateFormat("yyyy-MM-dd").parse(szulDatum);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            pers.setszulDatum(szulDatumDate);

            JsonNode phoneNode = node.get("phone");     String phone = phoneNode.asText();      pers.setphone(phone);
            JsonNode addressNode = node.get("address");     String address = addressNode.asText();      pers.setaddress(address);
            //JsonNode cityNode = node.get("city");     String city = cityNode.asText();      pers.setcity(city);
            JsonNode kozMediaNode = node.get("kozMedia");     String kozMedia = kozMediaNode.asText();      pers.setkozMedia(kozMedia);
            JsonNode messageAppsNode = node.get("messageApps");     String messageApps = messageAppsNode.asText();      pers.setmessageApps(messageApps);
            JsonNode webSiteNode = node.get("webSite");     String webSite = webSiteNode.asText();      pers.setwebSite(webSite);
            JsonNode pictureNode = node.get("picture");     String picture = pictureNode.asText();      pers.setpicture(picture);
            JsonNode tanulmanyokNode = node.get("tanulmanyok");     String tanulmanyok = tanulmanyokNode.asText();      pers.settanulmanyok(tanulmanyok);
            JsonNode szakmaiTapNode = node.get("szakmaiTap");     String szakmaiTap = szakmaiTapNode.asText();      pers.setszakmaiTap(szakmaiTap);
            JsonNode egyebKeszsegekNode = node.get("egyebKeszsegek");     String egyebKeszsegek = egyebKeszsegekNode.asText();      pers.setegyebKeszsegek(egyebKeszsegek);
            JsonNode motivaciosLevelNode = node.get("motivaciosLevel");     String motivaciosLevel = motivaciosLevelNode.asText();      pers.setmotivaciosLevel(motivaciosLevel);
            //JsonNode nyelvIsmeretNode = node.get("nyelvIsmeret");     String nyelvIsmeret = nyelvIsmeretNode.asText();      pers.setnyelvIsmeret(nyelvIsmeret);


            return pers;
        }
    }
}
