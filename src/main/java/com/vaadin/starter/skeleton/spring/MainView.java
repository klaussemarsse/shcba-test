package com.vaadin.starter.skeleton.spring;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private final VerticalLayout showDecimalFormat;

    public MainView(@Autowired MessageBean bean) {


        showDecimalFormat = new VerticalLayout();

        MaskedNumberField maskedTextField = new MaskedNumberField("Masked Field");
        MaskedNumberField readonly = new MaskedNumberField("Readonly value");
//        readonly.setReadOnly(true);

//        maskedTextField.setSigned(false);
        Button showMaskedValue = new Button("Get Value", event -> {
            Notification.show(maskedTextField.getValue().toString());
            System.out.println(maskedTextField.getValue());
            readonly.setValue(maskedTextField.getValue());
        });

        final Button switchLocale = new Button("Change Locale", event -> {
            if(UI.getCurrent().getLocale() == Locale.ENGLISH){
                UI.getCurrent().setLocale(Locale.FRENCH);
            }else{
                UI.getCurrent().setLocale(Locale.ENGLISH);
            }
            showDecimalFormat(UI.getCurrent().getLocale());
            event.getSource().setText(UI.getCurrent().getLocale().toString());
        });
        add(maskedTextField,showMaskedValue, readonly, switchLocale,showDecimalFormat);
        showDecimalFormat(UI.getCurrent().getLocale());
    }


    private void showDecimalFormat(Locale locale){
        showDecimalFormat.removeAll();
        showDecimalFormat.add(new Label("locale: " + locale));

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();

        showDecimalFormat.add(new Label("getGroupingSeparator: " + decimalFormatSymbols.getGroupingSeparator()));
        showDecimalFormat.add(new Label("getDecimalSeparator: " + decimalFormatSymbols.getDecimalSeparator()));
        showDecimalFormat.add(new Label("getMonetaryDecimalSeparator: " + decimalFormatSymbols.getMonetaryDecimalSeparator()));
        showDecimalFormat.add(new Label("getCurrencySymbol: " + decimalFormatSymbols.getCurrencySymbol()));



    }

}
