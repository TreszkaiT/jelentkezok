package com.example.app.it;

import com.example.app.it.elements.LoginViewElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

public class LoginIT extends AbstractTest {
    public LoginIT() {
        super("");
    }

    @Test
    public void loginAsValidUserSucceeds() {
//        // Find the LoginForm used on the page
//        LoginFormElement form = $(LoginFormElement.class).first();
//        // Enter the credentials and log in
//        form.getUsernameField().setValue("u");
//        form.getPasswordField().setValue("p");
//        form.getSubmitButton().click();
//        // Ensure the login form is no longer visible
//        Assert.assertFalse($(LoginFormElement.class).exists());  // $ = new element selector
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertTrue(loginView.login("u", "p"));
    }

    @Test
    public void loginAsInvalidUserFails() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertFalse(loginView.login("user", "invalid"));
    }
}
