package com.saket.grocerylist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by sshriwas on 2019-11-02
 */

@Database(entities = {Product.class, ItemsList.class}, version = 1)
public abstract class GroceryRoomDatabase extends RoomDatabase {
    public abstract ListDao listDao();
    public abstract ProductDao productDao();
}
