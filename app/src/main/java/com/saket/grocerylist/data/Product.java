package com.saket.grocerylist.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by sshriwas on 2019-11-02
 */
@Entity
public class Product {

    @PrimaryKey (autoGenerate = true)
    public int product_id;

    public String product_name;
    public String qty;
}
