package com.saket.grocerylist.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Created by sshriwas on 2019-11-02
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
