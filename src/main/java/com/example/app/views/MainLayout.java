package com.example.app.views;


import com.example.app.viewcontroller.ProductController;
import com.example.app.viewcontroller.SecurityController;
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

    private SecurityController securityController;
    private ProductController productController;

    public MainLayout(ProductController productController, SecurityController securityController) {           // v. így használok a Product Component Bean-ból; így nem lesz null
        this.productController = productController;
        this.securityController = securityController;

        createHeader();
        createDrawer();
    }


    private void createHeader() {
        H1 logo = new H1("Önéletrajz adatokat tároló alkalmazás v." + productController.getProductVersion());
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Kilépés", e -> securityController.Logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");
        header.getElement().getThemeList().add("dark");
        header.addClassNames("header-border");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Szamélyek listája", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        VerticalLayout menuBar = new VerticalLayout();
        menuBar.add(listLink,
                new RouterLink("Adatszótárak feltöltése", StartingDataUpload.class));
        menuBar.setHeightFull();
        menuBar.getElement().getThemeList().add("dark");
        addToDrawer(menuBar);
    }
}
