package com.jaiBalramTraders.Button;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;  // For showing notifications

public class DailyReportButton extends Button {
    public DailyReportButton(VerticalLayout optionsLayout) {
        super("Reports", VaadinIcon.FILE_TEXT.create());
        setTooltipText("View daily reports");

        // Set button color and text style
        this.getStyle().set("background-color", "#1E90FF")
                        .set("color", "#FFFFFF")
                        .set("border-radius", "5px")
                        .set("font-weight", "bold");

        addClickListener(event -> {
            // Clear the previous options
            optionsLayout.removeAll();

            // Create the "By Date" button with a decent color
            Button dailyReportByDate = new Button("By Date", VaadinIcon.CALENDAR.create());
            dailyReportByDate.getStyle().set("background-color", "#4682B4")  // SteelBlue color for the button
                                       .set("color", "#FFFFFF")
                                       .set("border-radius", "5px")
                                       .set("font-weight", "bold");

            // Add the "By Date" button to the black area on the left
            optionsLayout.add(dailyReportByDate);
            optionsLayout.setAlignItems(Alignment.START);  // Align to the left
            optionsLayout.getStyle().set("background-color", "#000000");  // Black background
            optionsLayout.setWidth("100%");

            // Add a click listener for "By Date"
            dailyReportByDate.addClickListener(dateEvent -> showDatePicker(optionsLayout, dailyReportByDate));
        });
    }

    private void showDatePicker(VerticalLayout optionsLayout, Button dailyReportByDate) {
        // Remove any existing components before adding new ones
        optionsLayout.removeAll();

        // Create a layout with Flexbox for the black left area and centered DatePicker
        HorizontalLayout mainLayout = new HorizontalLayout();

        // Black area for the "By Date" button
        VerticalLayout leftArea = new VerticalLayout();
        leftArea.getStyle().set("background-color", "#000000")  // Black background for left side
                        .set("color", "#FFFFFF")
                        .set("padding", "20px")
                        .set("width", "200px");  // Fixed width for the black area

        // Create the "By Date" button and add it to the left area
        Button dailyReportByDate1 = new Button("By Date", VaadinIcon.CALENDAR.create());
        dailyReportByDate1.getStyle().set("background-color", "#4682B4")  // SteelBlue color for the button
                           .set("color", "#FFFFFF")
                           .set("border-radius", "5px")
                           .set("font-weight", "bold");
        leftArea.add(dailyReportByDate1);

        // DatePicker in the white background area
        VerticalLayout centerArea = new VerticalLayout();
        centerArea.getStyle().set("background-color", "#FFFFFF")  // White background for center area
                             .set("padding", "20px")
                             .set("width", "100%");  // Take up the remaining space

        DatePicker datePicker = new DatePicker("Select Date");
        datePicker.setValue(java.time.LocalDate.now());  // Default value as today
        datePicker.getStyle().set("background-color", "#FFFFFF")  // White background for the DatePicker
                             .set("color", "#000000")  // Black text
                             .set("border-radius", "10px")  // Rounded corners
                             .set("padding", "10px")  // Padding for comfort
                             .set("width", "250px");  // Set a fixed width for the DatePicker
        centerArea.add(datePicker);

        // Create Submit button for fetching the report
        Button submitButton = new Button("Submit", VaadinIcon.CHECK.create());
        submitButton.getStyle().set("background-color", "#4CAF50")  // Green color for submit button
                      .set("color", "#FFFFFF")
                      .set("border-radius", "5px")
                      .set("font-weight", "bold");

        // Add a click listener for Submit button to generate report
        submitButton.addClickListener(submitEvent -> {
            java.time.LocalDate selectedDate = datePicker.getValue();
            // Add logic here to fetch the daily report for the selected date
            Notification.show("Fetching daily report for: " + selectedDate);
        });

        // Add the Submit button below the DatePicker
        centerArea.add(submitButton);

        // Add the left area and center area to the main layout
        mainLayout.add(leftArea, centerArea);
        mainLayout.setWidth("100%");

        // Add the main layout to the optionsLayout
        optionsLayout.add(mainLayout);
    }
}
