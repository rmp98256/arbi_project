package com.jaiBalramTraders.Button;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MultiRowInputForm extends VerticalLayout {

    private Grid<FormData> grid;
    private List<FormData> rowDataList = new ArrayList<>();
    private Editor<FormData> editor;
    private  DatePicker datePicker;

    public MultiRowInputForm() {
        grid = new Grid<>();
        setupGrid();
        // Create buttons with enhanced styling and tooltips
        Button addRowButton = new Button("Add Row", VaadinIcon.PLUS.create(), click -> addRow());
        addRowButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addRowButton.getElement().setAttribute("title", "Click to add a new row");

        Button deleteRowButton = new Button("Delete", VaadinIcon.TRASH.create(), click -> deleteSelectedRows());
        deleteRowButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteRowButton.getElement().setAttribute("title", "Click to delete selected rows");

        Button submitButton = new Button("Submit", VaadinIcon.CHECK.create(), click -> submitData());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.getElement().setAttribute("title", "Click to submit data");
        
        datePicker = new DatePicker();
        
        datePicker.setValue(java.time.LocalDate.now());  // Default value as today
        datePicker.getStyle().set("background-color", "#FFFFFF").set("border-radius", "10px") ; // White background for the DatePicker
                             /*.set("color", "#000000")  // Black text
                             .set("border-radius", "10px")  // Rounded corners
                             .set("padding", "0px")  // Padding for comfort
                             .set("width", "250px"); 

*/
        // Horizontal layout to arrange buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(addRowButton, deleteRowButton, submitButton,datePicker);
        buttonLayout.setSpacing(true);  // Add spacing between buttons
        buttonLayout.addClassName("button-layout"); // You can define this class in CSS for additional styling

        add(buttonLayout, grid);
    }

    private void setupGrid() {
        grid.setItems(rowDataList);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        editor = grid.getEditor();
        editor.setBuffered(false);

        Binder<FormData> binder = new Binder<>(FormData.class);
        editor.setBinder(binder);

        // Editable Fields
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

       

        // Add columns
        grid.addColumn(FormData::getFarmerName).setHeader("Farmer Name").setEditorComponent(farmerNameField);
        grid.addColumn(FormData::getNoOfBags).setHeader("No of Bags").setEditorComponent(noOfBagsField);
        grid.addColumn(FormData::getBagWeight).setHeader("Bag Weight").setEditorComponent(bagWeightField);
        grid.addColumn(FormData::getRatePerBag).setHeader("Rate per Bag").setEditorComponent(ratePerBagField);
        grid.addColumn(FormData::getStatus).setHeader("Status").setEditorComponent(statusComboBox);
        

        // Enable inline editing on double-click
        grid.addItemDoubleClickListener(event -> editor.editItem(event.getItem()));
    }

    private void addRow() {
        FormData newRow = new FormData("", 0, 0.0, 0.0, "Incomplete", LocalDate.now());
        rowDataList.add(newRow);
        grid.getDataProvider().refreshAll();
        editor.editItem(newRow);
    }

    private void deleteSelectedRows() {
        Set<FormData> selectedRows = grid.asMultiSelect().getSelectedItems();
        if (selectedRows.isEmpty()) {
            Notification.show("Please select a row to delete", 3000, Notification.Position.MIDDLE);
        } else {
            rowDataList.removeAll(selectedRows);
            grid.getDataProvider().refreshAll();
            Notification.show(selectedRows.size() + " rows deleted", 3000, Notification.Position.MIDDLE);
        }
    }

    private void submitData() {
        for (FormData row : rowDataList) {
        	row.setDate(null);
            System.out.println("Row Data: " + row.getFarmerName() + ", " + row.getNoOfBags() + ", " + row.getBagWeight() + ", " + row.getRatePerBag() + ", " + row.getStatus() + ", " + row.getDate());
        }
        Notification.show("Data submitted!", 3000, Notification.Position.MIDDLE);
    }
}
