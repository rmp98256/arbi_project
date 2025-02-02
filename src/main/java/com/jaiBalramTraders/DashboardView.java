package com.jaiBalramTraders;

import com.jaiBalramTraders.Button.BuyerDataButton;
import com.jaiBalramTraders.Button.CustomerDataButton;
import com.jaiBalramTraders.Button.DailyReportButton;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        // Create header with dark green background
        Div header = new Div();
        header.getStyle().set("background-color", "#006400") // Dark green background color
                      .set("padding", "10px 0px")
                      .set("text-align", "center")
                      .set("display", "flex")
                      .set("align-items", "center")
                      .set("justify-content", "space-between");

        // Create a container for the title to keep it centered
        Div titleContainer = new Div();
        titleContainer.getStyle().set("flex-grow", "1"); // Allow title to grow and center it

        // Title inside the header
        H2 title = new H2("JaiBalram Traders");
        title.getStyle().set("color", "#FF9933")  // Saffron color for the title
                        .set("margin", "0");    // Remove margin to center title properly
        titleContainer.add(title);  // Add title to the container

        // Add Login Button in header
        Button loginButton = new Button("Login", VaadinIcon.SIGN_IN.create());
        loginButton.getStyle().set("background-color", "#FFD700")  // Golden color for the button
                              .set("color", "#000000")  // Black text for the button
                              .set("border-radius", "5px")
                              .set("font-weight", "bold")
                              .set("padding", "10px 20px");

        // Add the title container and login button to the header
        header.add(titleContainer, loginButton);

        // Main Buttons with Icons
        // Create the options layout
        VerticalLayout optionsLayout = new VerticalLayout();
        
        
        
        
        // Initialize buttons using the custom classes and pass optionsLayout to them
        CustomerDataButton customerDataButton = new CustomerDataButton(optionsLayout);
        BuyerDataButton buyerDataButton = new BuyerDataButton(optionsLayout);
        DailyReportButton dailyReportButton = new DailyReportButton(optionsLayout);

        // Create the main layout and options layout
        HorizontalLayout mainButtonsLayout = new HorizontalLayout(customerDataButton, buyerDataButton, dailyReportButton);

        // Add the header, main layout, and options layout to the UI
        add(header, mainButtonsLayout, optionsLayout);
    }
}
