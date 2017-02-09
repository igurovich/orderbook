package com.igorgurovich.controller;

import com.igorgurovich.domain.OrderRequest;
import com.igorgurovich.domain.OrderResponse;
import com.igorgurovich.exception.OrderBookNotFoundException;
import com.igorgurovich.exception.ProductNotFoundException;
import com.igorgurovich.exception.UnableFillOrderException;
import com.igorgurovich.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by igorgurovich on 2/8/17.
 */
@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping( value = "/order", method = RequestMethod.POST )
    public OrderResponse order(@RequestBody OrderRequest order){
        return orderService.placeMarketOrder(order);
    }


    @ExceptionHandler(OrderBookNotFoundException.class)
    public void handlePostNotFound(OrderBookNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(), exception.getMessage() );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public void handlePostNotFound(ProductNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(), exception.getMessage() );
    }

    @ExceptionHandler(UnableFillOrderException.class)
    public void handlePostNotFound(UnableFillOrderException exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(), exception.getMessage() );
    }


}
