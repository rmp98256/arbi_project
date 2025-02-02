package com.jaiBalramTraders.Button;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class BuyerDataButton extends Button {
    public BuyerDataButton(VerticalLayout optionsLayout) {
        super("Buyers", VaadinIcon.CART.create());
        setTooltipText("Manage buyer data");

        addClickListener(event -> {
            // Clear the previous options
            optionsLayout.removeAll();

            Button getOrderByDate = new Button("By Date", VaadinIcon.CALENDAR.create());
            Button getOrderByBuyerID = new Button("By Buyer ID", VaadinIcon.CREDIT_CARD.create());
            Button addMultipleOrders = new Button("Add Multiple", VaadinIcon.PLUS.create());
            Button addSingleOrder = new Button("Add Single", VaadinIcon.PLUS_CIRCLE.create());

            optionsLayout.add(getOrderByDate, getOrderByBuyerID, addMultipleOrders, addSingleOrder);
        });
    }
}
