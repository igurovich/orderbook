package com.igorgurovich.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by igorgurovich on 2/7/17.
 */
public class Product {
    private String id;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("base_min_size")
    private String baseMinSize;

    @JsonProperty("base_max_size")
    private String baseMaxSize;

    @JsonProperty("quote_increment")
    private String quoteIncremet;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getBaseMinSize() {
        return baseMinSize;
    }

    public void setBaseMinSize(String baseMinSize) {
        this.baseMinSize = baseMinSize;
    }

    public String getBaseMaxSize() {
        return baseMaxSize;
    }

    public void setBaseMaxSize(String baseMaxSize) {
        this.baseMaxSize = baseMaxSize;
    }

    public String getQuoteIncremet() {
        return quoteIncremet;
    }

    public void setQuoteIncremet(String quoteIncremet) {
        this.quoteIncremet = quoteIncremet;
    }

    @Override
    public String toString() { return "Product[id=" + id + "]";}


    }
