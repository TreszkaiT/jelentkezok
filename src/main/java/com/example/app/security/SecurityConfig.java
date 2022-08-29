package com.example.app.security;


import com.example.app.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * VaadinWebSecurityConfigurerAdapter adapter használatával
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {

    /**
     * a belépő képernyőt a saját megjelenésünkre állítjuk
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    /**
     * no ignore Image folder
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");       // minden kép mappában lévő fájlt letiltunk
        web.ignoring().antMatchers("/images/**");       // minden kép mappában lévő fájlt letiltunk
        super.configure(web);
    }

    /**
     * egy Usert kell definiálni a rendszerhez
     * Demo UserDetailService, which only provides two hardcoded
     * in-memory users and their roles.
     * NOTE: This should not be used in real-world applications.
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.withUsername("u")
                .password("{noop}p")
                .roles("USER")
                .build());
    }
}