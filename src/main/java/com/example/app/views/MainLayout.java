package com.example.app.views;


import com.example.app.views.appnav.AppNav;
import com.example.app.views.pages.ListView;
import com.example.app.views.pages.StartingDataUpload;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

import static com.example.app.data.properties.SetProperties.SetButtonAppPropertyValue;

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Önéletrajz adatok")
//@Route(value = "")
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
        //SetButtonAppPropertyValue(1,1);
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Menü");
        appName.addClassNames("app-name");

        RouterLink listLink = new RouterLink("Szamélyek listája", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        var section = new com.vaadin.flow.component.html.Section(
                appName,
                listLink,
                new RouterLink("Adatszótárak feltöltése", StartingDataUpload.class),
                createNavigation(),
                createFooter()
        );
        section.addClassNames("drawer-section");
        return section;
    }


    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();
        nav.addClassNames("app-nav");

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("app-nav-footer");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
