package com.wspa.pa1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinData {

    private String timestamp;

    @JsonProperty("data")
    private List<Coin> data;

    public CoinData() {
    }

    public List<Coin> getCoins() {
        return data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "CoinData{" +
                "timestamp='" + timestamp + '\'' +
                ", data=" + data +
                '}';
    }
}
