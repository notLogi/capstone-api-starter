package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest extends BaseDaoTestClass {
    private CategoryDao categoryDao;

    @BeforeEach
    public void setup()
    {
        categoryDao = new MySqlCategoryDao(dataSource);
    }

    @Test
    public void getById_shouldReturnCorrectCategory() {
        // arrange
        int categoryId = 1;

        Category expected = new Category();
        expected.setCategoryId(1);
        expected.setName("Console Games");
        expected.setDescription("Latest games for PlayStation, Xbox, and Nintendo platforms.");

        // act
        Category actual = categoryDao.getById(categoryId);

        // assert
        assertNotNull(actual, "Category should not be null");
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }


}