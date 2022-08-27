package com.example.app.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login | Önéletrajz adatok bevitele")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login = new LoginForm();
   private  LoginOverlay loginOverlay = new LoginOverlay();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        /*LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Header i18nHeader = i18n.getHeader();
        i18nHeader.setTitle("Önéletrajz adatok bevitele");
        i18nHeader.setDescription("Készítette: T.Tamás");

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Bejelentkezés");
        i18nForm.setUsername("Felhasználónév");
        i18nForm.setPassword("Jelszó");
        i18nForm.setSubmit("Belépés");
        i18nForm.setForgotPassword("Elfelejtett jelszó");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Nem jó a felhasználónév vagy a jelszó");
        i18nErrorMessage.setMessage("Ellenőrizd, hogy a megfelelő felhasználónevet és jelszót adtad-e meg, majd próbáld újra.");
        i18n.setErrorMessage(i18nErrorMessage);

        i18n.setAdditionalInformation("Ha nem tudnál belépni az adataiddal, akkor kérlek vedd fel a kapcsolatot velünk: admin@company.com");
        */

        //loginOverlay.setI18n(i18n);
        loginOverlay.setOpened(true);
        loginOverlay.getElement().setAttribute("no-autofocus", "");
        loginOverlay.getElement().getThemeList().add("dark");
        LoginI18n i18n = LoginI18n.createDefault();

        i18n.setAdditionalInformation("Ha nem tudnál belépni az adataiddal, akkor kérlek vedd fel a kapcsolatot velünk: admin@company.com");



        loginOverlay.setTitle("Önéletrajz adatok bevitele");
        loginOverlay.setDescription("Készítette: T.Tamás");
        loginOverlay.setOpened(true);
        loginOverlay.setError(true);
        loginOverlay.setI18n(i18n);
        loginOverlay.setAction("login");
        loginOverlay.getElement().setAttribute("no-autofocus", "");
        loginOverlay.getElement().getThemeList().add("dark");

        //login.setAction("login");

        //login.getElement().getThemeList().add("dark");
        //login.getElement().setAttribute("no-autofocus", "");


        add(
                //new H1("Önéletrajz adatok bevitele"),
                loginOverlay
                //login
        );



    }

    /**
     * belépési query kiolvasása, és hiba mutatása, ha valami gond van
     * @param beforeEnterEvent
     *            before navigation event with event details
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            //login.setError(true);
            loginOverlay.setError(true);
        }
    }
}
