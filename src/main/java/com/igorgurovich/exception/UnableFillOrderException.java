package com.igorgurovich.exception;

/**
 * Created by igorgurovich on 2/8/17.
 */
public class UnableFillOrderException  extends RuntimeException{
    public UnableFillOrderException(String message) {
        super(message);
    }
}
