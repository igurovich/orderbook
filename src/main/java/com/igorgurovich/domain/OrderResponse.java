package com.igorgurovich.domain;


/**
 * Created by igorgurovich on 2/8/17.
 */

public class OrderResponse {

    //Total quantity of currency currency
    private String total;

    //The per-unit cost of the base currency
    private String price;

    // The quote currency
    private String currency;

    public OrderResponse(String total, String price, String currency) {
        this.total = total;
        this.price = price;
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public String getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }
}
