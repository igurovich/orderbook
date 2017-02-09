package com.igorgurovich.exception;

/**
 * Created by igorgurovich on 2/8/17.
 */
public class ProductNotFoundException  extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
