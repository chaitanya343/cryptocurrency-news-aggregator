package com.wspa.pa1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesData {

    @JsonProperty("data")
    private List<Rate> data;

    public RatesData() {
    }

    public List<Rate> getData() {
        return data;
    }
}
