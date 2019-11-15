package com.saket.grocerylist.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Created by sshriwas on 2019-11-02
 */
@Dao
public interface ListDao {
    @Insert
    long addGroceryList(ItemsList list);

    @Query("SELECT * FROM GROCERY_LIST")
    List<ItemsList> getAllLists();
}
