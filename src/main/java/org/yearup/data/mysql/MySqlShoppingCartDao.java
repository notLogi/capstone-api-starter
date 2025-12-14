package org.yearup.data.mysql;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private DataSource dataSource;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource){
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public ShoppingCart getByUserId(int userId){
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";
        ShoppingCart cart = new ShoppingCart();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, userId);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    ProductDao productDAO = new MySqlProductDao(dataSource);
                    if(resultSet.next()){
                        do{
                            int productId = resultSet.getInt("product_id");
                            int quantity = resultSet.getInt("quantity");
                            ShoppingCartItem item = new ShoppingCartItem();
                            item.setProduct(productDAO.getById(productId));
                            System.out.println(item.getProduct());
                            item.setQuantity(quantity);
                            cart.add(item);
                        }while(resultSet.next());
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cart;
    }
}
