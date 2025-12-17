package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.OrderLineItemDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class OrderService{
    private OrderDao orderDao;
    private OrderLineItemDao orderLineItemDao;
    private ShoppingCartDao shoppingCartDao;
    private ProfileDao profileDao;

    @Autowired
    public OrderService(OrderDao orderDao, OrderLineItemDao orderLineItemDao, ShoppingCartDao shoppingCartDao, ProfileDao profileDao){
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
        this.shoppingCartDao = shoppingCartDao;
        this.profileDao = profileDao;
    }

    /**
     * Retrieves all the items in the shopping cart, and put them in the order_line_item table,
     * and tracking the time of the order and profile details from the database.
     * @param userId to grab the order according to the user's ID
     * @return the order to confirm it's been stored in the database.
     * @throws RuntimeException if a database access error occurs
     */
    public Order checkout(int userId){
        Order order = new Order();
        makeOrder(userId, order);
        orderDao.create(order);
        ShoppingCart cart = shoppingCartDao.getByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
        Map<Integer, ShoppingCartItem> itemList = cart.getItems();
        for(Map.Entry<Integer, ShoppingCartItem> i : itemList.entrySet()){
            OrderLineItem orderLineItem = new OrderLineItem();
            int productId = i.getKey();
            Product product = i.getValue().getProduct();
            orderLineItem.setOrderId(order.getOrderId());
            orderLineItem.setProductId(productId);
            orderLineItem.setSalesPrice(product.getPrice());
            orderLineItem.setQuantity(i.getValue().getQuantity());
            orderLineItem.setDiscount(i.getValue().getDiscountPercent());
            orderLineItemDao.create(orderLineItem);
        }
        shoppingCartDao.deleteCart(userId);
        return order;
    }

    private void makeOrder(int userId, Order order){
        Profile profile = profileDao.getByUserId(userId);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found");
        }
        LocalDateTime dateTime = LocalDateTime.now();
        String address = profile.getAddress();
        String state = profile.getState();
        String city = profile.getCity();
        String zip = profile.getZip();
        order.setUserId(userId);
        order.setDate(dateTime);
        order.setAddress(address);
        order.setState(state);
        order.setCity(city);
        order.setZip(zip);
        order.setShippingAmount(new BigDecimal("0"));
    }
}
