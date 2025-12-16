package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.UserDao;
import org.yearup.models.Order;
import org.yearup.models.User;
import org.yearup.service.OrderService;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("orders")
public class OrdersController {
    private OrderService orderService;
    private UserDao userDao;

    @Autowired
    public OrdersController(OrderService orderService, UserDao userDao){
        this.orderService = orderService;
        this.userDao = userDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public Order checkout(Principal principal){
        User user = userDao.getByUserName(principal.getName());
        int userId = user.getId();
        return orderService.checkout(userId);

    }
}
