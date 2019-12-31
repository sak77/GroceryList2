package com.saket.grocerylist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by sshriwas on 2019-11-02
 *
 * The Database is an abstract class that acts an interface between the SQLite DB
 * and the Room BD classes. It must contain an abstract method that returns an instance
 * of the DAO class.
 */

@Database(entities = {Product.class, ItemsList.class}, version = 1)
public abstract class GroceryRoomDatabase extends RoomDatabase {
    public abstract ListDao listDao();
    public abstract ProductDao productDao();
}
