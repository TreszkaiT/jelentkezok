package com.example.app.views.routenotfound;

import com.example.app.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;

import javax.servlet.http.HttpServletResponse;

@Route(value = "dashboard", layout = MainLayout.class)
@Tag(Tag.DIV)
public class RouteNotFoundErrorView extends Component
        implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {
//        getElement().setText("Could not navigate to '"
//                + event.getLocation().getPath()
//                + "'");

            ErrorDialog(event);

        return HttpServletResponse.SC_NOT_FOUND;
    }

    private Span status;

    public void ErrorDialog(BeforeEnterEvent event) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Nincs ilyen oldal! ('/" + event.getLocation().getPath() +" ')");
        dialog.setText("Kérlek írj be egy másik URL-t, vagy kattints az oldalon egy meglévő menüpontra!");

        dialog.setCancelable(true);
        dialog.setCancelText("Mégsem");
        dialog.addCancelListener(e -> setStatus("Canceled"));

        dialog.setRejectable(true);
        dialog.setRejectText("Elvetés");
        dialog.addRejectListener(e -> setStatus("Discarded"));

        dialog.setConfirmText("Tovább");
        dialog.addConfirmListener(e -> setStatus("Saved"));

//        Button button = new Button("Open confirm dialog");
//        button.addClickListener(e -> {
//            dialog.open();
//            status.setVisible(false);
//        });

        dialog.open();

        layout.add(status);//button, status);
//        this.getElement().add(layout);
//
//        // Center the button within the example
//        getStyle().set("position", "fixed").set("top","0").set("right", "0")
//                .set("bottom", "0").set("left", "0").set("display", "flex")
//                .set("align-items", "center").set("justify-content", "center");
    }

    private void setStatus(String value) {
//        status.setText("Status: " + value);
//        status.setVisible(true);
        this.getUI().ifPresent(ui -> ui.navigate(""));
    }
}