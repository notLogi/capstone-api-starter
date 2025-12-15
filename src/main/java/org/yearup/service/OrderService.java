package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.data.OrderLineItemDao;

@Component
public class OrderService{
    private OrderDao orderDao;
    private OrderLineItemDao orderLineItemDao;

    @Autowired
    public OrderService(OrderDao orderDao, OrderLineItemDao orderLineItemDao){
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
    }
}
