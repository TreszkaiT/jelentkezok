package com.example.app.views.list.form;

import com.example.app.data.entity.*;
import com.example.app.exception.InvalidBeanWriteException;
import com.example.app.views.list.upload.UploadPictureI18N;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PersonForm extends FormLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonForm.class);

    Binder<Person> binder = new BeanValidationBinder<>(Person.class);             // összekapcsol egy modul objektumor egy Components-el    BeanValidationBinder:a Person osztály validation Annotációit használja a formelemkre is, így már nekünk ezt nem kell megtenni a nézetnél külön

    private Person person;

    // Components
    TextField firstName = new TextField("Vezetéknév");              // automatikus Bind-elés miatt a nevük itt egyezzen meg a Person osztályban lévő nevekkel!!!
    TextField lastName = new TextField("Keresztnév");
    EmailField email = new EmailField("Email");
    DatePicker bornDate = new DatePicker("Születési dátum");
    TextField phone = new TextField("Telefonszám");
    TextField address = new TextField("Lakcím");
    TextField socialMedia = new TextField("Közösségi média");
    TextField messageApps = new TextField("Üzenetküldő appok");
    TextField webSite = new TextField("Website");


    Button divstudiesButton = new Button("Hozzáad");
    H5 h5studies = new H5("Tanulmányok");
    Div divstudies = new Div();


    TextField profExperience = new TextField("Szakmai Tapasztalat");
    Button divProfExperienceButton = new Button("Hozzáad");
    H5 h5ProfExperience = new H5("Szakmai Tapasztalat");
    Div divProfExperience = new Div();


    TextField otherSkill = new TextField("Egyéb készségek");


    Button divCoverLetter = new Button("Szerkesztés");
    H5 h5coverLetter = new H5("Motivációs levél");
    RichTextEditor coverLetter = new RichTextEditor();


    MemoryBuffer buffer = new MemoryBuffer();
    Upload upload = new Upload(buffer);
    Scroller scroller = new Scroller();
    TextField picture = new TextField("Fénykép");


    MultiselectComboBox<Language> language = new MultiselectComboBox<>("Nyelvismeret");
    ComboBox<City> city = new ComboBox<>("Város");

    Button save = new Button("Mentés");
    Button delete = new Button("Törlés");
    Button cancel = new Button("Mégsem");


    public PersonForm(List<City> cities, List<Language> languages) {//}, AppService services) {
        addClassName("contact-form");

        binder.bindInstanceFields(this);        // a binder meghívása itt van. És this elég !!! azért, mert az itt lévő fenti nevek megyegyeznek a Person osztályban lévő nevekkel!!!

        city.setItems(cities);
        city.setItemLabelGenerator(City::getName);                      // mit jelenítsünk meg a ComboBoxban

        language.setItems(languages);
        language.setItemLabelGenerator(Language::getName);

        h5coverLetter.setClassName("h5-style");
        h5ProfExperience.setClassName("h5-style");

        binder.forField(firstName)
                .withValidator(firstName -> firstName.length() >=3, "Túl kevés karaktert írtál ide!")
                .asRequired("Nem lehet üres")
                .bind(Person::getfirstName, Person::setfirstName);
        binder.forField(lastName)
                .withValidator(lastName -> lastName.length() >=3, "Túl kevés karaktert írtál ide!")
                .bind(Person::getlastName, Person::setlastName);
        binder.forField(phone)
                .withValidator(phone -> phone.length() >=8, "Túl kevés karaktert írtál ide!")
                .bind(Person::getphone, Person::setphone);
        binder.forField(address)
                .withValidator(address -> address.length() >=5, "Túl kevés karaktert írtál ide!")
                .bind(Person::getaddress, Person::setaddress);
        binder.forField(picture)
                .withValidator(picture -> picture.length() >=2, "Túl kevés karaktert írtál ide!")
                .bind(Person::getpicture, Person::setpicture);

        firstName.setPlaceholder("Kiss");
        lastName.setPlaceholder("János");
        email.setPlaceholder("kissjanos@gmail.com");
        phone.setPlaceholder("06-30-3487-952");
        bornDate.setPlaceholder("2022. 09. 12.");
        address.setPlaceholder("Bessenyei tér 4/A");
        socialMedia.setPlaceholder("facebook, twitter, iwiw");
        messageApps.setPlaceholder("messenger, skype");
        webSite.setPlaceholder("www.sajatoldalam.hu");
        otherSkill.setPlaceholder("jó szociális készség, agilitás");
        picture.setPlaceholder("igazolványkép.jpg");

        firstName.focus();
        //firstName.setAutofocus(true);


        add(
                firstName,
                lastName,
                email,
                bornDate,
                phone,
                address,
                socialMedia,
                messageApps,
                webSite,

                h5studies, divstudiesButton, divstudies,

                h5ProfExperience, divProfExperienceButton, divProfExperience,
                otherSkill,
                h5coverLetter, divCoverLetter,

                picture,
                upload,
                scroller,
                city,
                language,
                createButtonLayout()
        );

        divProfExperience.setWidthFull();

        UploadBeallitasa();
        UploadUtanKepBetoltese();

        DialgProfExperience();
        DialgStudies();
        showStudiesList();
        showProfExperienceList();

        DialgCoverLetter(coverLetter);
    }


    /**
     * Image gomb figyelése, hibakezelés
     */
    private void UploadBeallitasa() {
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        int maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB
        upload.setMaxFileSize(maxFileSizeInBytes);
        upload.setMaxFiles(1);
        UploadPictureI18N i18n = new UploadPictureI18N();
        i18n.getError().setTooManyFiles("Egyszerre legfeljebb egy fájlt tölthetsz fel.");
        upload.setI18n(i18n);

        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(
                    errorMessage,
                    5000,
                    Notification.Position.MIDDLE
            );
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
    }

    private void UploadUtanKepBetoltese() {
        final String[] pictName = {""};
        final String[] s = {""};
        upload.addSucceededListener(event -> {
            // Get information about the uploaded file
            InputStream fileData = buffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            picture.setValue(fileName);

            pictName[0] = fileName;
            // Do something with the file data
            Component component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());

            scroller.setContent(null);
            showOutput(event.getFileName(), component, scroller);

            String resourcesPath = "src/main/resources/images/";
            File targetFile = new File(resourcesPath + fileName);

            Path currentRelativePath = Paths.get("");
            s[0] = (currentRelativePath.toAbsolutePath().toString() + resourcesPath);

            // fájl feltöltése
            try {
                FileUtils.copyInputStreamToFile(buffer.getInputStream(), targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //  File uploadnál a clear gombra kattintva X gombra
            if (component instanceof Image) {
                upload.getElement().addEventListener("file-remove", event1 -> {
                    //System.out.println("File remove");
                    scroller.setContent(null);
                    targetFile.delete();
                });
            }


        });
    }

    /**
     * Image feltöltése, és eseménykezelése
     *
     * @param mimeType
     * @param fileName
     * @param stream
     * @return
     */
    private Component createComponent(String mimeType, String fileName,
                                      InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        } else if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {

                byte[] bytes = IOUtils.toByteArray(stream);
                image.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }

    private Component createTextComponent(InputStream stream) {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            text = "exception reading stream";
        }
        return new Text(text);
    }

    private void showOutput(String text, Component content,
                            Scroller scroller) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        scroller.setWidthFull();//("200px");
        scroller.setHeight("200px");

        scroller.setContent(content);
    }

    /**
     * Buttons Layer összeállítása
     *
     * @return
     */
    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());                                              // Mentes 0.: amikor a Person felmegy perzisztálásra, azaz megnyomjuk a nagy Mentés gombot a Form leglalján, meghívódik a validatAndSave()
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, person)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(person);                                                               // Mentes 1.: a Binder visszaírja a Person-ba a Form értékeit, csak itt nincs hozzáadva a Student, mert azt mi megcsináltuk korábban. Így az összes többi elemet visszaírja a Personba,és
            //appController.savePerson(person);
            fireEvent(new SaveEvent(this, person));                                          // és egy SaveEvent-et bocsájtunk ki. Ami a SaveEvent-be belerakja a person értékét, és ...>> ListView.java configFormn()
        } catch (ValidationException e) {
            //throw new RuntimeException(e);
            throw new InvalidBeanWriteException("A személy mentése közben hiba történt!");
        }
    }

    /**
     * Person adatok beállítása és mentése
     *
     * @param person
     */


    /**
     * Eventek Class elkészítése
     */
    public static abstract class PersonFormEvent extends ComponentEvent<PersonForm> {
        private Person person;

        protected PersonFormEvent(PersonForm source, Person person) {
            super(source, false);
            this.person = person;
        }

        public Person getPerson() {
            return person;
        }
    }

    public static class SaveEvent extends PersonFormEvent {
        SaveEvent(PersonForm source, Person person) { super(source, person);  }
    }
    public static class DeleteEvent extends PersonFormEvent {
        DeleteEvent(PersonForm source, Person person) {
            super(source, person);
        }

    }
    public static class CloseEvent extends PersonFormEvent {
        CloseEvent(PersonForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


    /**
     * Mindegyik gombhoz alul kell, a mentéshez és a remove-hoz
     * @param person
     */

    public void setPerson(Person person) {
        this.person = person;
        binder.readBean(person);        // Binder beolvassa ezt a persont, és ezek a fentebbi mezők az add( firstName, ...  ez alapján töltődnek fel
        showStudiesList();
        showProfExperienceList();
    }

    /**
     * SZAKMAI TAPASZTALAT
     * Hozzáad gombra kattintva
     * Create Dialog window Szakmai Tapasztalat
     */
    public List<String> profExpNameClass = new ArrayList<>();
    private void DialgProfExperience() {
        divProfExperienceButton.addClickListener(event -> {
            ProfExperience profExperience = new ProfExperience();
            createProfExperianceFormNew(profExperience, createDialogLayoutProfExperience(profExperience)).open();
        });
    }
    private Dialog createProfExperianceFormNew(ProfExperience profExperience, ProfExperienceForm profExperienceForm){
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");
        dialog.getHeader().add(createHeaderLayoutProfExperience());

        dialog.add(profExperienceForm);
        dialog.setModal(false);
        dialog.setDraggable(true);

        Button cancelButton = new Button("Mégsem", e -> dialog.close());
        Button saveButton = new Button("Mentés", e -> {
            profExperienceForm.save();
            PersonForm.this.person.getProfExperiences().add(profExperience);    // itt beállítjuk mindkettőt az elmentéshez, és a nagy mentés gombbra kattintva menti csak majd el
            profExperience.setPerson(PersonForm.this.person);
            dialog.close();
            profExpNameClass.add(profExperience.getNameWork());
            showProfExperienceList();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        return dialog;
    }

    /**
     * Kitölt gombra kattintva a listában
     * Create Dialog window Szakmai Tapasztalat
     */
    private void removeExperienceOne(ProfExperience profExperience) {
        Person person =  profExperience.getPerson();
        person.getProfExperiences().remove(profExperience);
        profExperience.setPerson(null);
    }
    private void showProfExperienceList() {
        divProfExperience.removeAll();
        if (null == person){
            return;
        }
        for (ProfExperience profExperience : person.getProfExperiences()) {
            ProfExperienceForm profExperienceForm = createDialogLayoutProfExperience(profExperience);
            Dialog dialog = createProfExperienceDialog(profExperienceForm);
            H5 h5 = new H5(profExperience.getNameWork());
            if(profExpNameClass.size()>0){
                for(String str: profExpNameClass){
                    if(profExperience.getNameWork().equals(str)) h5.addClassName("prof-experience-change"); else h5.addClassName("prof-experience");
                }
            }
            Button buttonShow = new Button("Kitölt", e -> dialog.open());
            Button buttonClose = new Button("Töröl", e -> removeExperienceOne(profExperience));
            add(dialog);

            Scroller sc1 = new Scroller();
            VerticalLayout vl = new VerticalLayout();
            HorizontalLayout hl2 = new HorizontalLayout();
            hl2.add(buttonShow, buttonClose);
            vl.add(h5,hl2);

            sc1.setContent(vl);

            divProfExperience.add(sc1);//buttonShow, buttonClose, h5);

            buttonClose.addClickListener(e -> sc1.setContent(null));

            createFooterProfExperience(dialog, profExperienceForm, profExperience);
        }
    }
    private static ProfExperienceForm createDialogLayoutProfExperience(ProfExperience profExperience) {
        ProfExperienceForm profExperienceLayout= new ProfExperienceForm(profExperience);

        profExperienceLayout.getStyle().set("width", "400px").set("max-width", "100%");

        return profExperienceLayout;
    }
    private Dialog createProfExperienceDialog(ProfExperienceForm profExperienceForm){
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");

        dialog.getHeader().add(createHeaderLayoutProfExperience());

        dialog.add(profExperienceForm);
        dialog.setModal(false);
        dialog.setDraggable(true);
        return dialog;
    }
    private static H2 createHeaderLayoutProfExperience() {
        H2 headline = new H2("Szakmai Tapasztalat");
        headline.addClassName("draggable");
        headline.getStyle().set("margin", "0").set("font-size", "1.5em")
                .set("font-weight", "bold")
                .set("cursor", "move")
                .set("padding", "var(--lumo-space-m) 0")
                .set("flex", "1");

        return headline;
    }
    private void createFooterProfExperience(Dialog dialog, ProfExperienceForm profExperienceForm, ProfExperience profExperience) {
        Button cancelButton = new Button("Mégsem", e -> dialog.close());
        Button saveButton = new Button("Mentés", e -> {
            profExperienceForm.save();
            dialog.close();
            profExpNameClass.add(profExperience.getNameWork());
            showProfExperienceList();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
    }


    /**
     * TANULMÁNYOK
     * Hozzáad gombra kattintva
     * Create Dialog window Tanulmányok
     */
    public List<String> studyNameClass = new ArrayList<>();
    private void DialgStudies() {
        divstudiesButton.addClickListener(event -> {
            Study study = new Study();
            Dialog dialog = createStudyDialogForNew(study, createDialogLayoutStudies(study));
            dialog.open();
        });
    }
    private Dialog createStudyDialogForNew(Study study, StudyForm studyForm) {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");
        dialog.getHeader().add(createHeaderLayoutStudies());

        dialog.add(studyForm);
        dialog.setModal(false);
        dialog.setDraggable(true);
        Button cancelButton = new Button("Mégsem", e -> dialog.close());
        Button saveButton = new Button("Mentés", e -> {
            studyForm.save();
            PersonForm.this.person.getstudies().add(study);         // itt csak beállítjuk az értékeket, de csak a nagy mentés gombnál metnődik el
            study.setPerson(PersonForm.this.person);                // ugyanaz
            dialog.close();
            studyNameClass.add(study.getNameSchool());
            showStudiesList();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        return dialog;
    }

    /**
     * Kitölt gombra kattintva a listában
     * Create Dialog window Tanulmányok
     * @return
     */

    private void removeStudent(Study study) {
        Person person = study.getPerson();      // a student-ből kiveszi a person-t
        person.getstudies().remove(study);      // person-nak lekérdezi a student-jeit, majd kiveszi a sudent-et a person-ból
        study.setPerson(null);                  // a másik oldalon meg student-nél null-ra állítja a person-t
    }                                           // és mikor majd mentésre kerül -> akkor kiveszi majd a kapcsolatot automatikusan, szétkapcsolódnak de nem fogja törölni az adatbázisból, csak a kapcsolatot szedi szét
                                                // így már nem lesz köze a Person-hoz, mi meg a person-nal dolgozunk úgyis
    private void showStudiesList() {
        divstudies.removeAll();                                                                 // divből kitörlök mindent
        if (null == person) {                                                                   // a Persont később Setteljük be, így megnézzük, hogy megkaptuk-e
            return;
        }
        for (Study study : person.getstudies()) {                                               // ha vannak elemek, akkor azt végig Iteráljuk egy forEach-el
            StudyForm studyForm = createDialogLayoutStudies(study);                             // StudyForm létrehozása, mert a Binderhez kell, hogy egy FormLayout-unk legyen
            Dialog dialog = createStudyDialog(studyForm);
            H5 h5 = new H5(study.getNameSchool());
            if(studyNameClass.size()>0){
                for(String str: studyNameClass){
                    if(study.getNameSchool().equals(str)) h5.addClassName("study-name-change"); else h5.addClassName("study-name");
                }
            }
            Button buttonShow = new Button("Kitölt", e -> dialog.open());
            Button buttonClose = new Button("Töröl", e->removeStudent(study));
            add(dialog);

            Scroller sc1 = new Scroller();
            HorizontalLayout hl2 = new HorizontalLayout();
            hl2.add(buttonShow, buttonClose);
            VerticalLayout vl = new VerticalLayout();
            vl.add(h5,hl2);

            sc1.setContent(vl);

            divstudies.add(sc1);

            buttonClose.addClickListener(e -> sc1.setContent(null));
            createFooterStudies(dialog, studyForm, study);
        }
    }
    private static StudyForm createDialogLayoutStudies(Study study) {
        StudyForm fieldLayout = new StudyForm(study);

        fieldLayout.getStyle().set("width", "400px").set("max-width", "100%");

        return fieldLayout;
    }
    private Dialog createStudyDialog(StudyForm studyForm) {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");

        dialog.getHeader().add(createHeaderLayoutStudies());

        dialog.add(studyForm);
        dialog.setModal(false);
        dialog.setDraggable(true);
        return dialog;
    }
    private static H2 createHeaderLayoutStudies() {
        H2 headline = new H2("Tanulmányok");
        headline.addClassName("draggable");
        headline.getStyle().set("margin", "0").set("font-size", "1.5em")
                .set("font-weight", "bold")
                .set("cursor", "move")
                .set("padding", "var(--lumo-space-m) 0")
                .set("flex", "1");

        return headline;
    }
    private void createFooterStudies(Dialog dialog, StudyForm studyForm, Study study) {
        Button cancelButton = new Button("Mégsem", e -> dialog.close());
        Button saveButton = new Button("Mentés", e -> {
            studyForm.save();
            dialog.close();
            studyNameClass.add(study.getNameSchool());
            showStudiesList();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
    }


    /**
     * MOTIVÁCIÓS LEVÉL
     * Hozzáad gombra kattintva
     * Create Dialog window Motivációs levél
     */
    private void DialgCoverLetter(RichTextEditor coverLetter) {

        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Motivációs levél");

        dialog.getHeader().add(createHeaderLayoutCoverLetter());

        VerticalLayout dialogLayout = createDialogLayoutCoverLetter(coverLetter);
        dialog.add(dialogLayout);
        dialog.setModal(false);
        dialog.setDraggable(true);

        createFooterCoverLetter(dialog);

        add(dialog);

        divCoverLetter.addClickListener(event -> dialog.open());
    }

    private static H2 createHeaderLayoutCoverLetter() {
        H2 headline = new H2("Motivációs levél");
        headline.addClassName("draggable");
        headline.getStyle().set("margin", "0").set("font-size", "1.5em")
                .set("font-weight", "bold")
                .set("cursor", "move")
                .set("padding", "var(--lumo-space-m) 0")
                .set("flex", "1");

        return headline;
    }

    private static VerticalLayout createDialogLayoutCoverLetter(RichTextEditor coverLetter) {

        VerticalLayout fieldLayout = new VerticalLayout(coverLetter);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        fieldLayout.getStyle().set("width", "600px").set("max-width", "100%");

        return fieldLayout;
    }

    private static void createFooterCoverLetter(Dialog dialog) {
        Button cancelButton = new Button("Mégsem", e -> dialog.close());
        Button saveButton = new Button("Mentés", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
    }

}
