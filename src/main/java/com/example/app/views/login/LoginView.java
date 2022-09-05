package com.example.app.views.login;

import com.example.app.viewcontroller.ProductController;
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

    private LoginOverlay loginOverlay = new LoginOverlay();

    private ProductController productController;

    public LoginView(ProductController productController) {
        this.productController = productController;
        addClassName("login-view");

        loginOverlay.setTitle("Önéletrajz adatokat tároló alkalmazás v." + productController.getProductVersion());
        loginOverlay.setDescription("Készítette: T.Tamás");
        loginOverlay.setOpened(true);
        loginOverlay.setError(false);
        loginOverlay.setAction("login");
        loginOverlay.setI18n(createLoginI18n());

        add(
                loginOverlay
        );

        loginOverlay.getElement().setAttribute("no-autofocus", "");
        loginOverlay.getElement().getThemeList().add("dark");
        this.getElement().getStyle().set("background-image", "images/art_back.jpg");

    }

    private LoginI18n createLoginI18n() {
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

        return i18n;
    }

    /**
     * belépési query kiolvasása, és hiba mutatása, ha valami gond van
     *
     * @param beforeEnterEvent before navigation event with event details
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginOverlay.setError(true);
        }
    }
}
