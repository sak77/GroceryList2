package com.saket.grocerylist.data;

import android.content.Context;

import androidx.room.Room;

/**
 * Created by sshriwas on 2019-11-02
 *
 *  If your app runs in a single process, you should follow the singleton design pattern when
 *  instantiating an AppDatabase object. Each RoomDatabase instance is fairly expensive, and you
 *  rarely need access to multiple instances within a single process.
 *
 * If your app runs in multiple processes, include enableMultiInstanceInvalidation() in your
 * database builder invocation. That way, when you have an instance of AppDatabase in each
 * process, you can invalidate the shared database file in one process, and this invalidation
 * automatically propagates to the instances of AppDatabase within other processes.
 */
public class SingletonGroceryDBInstance {

    private static GroceryRoomDatabase mSingletonGroceryDB;

    public static GroceryRoomDatabase getInstance(Context context) {
        if (mSingletonGroceryDB == null) {
            mSingletonGroceryDB = Room.databaseBuilder(context, GroceryRoomDatabase.class,
                    "GroceryListDB").build();
        }
        return mSingletonGroceryDB;
    }
}
