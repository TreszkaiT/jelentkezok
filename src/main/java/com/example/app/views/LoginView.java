package com.example.app.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
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

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Önéletrajz adatok bevitele");
        loginOverlay.setDescription("Készítette: T.Tamás");

        login.setAction("login");

        login.getElement().getThemeList().add("dark");
        login.getElement().setAttribute("no-autofocus", "");


        add(
                //new H1("Önéletrajz adatok bevitele"),
                loginOverlay,
                login
        );
        loginOverlay.setOpened(true);
        loginOverlay.getElement().setAttribute("no-autofocus", "");
        loginOverlay.getElement().getThemeList().add("dark");
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
            login.setError(true);
        }
    }
}
