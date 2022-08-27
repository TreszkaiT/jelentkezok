package com.example.app.views;


import com.example.app.security.SecurityService;
import com.example.app.views.pages.ListView;
import com.example.app.views.pages.StartingDataUpload;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Adatok")
//@Route(value = "")
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
        //setPrimarySection(Section.DRAWER);
        //addToNavbar(true, createHeaderContent());
        //addToDrawer(createDrawerContent());
        //SetButtonAppPropertyValue(1,1);
    }


    private void createHeader() {
        H1 logo = new H1("Önéletrajz készítő alkalmazás");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Kilépés", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

private void createDrawer() {
        RouterLink listLink = new RouterLink("Szamélyek listája", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
        listLink,
        new RouterLink("Adatszótárak feltöltése", StartingDataUpload.class)
        ));
        }



    /* private Component createHeaderContent() {
         DrawerToggle toggle = new DrawerToggle();
         toggle.addClassNames("view-toggle");
         toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
         toggle.getElement().setAttribute("aria-label", "Menu toggle");

         viewTitle = new H1();
         viewTitle.addClassNames("view-title");

         Button logOut = new Button("Kilépés", e -> securityService.logout());

         Header header = new Header(toggle, viewTitle, logOut);
         header.addClassNames("view-header");
         header
         header.setWidth("100%");
         return header;
     }

    private Component createDrawerContent() {
        H2 appName = new H2("Önéletrajz adatok bevitele");
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
    }*/
}
