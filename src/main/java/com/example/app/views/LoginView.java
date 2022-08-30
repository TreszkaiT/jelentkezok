package com.example.app.views;

import com.example.app.service.component.Product;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login | Önéletrajz adatok bevitele")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login = new LoginForm();
   private  LoginOverlay loginOverlay = new LoginOverlay();

   private Product product;

    public LoginView(Product product){
        this.product = product;
        addClassName("login-view");
        //setSizeFull();
        //setAlignItems(Alignment.CENTER);
        //setJustifyContentMode(JustifyContentMode.CENTER);

        //LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Önéletrajz adatokat tároló alkalmazás v."+product.getVersion());
        loginOverlay.setDescription("Készítette: T.Tamás");
        loginOverlay.setOpened(true);
        loginOverlay.setError(true);
        loginOverlay.setAction("login");
        loginOverlay.setI18n(createLoginI18n());

        add(
                //new H1("Önéletrajz adatok bevitele"),
                loginOverlay
                //login
        );
        //loginOverlay.setOpened(true);
        loginOverlay.getElement().setAttribute("no-autofocus", "");
        loginOverlay.getElement().getThemeList().add("dark");
        this.getElement().getStyle().set("background-image", "images/art_back.jpg");

    }

    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();

        //LoginI18n.Header i18nHeader = i18n.getHeader();
        //i18nHeader.setTitle("Önéletrajz adatok bevitele");
        //i18nHeader.setDescription("Készítette: T.Tamás");

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
    /*
        // define all visible Strings to the values you want
        // this code is copied from above-linked example codes for Login
        // in a truly international application you would use i.e. `getTranslation(USERNAME)` instead of hardcoded string values. Make use of your I18nProvider
        //i18n.getHeader().setTitle("Nome do aplicativo");
        //i18n.getHeader().setDescription("Descrição do aplicativo");
        i18n.getForm().setUsername("Usuário"); // this is the one you asked for.
        i18n.getForm().setTitle("Acesse a sua conta");
        i18n.getForm().setSubmit("Entrar");
        i18n.getForm().setPassword("Senha");
        i18n.getForm().setForgotPassword("Esqueci minha senha");
        i18n.getErrorMessage().setTitle("Usuário/senha inválidos");
        i18n.getErrorMessage()
                .setMessage("Confira seu usuário e senha e tente novamente.");
        i18n.setAdditionalInformation(
                "Caso necessite apresentar alguma informação extra para o usuário"
                        + " (como credenciais padrão), este é o lugar.");
        */
        return i18n;
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
