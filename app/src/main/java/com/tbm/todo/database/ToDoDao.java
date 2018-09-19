package com.tbm.todo.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {
    
    @Insert
    void insert(ToDoItem... item);

    @Query("SELECT COUNT(*) from todo_table")
    int countItems();

    @Update
    void updateToDo(ToDoItem... item);

    @Query("SELECT * FROM todo_table")
    LiveData<List<ToDoItem>> getAll();

    // TODO: 25/06/2018 will need a way to update entery

    @Query("SELECT * FROM todo_table WHERE noteText = :noteText")
    ToDoItem findByToDoText(String noteText);

    @Query("SELECT * FROM todo_table WHERE noteID = :noteID")
    ToDoItem findByToDoID(String noteID);

    @Delete
    void delete(ToDoItem item);

    @Query("DELETE FROM todo_table ")
    void deleteAll();
}
