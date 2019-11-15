package com.saket.grocerylist.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by sshriwas on 2019-11-02
 */
@Entity (tableName = "grocery_list")
public class ItemsList {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public String list_name;
    public String products;
}
