package com.example.app.views.pages;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import com.example.app.views.pages.upload.UploadPictureI18N;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
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
import java.util.Iterator;
import java.util.List;


public class PersonForm extends FormLayout {

    Binder<Person> binder = new BeanValidationBinder<>(Person.class);             // összekapcsol egy modul objektumor egy Components-el    BeanValidationBinder:a Person osztály validation Annotációit használja a formelemkre is, így már nekünk ezt nem kell megtenni a nézetnél külön

    TextField firstName         = new TextField("Vezetéknév");              // automatikus Bind-elés miatt a nevük itt egyezzen meg a Person osztályban lévő nevekkel!!!
    TextField lastName          = new TextField("Keresztnév");
    EmailField email            = new EmailField("Email");
    DatePicker bornDate         = new DatePicker ("Születési dátum");
    TextField phone             = new TextField("Telefonszám");
    TextField address           = new TextField("Lakcím");
    TextField socialMedia       = new TextField("Közösségi média");
    TextField messageApps       = new TextField("Üzenetküldő appok");
    TextField webSite           = new TextField("Website");
    TextField studies           = new TextField("Tanulmányok");
    TextField profExperience    = new TextField("Szakmai Tapasztalat");
    TextField otherSkill        = new TextField("Egyéb készségek");
    //TextField coverLetter   = new TextField("Motivációs levél");

    H5 h5                       = new H5("Motivációs levél");
    RichTextEditor coverLetter  = new RichTextEditor();

   // FileBuffer fileBuffer = new FileBuffer();
    //Upload singleFileUpload = new Upload(fileBuffer);

    MemoryBuffer buffer = new MemoryBuffer();
    Upload upload = new Upload(buffer);

    Scroller scroller = new Scroller();

    TextField picture           = new TextField("Fénykép");

    MultiselectComboBox<Language> language    = new MultiselectComboBox<>("Nyelvismeret");
    ComboBox<City>          city            = new ComboBox<>("Város");

    Button save     = new Button("Mentés");
    Button delete   = new Button("Törlés");
    Button cancel   = new Button("Mégsem");

    private Person person;

    public PersonForm(List<City> cities, List<Language> languages){//}, AppService services) {

        addClassName("contact-form");

        binder.bindInstanceFields(this);        // a binder meghívása itt van. És this elég !!! azért, mert az itt lévő fenti nevek megyegyeznek a Person osztályban lévő nevekkel!!!

        city.setItems(cities);
        city.setItemLabelGenerator(City::getName);                      // mit jelenítsünk meg a ComboBoxban

        language.setItems(languages);
        language.setItemLabelGenerator(Language::getName);

        h5.setClassName("h5-style");

       // TextArea textArea = new TextArea("Html Value", "Type html string here to set it as value to the Rich Text Editor above...");
       // textArea.setWidthFull();
       // coverLetter1.setValue(textArea.getValue());//Person::getcoverLetter);
       //coverLetter1.setLabel("Motivációs levél");

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
          studies,
          profExperience,
          otherSkill,
          h5,coverLetter,
          picture,
          upload,
          scroller,
          city,
          language,
          createButtonLayout()
        );


        UploadBeallitasa();

        UploadUtanKepBetoltese();

        //this.getElement().getThemeList().add("dark");

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
        //upload.getElement().getThemeList().add("dark");
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
            File targetFile = new File(resourcesPath+fileName);

            Path currentRelativePath = Paths.get("");
            s[0] = (currentRelativePath.toAbsolutePath().toString()+resourcesPath);

            // fájl feltöltése
            try {
                FileUtils.copyInputStreamToFile(buffer.getInputStream(), targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //  File uploadnál a clear gombra kattintva X gombra
            //component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
            if (component instanceof Image) {
                upload.getElement().addEventListener("file-remove",event1 -> {
                    //System.out.println("File remove");
                    scroller.setContent(null);
                    targetFile.delete();
                });
            }


        });
    }

    /**
     * Image feltöltése, és eseménykezelése
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
        //outputContainer.add(p);
        //outputContainer.add(content);
    }

    /**
     * Buttons Layer összeállítása
     * @return
     */
    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, person)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }


    /**
     * Person adatok beállítása és mentése
     * @param person
     */
    public void setPerson(Person person){
        this.person = person;
        binder.readBean(person);        // Binder beolvassa ezt a persont, és ezek a fentebbi mezők az add( firstName, ...  ez alapján töltődnek fel
    }

    private void validateAndSave() {
        try {
            binder.writeBean(person);
            fireEvent(new SaveEvent(this, person));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

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
        SaveEvent(PersonForm source, Person person) {
            super(source, person);
        }
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

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
