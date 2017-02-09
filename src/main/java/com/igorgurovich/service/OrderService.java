package com.igorgurovich.service;

import com.igorgurovich.domain.OrderBook;
import com.igorgurovich.domain.OrderRequest;
import com.igorgurovich.domain.OrderResponse;
import com.igorgurovich.domain.Product;

/**
 * Created by igorgurovich on 2/8/17.
 */
public interface OrderService {
    public final static String GDAX_PRODUCT_ENDPOINT = "https://api.gdax.com/products/%s";
    public final static String GDAX_ORDERBOOK_ENDPOINT = "https://api.gdax.com/products/%s/book?level=2";

    Product getProduct(String id);
    OrderBook getOrderBook(String id);
    OrderBook getOrderBook(Product product);

    OrderResponse placeMarketOrder(String action, String baseCurrency, String quoteCurrency, String amount);

    public OrderResponse placeMarketOrder(Product product,
                                          OrderBook orderBook,
                                          String action,
                                          String baseCurrency,
                                          String quoteCurrency,
                                          String amount);

    OrderResponse placeMarketOrder(OrderRequest order);
}
