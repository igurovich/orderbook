package com.igorgurovich.service;

import com.igorgurovich.domain.OrderBook;
import com.igorgurovich.domain.OrderRequest;
import com.igorgurovich.domain.OrderResponse;
import com.igorgurovich.domain.Product;
import com.igorgurovich.exception.OrderBookNotFoundException;
import com.igorgurovich.exception.ProductNotFoundException;
import com.igorgurovich.exception.UnableFillOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by igorgurovich on 2/8/17.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    RestTemplate restTemplate = new RestTemplate();

    public Product getProduct(String id) {

        Product product = null;

        String productUrl = String.format(GDAX_PRODUCT_ENDPOINT, id);
        try {
            product = restTemplate.getForObject(productUrl, Product.class);
        } catch (Exception ex) {
            // try reverse base and quote currencies
            try {
                String[] currencies =  id.split("-");
                if (currencies.length == 2) {
                    productUrl = String.format(GDAX_PRODUCT_ENDPOINT, currencies[1] + "-" + currencies[0]);
                    product = restTemplate.getForObject(productUrl, Product.class);
                }
            } catch (Exception ex1) {
                logger.info(ex1.toString());
            }

        }


        return product;
    }

    @Override
    public OrderBook getOrderBook(String id) {

        return null;
    }

    @Override
    public OrderBook getOrderBook(Product product) {

        OrderBook orderBook  = null;
        String productId = product.getId();
        try {
            String orderBookURL = String.format(GDAX_ORDERBOOK_ENDPOINT, productId);
            orderBook = restTemplate.getForObject(orderBookURL, OrderBook.class);
        } catch (Exception ex) {
            logger.info(ex.toString());
        }
        return orderBook;
    }

    @Override
    public OrderResponse placeMarketOrder(String action, String baseCurrency, String quoteCurrency, String amount) {

        String productId = baseCurrency + "-" + quoteCurrency;
        Product product = getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product " + productId + " not found.");
        }

        // TODO validate order using base_min_size, base_max_size

        OrderBook orderBook = getOrderBook(product);
        if (orderBook == null) {
            throw new OrderBookNotFoundException("Order book not found for product " + product.getId() + ".");
        }

        OrderResponse response = placeMarketOrder(product, orderBook, action,  baseCurrency,  quoteCurrency,  amount);



        return response;
    }

    @Override
    public OrderResponse placeMarketOrder(OrderRequest order) {
        return placeMarketOrder(order.getAction(), order.getBaseCurrency(), order.getQuoteCurrency(), order.getAmount());
    }

    public OrderResponse placeMarketOrder(Product product,
                                           OrderBook orderBook,
                                           String action,
                                           String baseCurrency,
                                           String quoteCurrency,
                                           String amount) {
        // Cases to consider
        //  product and orderBook for  baseCur-quoteCur exists
        //  1)  buy order:
        //      Traverse  orderBook.asks list (which is sorted from lower to higher price) and try to buy "amount" of base currency using quote curr.

        //  2)  sell order:
        //      Traverse  orderBook.bids list (which is sorted from higher to lower price) and try to sell "amount" of base currency.

        //  product and orderBook for  REVERSED baseCur, quoteCur exists (quoteCur-baseCur)

        //   3) Traverse orderBook.bids list in reverse order (from lower to higher price) and try to buy "amount" of quote currency
        //   4) Traverse orderBook.asks list in reverse order (from higher to lower price) and try to sell "amount" of quote currency

        float baseCurrencyGoal = Float.parseFloat(amount);
        float baseCurrencyExchangedTotal = 0;
        float quoteCurrencyExchangedTotal = 0;

        if ((baseCurrency+"-" + quoteCurrency).equals(product.getId())) {
            List quotes = null;

            if (action.equals("buy")) {
                quotes = orderBook.getAsks();
            } else if (action.equals("sell")) {
                quotes = orderBook.getBids();
            }

            // Goal is to buy "amount of base currency using quote currency
            for (Object entry: quotes) {
                List record = (List) entry;
                float price =  Float.parseFloat((String) record.get(0));
                float size =  Float.parseFloat((String) record.get(1));
                int numOrders = (int) record.get(2);
                // buy some amount of base currency
                float baseCurrencyExchanged = Math.min(baseCurrencyGoal - baseCurrencyExchangedTotal, size);
                float quoteCurrencyExchanged = baseCurrencyExchanged * price;
                baseCurrencyExchangedTotal += baseCurrencyExchanged;
                quoteCurrencyExchangedTotal += quoteCurrencyExchanged;
                if (baseCurrencyExchangedTotal >= baseCurrencyGoal)
                    break;
            }

        } else if ((quoteCurrency + "-" + baseCurrency).equals(product.getId()))  {
            // product data are for quoteCurrency + "-" + baseCurrency
            List quotes = null;
            if (action.equals("buy")) {
                quotes = orderBook.getBids();
            } else if (action.equals("sell")) {
                // Goal is to sell "amount of base currency and receive quote currency
                quotes = orderBook.getAsks();
            }

            // reverse quotes
            Collections.reverse(quotes);

            // Goal is to buy "amount of base currency using quote currency
            for (Object entry: quotes) {
                List record = (List) entry;
                float price =  Float.parseFloat((String) record.get(0));
                float size =  Float.parseFloat((String) record.get(1));
                int numOrders = (int) record.get(2);
                float needBaseCurrency = baseCurrencyGoal - baseCurrencyExchangedTotal;
                // buy some amount of base currency
                float baseCurrencyExchanged = Math.min(needBaseCurrency, size * price );
                float quoteCurrencyExchanged = baseCurrencyExchanged / price; //!!!
                baseCurrencyExchangedTotal += baseCurrencyExchanged;
                quoteCurrencyExchangedTotal += quoteCurrencyExchanged;
                if (baseCurrencyExchangedTotal >= baseCurrencyGoal)
                    break;
            }
        }

        if (baseCurrencyExchangedTotal == baseCurrencyGoal) {
            float averagePrice = quoteCurrencyExchangedTotal/ baseCurrencyExchangedTotal ;  ///// !!!
            String priceString = String.format("%f", averagePrice);
            String quoteCurrencyTotalString = String.format("%f", quoteCurrencyExchangedTotal);
            return new OrderResponse(quoteCurrencyTotalString, priceString, quoteCurrency);
        } else {
            throw new UnableFillOrderException("unable to fill order.");
        }
    }
}
