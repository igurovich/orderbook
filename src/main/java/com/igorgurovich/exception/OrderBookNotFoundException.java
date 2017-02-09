package com.igorgurovich.exception;

/**
 * Created by igorgurovich on 2/8/17.
 */
public class OrderBookNotFoundException extends RuntimeException  {
    public OrderBookNotFoundException(String message) {
        super(message);
    }
}
