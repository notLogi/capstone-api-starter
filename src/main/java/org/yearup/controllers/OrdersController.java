package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.User;
import org.yearup.service.OrderService;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("orders")
public class OrdersController {
    private ShoppingCartDao shoppingCartDao;
    private OrderService orderService;
    private UserDao userDao;

    @Autowired
    public OrdersController(ShoppingCartDao shoppingCartDao, OrderService orderService, UserDao userDao){
        this.shoppingCartDao = shoppingCartDao;
        this.orderService = orderService;
        this.userDao = userDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public void addOrder(Principal principal){
        try{
            User user = userDao.getByUserName(principal.getName());
            int
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
