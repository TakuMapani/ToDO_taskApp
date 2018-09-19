package com.tbm.todo.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ToDoRepository {
    private ToDoDao mToDoDao;
    private LiveData<List<ToDoItem>> mTodoList;

    public ToDoRepository(Application application){
        ToDoDatabase db = ToDoDatabase.getDatabase(application);
        mToDoDao = db.toDoDao();
        mTodoList = mToDoDao.getAll();
    }

    public LiveData<List<ToDoItem>> getAllTodos(){
        return mTodoList;
    }

    public void insert (ToDoItem toDoItem){
        new insertAsyncTask(mToDoDao).execute(toDoItem);
    }

    private static class insertAsyncTask extends AsyncTask<ToDoItem, Void, Void> {

        private ToDoDao mAsyncDao;

         insertAsyncTask(ToDoDao dao) {
             mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final ToDoItem... toDoItems) {
            mAsyncDao.insert(toDoItems[0]);
            return null;
        }
    }
}
