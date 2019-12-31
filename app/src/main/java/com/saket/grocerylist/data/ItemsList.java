package com.saket.grocerylist.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by sshriwas on 2019-11-02
 * The entity class represents the table in the DB.
 * By default the table name is the class name. However,
 * you can specify another tableName attribute with Entity annotation.
 * The members of the entity class represent the columns of the table.
 * Each entity class must have at least 1 column which is the Primary Key column.
 * It has to be marked with @PrimaryKey annot. Then you can also specify your
 * own column name by using @ColumnInfo (name = "") or you can choose to ignore
 * some members from the table by using @Ignore annot.
 */
@Entity (tableName = "grocery_list")
public class ItemsList {
    @PrimaryKey (autoGenerate = true)
    public int id;

    //@ColumnInfo (name = "list_name")
    public String list_name;
    public String products;
}
