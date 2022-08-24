package com.example.app.views.pages;

import com.example.app.data.entity.City;
import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.entity.Person;
import com.example.app.views.pages.upload.UploadPictureI18N;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

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
    DatePicker szulDatum        = new DatePicker ("Születési dátum");
    TextField phone             = new TextField("Telefonszám");
    TextField address           = new TextField("Lakcím");
    TextField kozMedia          = new TextField("Közösségi média");
    TextField messageApps       = new TextField("Üzenetküldő appok");
    TextField webSite           = new TextField("Website");
    TextField tanulmanyok       = new TextField("Tanulmányok");
    TextField szakmaiTap        = new TextField("Szakmai Tapasztalat");
    TextField egyebKeszsegek    = new TextField("Egyéb készségek");
    TextField motivaciosLevel   = new TextField("Motivációs levél");

   // FileBuffer fileBuffer = new FileBuffer();
    //Upload singleFileUpload = new Upload(fileBuffer);

    MemoryBuffer buffer = new MemoryBuffer();
    Upload upload = new Upload(buffer);
   // Div output = new Div();
    //String relativeWebPath = "/WEB-INF/uploads";
    //String absoluteFilePath = getServletContext().getRealPath(relativeWebPath);
    //File uploadedFile = new File(absoluteFilePath, FilenameUtils.getName(item.getName()));

    /*Upload upload = new Upload((MultiFileReceiver) (filename, mimeType) -> {
        File file = new File(new File("uploaded-files"), filename);
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
    });*/

    Scroller scroller = new Scroller();

    TextField picture           = new TextField("Fénykép");

    ComboBox<Nyelvismeret>  nyelvIsmeret    = new ComboBox<>("Nyelvismeret");
    //MultiselectComboBox <Nyelvismeret>  nyelvIsmeret    = new MultiselectComboBox<>("Nyelvismeret");
    ComboBox<City>          city            = new ComboBox<>("Város");
    //MultiselectComboBox<City>          city            = new MultiselectComboBox<>("Város");

    Button save     = new Button("Save");
    Button delete   = new Button("Delete");
    Button cancel   = new Button("Cancel");

    private Person person;
    //private AppService service;

    public PersonForm(List<City> cities, List<Nyelvismeret> nyelvIsmeretek){//}, AppService services) {
        //this.service = services;
        addClassName("contact-form");
        binder.bindInstanceFields(this);        // a binder meghívása itt van. És this elég !!! azért, mert az itt lévő fenti nevek megyegyeznek a Person osztályban lévő nevekkel!!!

        city.setItems(cities);//service.findAllCities());//cities);
        city.setItemLabelGenerator(City::getName);                      // mit jelenítsünk meg a ComboBoxban

        nyelvIsmeret.setItems(nyelvIsmeretek);
        nyelvIsmeret.setItemLabelGenerator(Nyelvismeret::getName);

        add(
          firstName,
          lastName,
          email,
          szulDatum,
          phone,
          address,
          kozMedia,
          messageApps,
          webSite,
          tanulmanyok,
          szakmaiTap,
          egyebKeszsegek,
          motivaciosLevel,
          picture,
          upload,
              // output,
          scroller,
                //singleFileUpload,
          city,
          nyelvIsmeret,
          createButtonLayout()
        );

        final String[] pictName = {""};
        final String[] s = {""};
        //upload.setAcceptedFileTypes("application/jpg", ".jpg");
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        int maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB
        upload.setMaxFileSize(maxFileSizeInBytes);

        upload.setMaxFiles(1);
        UploadPictureI18N i18n = new UploadPictureI18N();
        i18n.getError()
                .setTooManyFiles(
                        "You may only upload a maximum of three files at once.");
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

        upload.addSucceededListener(event -> {
            // Get information about the uploaded file
            InputStream fileData = buffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            picture.setValue(fileName);

            pictName[0] = fileName;
            // Do something with the file data
            // processFile(fileData, fileName, contentLength, mimeType);

            //add(new H1(event.getMIMEType()));
            Component component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
            //hideOutput(event.getFileName(), component, output);
            //output.removeAll();
            scroller.setContent(null);
            showOutput(event.getFileName(), component, scroller);

            String resourcesPath = "src/main/resources/images/";//"\\src\\main\\resources\\";
            File targetFile = new File(resourcesPath+fileName);

            Path currentRelativePath = Paths.get("");
            s[0] = (currentRelativePath.toAbsolutePath().toString()+resourcesPath);

                // fájl feltöltése
            try {
                FileUtils.copyInputStreamToFile(buffer.getInputStream(), targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*OutputStream outStream = null;
            try {
                outStream = new FileOutputStream(targetFile);
                outStream.write(buffer.getInputStream());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

           /* InputStream inputStream = buffer.getInputStream(fileName);

            if(inputStream != null) {
                FileUploadDto fileUploadDto = new FileUploadDto();
                fileUploadDto.setFileName(event.getFileName());
                fileUploadDto.setMimeType(event.getMIMEType());
                try {
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    fileUploadDto.setByteArrayDocument(bytes);
                    fileUploadDtoList.add(fileUploadDto);

                } catch (IOException exception) {
                    log.error(exception.getMessage());
                }
            }*/
            /*if (component != null) {
                vlowerLayout.remove(component);

            }*/
                    //  File uploadnál a clear gombra kattintva X gombra
            //component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
            if (component instanceof Image) {
                upload.getElement().addEventListener("file-remove",event1 -> {
                    //System.out.println("File remove");
                    scroller.setContent(null);
                    targetFile.delete();
                });
                // upload.getElement().addEventData("event.detail.file.name");
            }
            //p = new HtmlComponent(Tag.H3);
            //p.getElement().setText(event.getFileName());
            //vlowerLayout.add(component);





            //System.out.println(upload.getElement().isEnabled());
            /*scroller.setWidth("200px");
            scroller.setHeight("200px");
            StreamResource imageResource = new StreamResource(""+pictName[0],
                    () -> getClass().getResourceAsStream("/images/"+pictName[0]));    //"/images/jo18.png"));//
            //String imageResource = pictName[0];
            System.out.println(imageResource);
            Image img = new Image(imageResource, "No image!");
            scroller.setContent(img);*/

        });


        //System.out.println(upload.getElement().isEnabled());
        /*singleFileUpload.addSucceededListener(event -> {
            // Get information about the file that was written to the file system
            FileData savedFileData = fileBuffer.getFileData();
            String absolutePath = savedFileData.getFile().getAbsolutePath();

            System.out.printf("File saved to: %s%n", absolutePath);
        });*/

    }

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
    /*private void hideOutput(String text, Component content,
                            Scroller outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add("");
        outputContainer.remove(content);
    }*/

    /*public void FileCopyPicture(String selectedFile, ){
        private Path to;
        private Path from;
        private File selectedFile;

        // fájl bemásolása
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current absolute path is: " + s);

        selectedFile = upload.getElement().getChild(0)..getChild();//.getSelectedFile();
        from = Paths.get(selectedFile.toURI());
        to = Paths.get(s+"\\" + selectedFile.getName()); //to = Paths.get(s+"\\src\\main\\resources\\" + selectedFile.getName());
        try {
            // Files.copy(from.toFile(), to.toFile()); //gives a 'cannot resolve method error
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println("fd");
        }
    }*/
    /*private static File getUploadFolder() {
        File folder = new File("uploaded-files");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }*/

    public void setPerson(Person person){
        this.person = person;
        binder.readBean(person);        // Binder beolvassa ezt a persont, és ezek a fentebbi mezők az add( firstName, ...  ez alapján töltődnek fel
    }

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

    private void validateAndSave() {
       // try{
            binder.readBean(person);
            fireEvent(new SaveEvent(this, person));
       // } catch (ValidationException e){
        //    e.printStackTrace();
       // }
    }

    // Events
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
