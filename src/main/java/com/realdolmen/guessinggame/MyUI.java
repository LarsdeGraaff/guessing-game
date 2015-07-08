package com.realdolmen.guessinggame;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.client.ui.ResetButton;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.realdolmen.guessinggame.MyAppWidgetset")
public class MyUI extends UI {
    private Label label = new Label();
    int kans = 3;
    int randomGetal = randomGetal();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        layout.setMargin(true);

        setContent(layout);


        final TextField textField = new TextField();
        layout.addComponent(new Label("Geef een nummer tussen 1 en 10"));
        layout.addComponent(textField);


        final Button button = new Button("guess");
        final Button resetButton = new Button("RESET");

        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                layout.addComponent(new Label("Random number: " + randomGetal + " / your number " + textField.getValue()));


                if (randomGetal == Integer.parseInt(textField.getValue())) {
                    label.setCaption("PROFICIAT");

                    layout.replaceComponent(button, resetButton);
                } else {
                    kans = kans - 1;

                    if (kans != 0) {
                        label.setCaption("fout, nog " + kans + " kansen");

                        textField.setValue("");

                    }
                    if (kans == 0) {
                        label.setCaption("GAME OVER");

                        textField.setValue("");
                        layout.replaceComponent(button, resetButton);
                    }

                }
            }
        });


        resetButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                layout.replaceComponent(resetButton, button);
                label.setCaption("");
                kans = 3;

                randomGetal = randomGetal();
            }


        });

        layout.addComponent(button);
        layout.addComponent(label);


    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


    public int randomGetal() {
        return (int) Math.round(Math.random() * (10 - 1) + 1);
    }
}
