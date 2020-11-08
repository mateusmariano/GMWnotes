package com.example.gmwnotes.Database;

import android.content.Context;
import android.util.EventLog;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities =  {EntityClass.class}, version = 1)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract EventDao EventDao();
    private static DatabaseClass INSTANCE;

    public static DatabaseClass getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseClass.class){
                if(INSTANCE == null){
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                            DatabaseClass.class, "product_database")
                                    .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
