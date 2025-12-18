package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    public CategoriesController(ProductDao productDAO, CategoryDao categoryDAO){
        productDao = productDAO;
        categoryDao = categoryDAO;
    }

    /**
     * Retrieves all categories.
     * @return a list of all categories
     * @throws ResponseStatusException if no categories are found or an internal error occurs
     */
    @GetMapping
    public List<Category> getAll()
    {
        // find and return all categories
        try {
            List<Category> categoryList = categoryDao.getAllCategories();
            if(categoryList == null || categoryList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories found");
            return categoryDao.getAllCategories();
        } catch (ResponseStatusException e){
            throw e;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    /**
     * Retrieves a single category by its ID.
     * @param id from the URL
     * @return the Category gotten by request
     * @throws ResponseStatusException if the category does not exist or an internal error occurs
     */
    @GetMapping("{id}")
    public Category getById(@PathVariable int id)
    {
        try {
            var category = categoryDao.getById(id);
            if (category == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
            return category;
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    /**
     * Retrieves all products belonging to a specific category.
     * @param categoryId from the URL
     * @return a list of products in the given category
     * @throws ResponseStatusException if an internal error occurs
     */
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        try {
            return productDao.search(categoryId, null, null, null);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    /**
     * Adds a category to the database.
     * Access restricted to users with the ADMIN role.
     * @param category the updated category data
     * @throws ResponseStatusException if an internal error occurs
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category)
    {
        try{
            Category newCategory = categoryDao.create(category);
            if(newCategory == null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return newCategory;
        } catch (ResponseStatusException e) {
            throw e;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    /**
     * Updates an existing category.
     * Access restricted to users with the ADMIN role.
     * @param id the category ID from URL
     * @param category the updated category data
     * @throws ResponseStatusException if an internal error occurs
     */
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        try{
            if (category == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            categoryDao.update(id, category);
        } catch (ResponseStatusException e) {
            throw e;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    /**
     * Deletes a category by its ID.
     * Only admins are allowed to access
     * @param id the category ID from the URL
     * @throws ResponseStatusException if the category does not exist or an internal error occurs
     */
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id)
    {
        try {
            Category category = categoryDao.getById(id);
            if (category == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            categoryDao.delete(id);
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
