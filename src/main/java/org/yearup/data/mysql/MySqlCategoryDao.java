package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM categories");
            ResultSet resultSet = preparedStatement.executeQuery()){
            if(resultSet.next()){
                do{
                    categoryList.add(mapRow(resultSet));
                }
                while(resultSet.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM categories WHERE category_id = ?")){
            preparedStatement.setInt(1, categoryId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapRow(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO categories (name, description) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating category failed, no rows affected.");
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    int generatedId = generatedKeys.getInt(1);
                    category.setCategoryId(generatedId);
                }
                else{
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE categories SET name = ?, description = ? WHERE category_id = ?")){
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, categoryId);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) throw new SQLException("Updating category failed, no rows affected.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM categories WHERE category_id = ?")){
            preparedStatement.setInt(1, categoryId);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) throw new SQLException("Deleting category failed, no rows affected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
