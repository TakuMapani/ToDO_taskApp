package com.tbm.todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tbm.todo.database.ToDoItem;
import com.tbm.todo.database.ToDoRepository;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {

    private ToDoRepository mRepository;
    private LiveData<List<ToDoItem>> mToDoList;

    public ToDoViewModel (Application application){
        super(application);
        mRepository = new ToDoRepository(application);
        mToDoList = mRepository.getAllTodos();
    }

    public LiveData<List<ToDoItem>> getAllTodos() {
        return mToDoList;
    }

    public void insert(ToDoItem toDoItem){
        mRepository.insert(toDoItem);
    }

    public void update(ToDoItem toDoItem){
        mRepository.update(toDoItem);
    }

    public void delete(ToDoItem toDoItem){
        mRepository.delete(toDoItem);
    }
}
