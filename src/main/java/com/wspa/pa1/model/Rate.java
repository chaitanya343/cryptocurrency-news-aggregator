package com.wspa.pa1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    private String id;
    private String symbol;
    private String currencySymbol;
    private String type;
    private String rateUsd;

    public Rate() {
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getType() {
        return type;
    }

    public String getRateUsd() {
        return rateUsd;
    }


}
