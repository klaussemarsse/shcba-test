import IMask from 'imask';

window.Vaadin.Flow.mask = {

    initLazy: function (maskedTextField) {
        if (maskedTextField.$connector) {
            return;
        }

        maskedTextField.$connector = {};

        var maskOptions = {
            mask: Number,  // enable number mask
            // other options are optional with defaults below
            scale: 2,  // digits after point, 0 for integers
            signed: false,  // disallow negative
            thousandsSeparator: ' ',  // any single char
            padFractionalZeros: false,  // if true, then pads zeros at end to the length of scale
            normalizeZeros: true,  // appends or removes zeros at ends
            radix: '.',  // fractional delimiter
            mapToRadix: ['.']  // symbols to process as radix

        };


        var mask = new IMask(maskedTextField, {mask: String});

        maskedTextField.$connector.mask = mask;


        maskedTextField.$connector.setNormalizeZeros = function(normalizeZeros) {
            mask.updateOptions({normalizeZeros: normalizeZeros});
        }

        maskedTextField.$connector.setScale = function(scale) {
            mask.updateOptions({scale: scale});
        }

        maskedTextField.$connector.setSigned = function(signed) {
            mask.updateOptions({signed: signed});
        }

        maskedTextField.$connector.setPadFractionalZeros = function(padFractionalZeros) {
            mask.updateOptions({padFractionalZeros: padFractionalZeros});
        }

        maskedTextField.$connector.setMask = function(mask) {
            this.mask.updateOptions({mask: mask});
        }

        maskedTextField.$connector.setThousandsSeparator = function(thousandsSeparator) {
            mask.updateOptions({thousandsSeparator: thousandsSeparator});
        }

        maskedTextField.$connector.setMapToRadix = function(mapToRadix) {
            mask.updateOptions({mapToRadix: [mapToRadix]});
        }

        maskedTextField.$connector.setRadix = function(radix) {
            mask.updateOptions({radix: radix});
        }

        maskedTextField.$connector.setNumberMask = function() {
            this.mask.updateOptions({mask: Number});
        }

        maskedTextField.$connector.setStringMask = function() {
            this.mask.updateOptions({mask: String});
        }


        maskedTextField.$connector.update = function() {
            maskedTextField.value = this.mask.masked.resolve(maskedTextField.value);
        }

    }
}
