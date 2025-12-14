package org.yearup.data.mysql;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private DataSource dataSource;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId){
        String sql = """
                SELECT * FROM shopping_cart
                JOIN products on products.product_id = shopping_cart.product_id
                WHERE user_id = ?""";
        ShoppingCart cart = new ShoppingCart();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, userId);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if(resultSet.next()){
                        do{
                            int productId = resultSet.getInt("product_id");
                            int quantity = resultSet.getInt("quantity");
                            String name = resultSet.getString("name");
                            BigDecimal price = resultSet.getBigDecimal("price");
                            int categoryId = resultSet.getInt("category_id");
                            String description = resultSet.getString("description");
                            String subCategory = resultSet.getString("subcategory");
                            String imageUrl = resultSet.getString("image_url");
                            int stock = resultSet.getInt("stock");
                            boolean isFeatured = resultSet.getBoolean("featured");
                            ShoppingCartItem item = new ShoppingCartItem();
                            item.setProduct(new Product(productId, name, price, categoryId, description, subCategory, stock, isFeatured, imageUrl));
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
