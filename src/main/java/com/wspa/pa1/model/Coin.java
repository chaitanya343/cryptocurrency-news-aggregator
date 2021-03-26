package com.wspa.pa1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coin {

    private String id;
    private String rank;
    private String symbol;
    private String name;
    private String supply;
    private String maxSupply;
    private String marketCapUsd;
    private String volumeUsd24Hr;
    private String priceUsd;
    private String changePercent24Hr;
    private String vwap24Hr;
    private String explorer;
    private int convertedPrice;

    public Coin() {
    }

    public String getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getSupply() {
        return supply;
    }

    public String getMaxSupply() {
        return maxSupply;
    }

    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    public String getVolumeUsd24Hr() {
        return volumeUsd24Hr;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getChangePercent24Hr() {
        return changePercent24Hr;
    }

    public String getVwap24Hr() {
        return vwap24Hr;
    }

    public String getExplorer() {
        return explorer;
    }

    public int getConvertedPrice() {
        return convertedPrice;
    }

    public void setConvertedPrice(int convertedPrice) {
        this.convertedPrice = convertedPrice;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id='" + id + '\'' +
                ", rank='" + rank + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", supply='" + supply + '\'' +
                ", maxSupply='" + maxSupply + '\'' +
                ", marketCapUsd='" + marketCapUsd + '\'' +
                ", volumeUsd24Hr='" + volumeUsd24Hr + '\'' +
                ", priceUsd='" + priceUsd + '\'' +
                ", changePercent24Hr='" + changePercent24Hr + '\'' +
                ", vwap24Hr='" + vwap24Hr + '\'' +
                ", explorer='" + explorer + '\'' +
                '}';
    }

    public String representation(String currCode) {
        return String.format("Rank: %s | Symbol: %s | Name: %s | Price in %s: %d", rank, symbol, name, currCode, convertedPrice);
    }
}
