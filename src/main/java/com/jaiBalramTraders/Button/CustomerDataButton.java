package com.jaiBalramTraders.Button;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.notification.Notification;  // For showing notifications
import com.vaadin.flow.component.notification.NotificationVariant;

public class CustomerDataButton extends Button {
    public CustomerDataButton(VerticalLayout optionsLayout) {
        super("Customers", VaadinIcon.USER.create());
        setTooltipText("Manage Customers");

        // Set button color and text style
        this.getStyle().set("background-color", "#FF6347")
                        .set("color", "#FFFFFF")
                        .set("border-radius", "5px")
                        .set("font-weight", "bold");

        addClickListener(event -> {
            // Clear the previous options
            optionsLayout.removeAll();

            // Create buttons for the Customers tab (e.g., Add, View)
            Button addCustomerButton = new Button("Add Customer", VaadinIcon.PLUS_CIRCLE.create());
            Button viewCustomerButton = new Button("View Customers", VaadinIcon.EYE.create());

            // Set button colors and styles
            addCustomerButton.getStyle().set("background-color", "#32CD32")
                                    .set("color", "#FFFFFF")
                                    .set("border-radius", "5px")
                                    .set("font-weight", "bold");
            viewCustomerButton.getStyle().set("background-color", "#FFD700")
                                     .set("color", "#FFFFFF")
                                     .set("border-radius", "5px")
                                     .set("font-weight", "bold");

            // Add buttons to the left section
            optionsLayout.add(addCustomerButton, viewCustomerButton);
            optionsLayout.setAlignItems(Alignment.START);  // Align to the left
            optionsLayout.getStyle().set("background-color", "#000000");  // Black background
            optionsLayout.setWidth("100%");

            // Add listeners for each button
            addCustomerButton.addClickListener(addEvent -> showAddCustomerForm(optionsLayout));
            viewCustomerButton.addClickListener(viewEvent -> showCustomerList(optionsLayout));
        });
    }
    

    private void showAddCustomerForm(VerticalLayout optionsLayout) {
    	 optionsLayout.removeAll();
    	 HorizontalLayout mainLayout = new HorizontalLayout();
    	 VerticalLayout leftArea = new VerticalLayout();
         leftArea.getStyle().set("background-color", "#000000")  // Black background for left side
                         .set("color", "#FFFFFF")
                         .set("padding", "20px")
                         .set("width", "200px");  // Fixed width for the black area

         Button addCustomerButton = new Button("Add Customer", VaadinIcon.PLUS_CIRCLE.create());
         addCustomerButton.getStyle().set("background-color", "#32CD32")  // Green button
                             .set("color", "#FFFFFF")
                             .set("border-radius", "5px")
                             .set("font-weight", "bold");
         leftArea.add(addCustomerButton);
         
         Button viewCustomerButton = new Button("View Customers", VaadinIcon.EYE.create());
         viewCustomerButton.getStyle().set("background-color", "#FFD700")  // Yellow button for view
                            .set("color", "#FFFFFF")
                            .set("border-radius", "5px")
                            .set("font-weight", "bold");
         leftArea.add(viewCustomerButton);
         addCustomerButton.addClickListener(addEvent -> showAddCustomerForm(optionsLayout));
         viewCustomerButton.addClickListener(viewEvent -> showCustomerList(optionsLayout));
         // Form in the white background area
         VerticalLayout layout= new  MultiRowInputForm();
    	 optionsLayout.add(mainLayout,layout);
    	 
    }

    private void showAddCustomerForm_1(VerticalLayout optionsLayout) {
        // Remove existing components before adding new ones
        optionsLayout.removeAll();

        // Create a layout with Flexbox for the black left area and centered form
        HorizontalLayout mainLayout = new HorizontalLayout();

        // Left area for the "Add Customer" button
        VerticalLayout leftArea = new VerticalLayout();
        leftArea.getStyle().set("background-color", "#000000")  // Black background for left side
                        .set("color", "#FFFFFF")
                        .set("padding", "20px")
                        .set("width", "200px");  // Fixed width for the black area

        Button addCustomerButton = new Button("Add Customer", VaadinIcon.PLUS_CIRCLE.create());
        addCustomerButton.getStyle().set("background-color", "#32CD32")  // Green button
                            .set("color", "#FFFFFF")
                            .set("border-radius", "5px")
                            .set("font-weight", "bold");
        leftArea.add(addCustomerButton);
        
        Button viewCustomerButton = new Button("View Customers", VaadinIcon.EYE.create());
        viewCustomerButton.getStyle().set("background-color", "#FFD700")  // Yellow button for view
                           .set("color", "#FFFFFF")
                           .set("border-radius", "5px")
                           .set("font-weight", "bold");
        leftArea.add(viewCustomerButton);
        addCustomerButton.addClickListener(addEvent -> showAddCustomerForm(optionsLayout));
        viewCustomerButton.addClickListener(viewEvent -> showCustomerList(optionsLayout));
        // Form in the white background area
        VerticalLayout centerArea = new VerticalLayout();
        centerArea.getStyle().set("background-color", "#FFFFFF")  // White background for form area
                         .set("padding", "20px")
                         .set("width", "100%");

        // Form Table-like structure
        Grid<FormData> customerGrid = new Grid<>(FormData.class);
        
        // Create a list to hold the grid items
        List<FormData> customerDataList = new ArrayList<>();
        customerGrid.setItems(customerDataList);  // Set grid to empty initially
     // Enable inline editing
        Editor<FormData> editor = customerGrid.getEditor();
        editor.setBuffered(false);

        // Binder to bind data changes
        Binder<FormData> binder = new Binder<>(FormData.class);
        editor.setBinder(binder);
        
     // Create editable fields for each column
        TextField farmerNameField = new TextField();
        binder.bind(farmerNameField, FormData::getFarmerName, FormData::setFarmerName);

        TextField noOfBagsField = new TextField();
        binder.bind(noOfBagsField, formData -> String.valueOf(formData.getNoOfBags()), 
                    (formData, value) -> formData.setNoOfBags(Integer.parseInt(value)));

        TextField bagWeightField = new TextField();
        binder.bind(bagWeightField, formData -> String.valueOf(formData.getBagWeight()), 
                    (formData, value) -> formData.setBagWeight(Double.parseDouble(value)));

        TextField ratePerBagField = new TextField();
        binder.bind(ratePerBagField, formData -> String.valueOf(formData.getRatePerBag()), 
                    (formData, value) -> formData.setRatePerBag(Double.parseDouble(value)));

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setItems("Incomplete", "Complete", "Complete Without Transportation");
        binder.bind(statusComboBox, FormData::getStatus, FormData::setStatus);

        DatePicker datePicker = new DatePicker();
        binder.bind(datePicker, FormData::getDate, FormData::setDate);

     // Set columns as editable

//        customerGrid.addColumn(FormData::getFarmerName).setHeader("Farmer Name")
//        .setEditorComponent(farmerNameField).setSortable(false);
//	    customerGrid.addColumn(FormData::getNoOfBags).setHeader("No of Bags")
//	        .setEditorComponent(noOfBagsField).setSortable(false);
//	    customerGrid.addColumn(FormData::getBagWeight).setHeader("Bag Weight")
//	        .setEditorComponent(bagWeightField).setSortable(false);
//	    customerGrid.addColumn(FormData::getRatePerBag).setHeader("Rate per Bag")
//	        .setEditorComponent(ratePerBagField).setSortable(false);
//	    customerGrid.addColumn(FormData::getStatus).setHeader("Status")
//	        .setEditorComponent(statusComboBox).setSortable(false);
//        customerGrid.addColumn(FormData::getFarmerName).setHeader("Farmer Name").setEditorComponent(farmerNameField);
//        customerGrid.addColumn(FormData::getNoOfBags).setHeader("No of Bags").setEditorComponent(noOfBagsField);
//        customerGrid.addColumn(FormData::getBagWeight).setHeader("Bag Weight").setEditorComponent(bagWeightField);
//        customerGrid.addColumn(FormData::getRatePerBag).setHeader("Rate per Bag").setEditorComponent(ratePerBagField);
//        customerGrid.addColumn(FormData::getStatus).setHeader("Status").setEditorComponent(statusComboBox);
//        customerGrid.addColumn(FormData::getDate).setHeader("Date").setEditorComponent(datePicker);

        // Allow editing when a row is double-clicked
        customerGrid.addItemDoubleClickListener(event -> editor.editItem(event.getItem()));
     // Add row button
        Button addRowButton = new Button("Add Row", VaadinIcon.PLUS.create(), click -> {
            FormData newRow = new FormData("", 0, 0.0, 0.0, "Incomplete", LocalDate.now());
            customerDataList.add(newRow);
            customerGrid.getDataProvider().refreshAll();
            editor.editItem(newRow);  // Auto-enable editing on new row
        });
        customerGrid.setSelectionMode(Grid.SelectionMode.MULTI);
     // Delete selected rows button
        Button deleteRowButton = new Button("Delete", VaadinIcon.TRASH.create(), click -> {
            Set<FormData> selectedRows = customerGrid.asMultiSelect().getSelectedItems(); // Get selected rows

            if (selectedRows.isEmpty()) {
                Notification.show("Please select a row to delete", 3000, Notification.Position.MIDDLE);
            } else {
                customerDataList.removeAll(selectedRows); // Remove from list
                customerGrid.getDataProvider().refreshAll(); // Refresh grid
                Notification.show(selectedRows.size() + " rows deleted", 3000, Notification.Position.MIDDLE);
            }
        });
        // Submit button
        Button submitButton = new Button("Submit", VaadinIcon.CHECK.create(), click -> {
            for (FormData row : customerDataList) {
                Notification.show("Customer added: " + row.getFarmerName() + " (" + row.getNoOfBags() + " bags)");
            }
        });

     // Add buttons and grid to layout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(addRowButton, deleteRowButton,  submitButton);
        VerticalLayout layout = new VerticalLayout(customerGrid,horizontalLayout);
        // Add the grid to the center area
        centerArea.add(layout);

        
       

        // Add the left area and center area to the main layout
        mainLayout.add(leftArea, centerArea);
        mainLayout.setWidth("100%");

        // Add the main layout to the optionsLayout
        optionsLayout.add(mainLayout);
    }


    private void showCustomerList(VerticalLayout optionsLayout) {
        // Remove existing components before adding new ones
        optionsLayout.removeAll();

        // Create a layout with Flexbox for the black left area and centered customer list
        HorizontalLayout mainLayout = new HorizontalLayout();

        // Left area for the "View Customers" button
        VerticalLayout leftArea = new VerticalLayout();
        leftArea.getStyle().set("background-color", "#000000")  // Black background for left side
                        .set("color", "#FFFFFF")
                        .set("padding", "20px")
                        .set("width", "200px");  // Fixed width for the black area
        Button addCustomerButton = new Button("Add Customer", VaadinIcon.PLUS_CIRCLE.create());
        addCustomerButton.getStyle().set("background-color", "#32CD32")  // Green button
                            .set("color", "#FFFFFF")
                            .set("border-radius", "5px")
                            .set("font-weight", "bold");
        leftArea.add(addCustomerButton);
        Button viewCustomerButton = new Button("View Customers", VaadinIcon.EYE.create());
        viewCustomerButton.getStyle().set("background-color", "#FFD700")  // Yellow button for view
                           .set("color", "#FFFFFF")
                           .set("border-radius", "5px")
                           .set("font-weight", "bold");
        leftArea.add(viewCustomerButton);
        addCustomerButton.addClickListener(addEvent -> showAddCustomerForm(optionsLayout));
        viewCustomerButton.addClickListener(viewEvent -> showCustomerList(optionsLayout));
        // Customer list in the white background area
        VerticalLayout centerArea = new VerticalLayout();
        centerArea.getStyle().set("background-color", "#FFFFFF")  // White background for list area
                             .set("padding", "20px")
                             .set("width", "100%");

        // Here you would display a list of customers (e.g., using a grid or a list component)
        // For now, let's simulate with a simple notification
        Notification.show("Displaying list of customers...");

        // Add the left area and center area to the main layout
        mainLayout.add(leftArea, centerArea);
        mainLayout.setWidth("100%");

        // Add the main layout to the optionsLayout
        optionsLayout.add(mainLayout);
    }
}
