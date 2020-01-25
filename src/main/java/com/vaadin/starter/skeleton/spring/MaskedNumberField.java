package com.vaadin.starter.skeleton.spring;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * A specialized {@link MaskedField} to handle localized {@link BigDecimal} only values.
 */
@Uses(MaskedField.class)
public class MaskedNumberField extends AbstractCompositeField<MaskedField, MaskedNumberField, BigDecimal> implements LocaleChangeObserver {


    private boolean suspendValueChangeListener;
    private DecimalFormat decimalFormat;


    public MaskedNumberField() {
        super(null);
        setDecimalFormat(getLocale());

        getContent().addValueChangeListener(event -> {
            suspendValueChangeListener = true;
            try {
                Number parsedValue = decimalFormat.parse(event.getValue());
                setValue(BigDecimal.valueOf(parsedValue.doubleValue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            suspendValueChangeListener = false;

        });

        getContent().setNumberMask();
        getContent().setSigned(true);
        getContent().setPadFractionalZeros(false);
        getContent().setNormalizeZeros(false);

        getContent().addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
    }


    public void setScale(int scale){
        getContent().setScale(scale);
    }

    public MaskedNumberField(String label) {
        this();
        getContent().setLabel(label);
    }

    public void setSigned(boolean signed){
        getContent().setSigned(signed);
    }
    @Override
    protected void setPresentationValue(BigDecimal newPresentationValue) {
        if(!suspendValueChangeListener){
            getContent().setValue(newPresentationValue != null ? newPresentationValue.toString(): "");
        }
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setDecimalFormat(event.getLocale());
    }

    private void setDecimalFormat(Locale locale){
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
        getContent().setThousandsSeparator(decimalFormatSymbols.getGroupingSeparator());
        getContent().setMapToRadix(decimalFormatSymbols.getDecimalSeparator());
        getContent().setRadix(decimalFormatSymbols.getDecimalSeparator());
    }
}
