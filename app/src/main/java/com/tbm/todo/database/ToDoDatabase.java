package com.tbm.todo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ToDoItem.class},version = 1,  exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();

    private static volatile ToDoDatabase INSTANCE;

    static ToDoDatabase getDatabase (final Context context){
        synchronized (ToDoDatabase.class){
            if ( INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ToDoDatabase.class, "TodoDB")
                        .build();
            }
        }
        return INSTANCE;
    }
}
