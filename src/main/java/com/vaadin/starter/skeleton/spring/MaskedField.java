package com.vaadin.starter.skeleton.spring;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * A specialized Vaadin {@link TextField} with the mask feature present.
 *
 */
@NpmPackage(value = "imask", version = "5.2.1")
@JsModule(value = "./imask/imaskConnector.js")
@JsModule(value = "imask/dist/imask.js")
public class MaskedField extends TextField{

    public MaskedField() {

    }

    public MaskedField(String label){
        super(label);
        addValueChangeListener(event -> {
            if(!event.isFromClient()){
                callJsFunction("resolve");
            }
        });
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initConnector();

    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        callJsFunction("update");
    }

    private void initConnector() {
        runBeforeClientResponse(ui -> ui.getPage().executeJs(
                "window.Vaadin.Flow.mask.initLazy($0)", getElement()));
    }


    void runBeforeClientResponse(SerializableConsumer<UI> command) {
        getElement().getNode().runWhenAttached(ui -> ui
                .beforeClientResponse(this, context -> command.accept(ui)));
    }

    /**
     * Defines if mask should allow negative sign or not.
     *
     * @param signed
     *          <code>true</code> allows negative numbers
     */
    public  void setSigned(boolean signed){
        setOption("setSigned", signed);
    }

    /**
     * Defines the digits after decimal separator, 0 for integers only
     *
     * @param scale
     *          number of digits after decimal separator, 0 for integers only
     */
    public  void setScale(int scale){
        setOption("setScale", scale);
    }

    /**
     * Sets the thousands separator single character.
     *
     * @param thousandsSeparator
     *      a single character to be used for thousands separator of numbers
     */
    public  void setThousandsSeparator(char thousandsSeparator){
        setOption("setThousandsSeparator", String.valueOf(thousandsSeparator));
    }

    /**
     * Sets the character that should be processed as radix (decimal separator)
     *
     * @param mapToRadix
     *      a single character to be processed as radix
     */
    public  void setMapToRadix(char mapToRadix){
        setOption("setMapToRadix", String.valueOf(mapToRadix));
    }

    /**
     * Sets the character to be used as decimal separator.
     *
     * @param radix
     *      the decimal separator character
     */
    public  void setRadix(char radix){
        setOption("setRadix", String.valueOf(radix));
    }

    /**
     * Defines if to append or remove zeros at the ends.
     *
     * @param normalizeZeros
     *
     */
    public  void setNormalizeZeros(boolean normalizeZeros){
        setOption("setNormalizeZeros", normalizeZeros);
    }

    /**
     * Defines if to pad the value with zeros for the decimal positions.
     *
     * @param padFractionalZeros
     *          if true, then pads zeros at end to the length of scale
     */
    public  void setPadFractionalZeros(boolean padFractionalZeros){
        setOption("setPadFractionalZeros", padFractionalZeros);
    }

    /**
     * Sets number mask.
     */
    public void setNumberMask(){
        callJsFunction("setNumberMask");
    }

    /**
     * Sets string mask.
     */
    public void setStringMask(){
        callJsFunction("setStringMask");
    }

    /**
     * Sets a mask pattern.
     * @param mask
     */
    public void setMask(String mask){
        Objects.requireNonNull(mask);
        setOption("setMask", mask);
    }


    private void setOption(String optionMethod, Serializable option){
        Objects.requireNonNull(optionMethod);
        Objects.requireNonNull(option);
        runBeforeClientResponse(ui -> {
            getElement().callJsFunction("$connector." + optionMethod, option);
        });
    }

    private void callJsFunction(String jsFunction){
        Objects.requireNonNull(jsFunction);
        runBeforeClientResponse(ui -> {
            getElement().callJsFunction("$connector." + jsFunction);
        });
    }

    public void resolve(String value, SerializableConsumer<String> resultConsumer){
        PendingJavaScriptResult pendingJavaScriptResult = getElement().callJsFunction("$connector.resolve", value);
        pendingJavaScriptResult.then(result -> resultConsumer.accept(result.asString()));
    }
}
