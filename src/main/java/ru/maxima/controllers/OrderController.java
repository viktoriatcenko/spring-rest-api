package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.maxima.exceptions.OrderNotCreatedException;
import ru.maxima.exceptions.OrderNotFoundException;
import ru.maxima.exceptions.PersonNotCreatedException;
import ru.maxima.exceptions.PersonNotFoundException;
import ru.maxima.models.Order;
import ru.maxima.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Order> getOrders() {
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Optional<Order> orderById = service.getOrderById(id);
        return orderById.orElseThrow(OrderNotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createOrder(@RequestBody @Valid Order order, BindingResult result) {
        checkErrors(result);

        service.save(order);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    static void checkErrors(BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach( error -> {
                builder.append(error.getField());
                builder.append(" - ");
                builder.append(error.getDefaultMessage());
            });
            throw new OrderNotCreatedException(builder.toString());
        }
    }


    @ExceptionHandler({ OrderNotFoundException.class })
    public ResponseEntity<Object> handleOrderNotFoundException() {
        return new ResponseEntity<>(
                "Order is not found",  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ OrderNotCreatedException.class })
    public ResponseEntity<Object> handleOrderNotCreatedException(OrderNotCreatedException ex) {
        return new ResponseEntity<>(
                ex.getMessage(),  HttpStatus.CONFLICT);
    }





}
