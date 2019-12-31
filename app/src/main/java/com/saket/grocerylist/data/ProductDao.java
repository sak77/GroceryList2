package com.saket.grocerylist.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Created by sshriwas on 2019-11-02
 * Dao class provides access to the DB using entities.
 *It has convenience methods. These methods can have following annots -
 * @Query - fetch data
 * @Insert - Insert data
 * @Update
 * @Delete
 */
@Dao
public interface ProductDao {
    @Insert
    long addProduct(Product product);

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM Product where product_id = :product_id")
    Product getProductById(int product_id);
}
