package com.tbm.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.tbm.todo.database.ToDoDatabase;
import com.tbm.todo.database.ToDoItem;



public class ToDoEditor extends AppCompatActivity {

    public static final String RESULT_ITEM = "result_item" ;
    /*private static final String DELETE_TODO ="delete_item" ;
    private static final String CREATE_TODO = "create_todo";*/
    public static final int RESULT_UPDATE = 2001;
    public static final int RESULT_DELETE = 2002;
    public static final int RESULT_CREATE = 2003;

    private String id;
    private ToDoItem item;
    private Menu menu;
    private boolean showDeleteBoolean;
    private int mNoteCompleted;

    EditText editTodo;
    public  String todoTextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_editor);

        Intent intent = getIntent();

        editTodo = findViewById(R.id.editText);
        // TODO: 25/06/2018 might need to wrap in switch statement

        if (intent.getStringExtra(MainActivity.TODO_CODE).equals("update")) {
            item = intent.getExtras().getParcelable(TodoListAdapter.ITEM_KEY);
            showDeleteBoolean = true;
            todoTextIntent = item.getNoteText();
            editTodo.setText(todoTextIntent);
            editTodo.setSelection(editTodo.getText().length());
            mNoteCompleted = item.getNoteCompleted();
        } else {
            showDeleteBoolean = false;
            editTodo.setHint("Enter ToDO");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        getMenuInflater().inflate(R.menu.todo_editor_menu, menu);
        showDelete(showDeleteBoolean);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int actionID = item.getItemId();
        switch (actionID) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.todo_menu_button:
                // TODO: 2/07/2018 make it visible only when there is an item
                //Toast.makeText(this, "Functionality to be added", Toast.LENGTH_SHORT).show();
                deleteToDo();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishedEditing();

    }

    private void finishedEditing() {
        EditText editTextView = findViewById(R.id.editText);
        String editText = editTextView.getText()
                .toString();
        // TODO: 25/06/2018 Check if string is notnull and update the entery else create new row


        if (/*todoTextIntent.length() != 0 || */todoTextIntent != null) {
            updateToDo(editText);
        } else {
            createToDo(editText);

            // super.onBackPressed();
        }
    }

    private void updateToDo(String editText) {
        Intent resultIntent = new Intent();
        if (todoTextIntent.equals(editText)) {
//            super.onBackPressed();
            setResult(RESULT_CANCELED,resultIntent);
        } else if (editText.length() == 0) {
            deleteToDo();

            //setResult(RESULT_OK);
            //super.onBackPressed();
        } else {

            resultIntent.putExtra(RESULT_ITEM, new ToDoItem(item.getNoteID(),editText,item.getNoteCompleted()));
            setResult(RESULT_UPDATE,resultIntent);
            //super.onBackPressed();
        }
        finish();
    }

    /**
     *delete the ToDoItem if there is nothing in the length
     */
    private void deleteToDo() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULT_ITEM, item);
        setResult(RESULT_DELETE,resultIntent);
        finish();
    }


    private void createToDo(String editText) {
        Intent resultIntent = new Intent();
        if (editText == null || editText.length() == 0){
            setResult(RESULT_CANCELED, resultIntent);
            finish();
            return;
        }
        resultIntent.putExtra(RESULT_ITEM, new ToDoItem(null, editText, 0));
        setResult(RESULT_CREATE, resultIntent);
        finish();
    }

    public void showDelete(boolean showDelete) {

        if (menu == null)
            return;
        menu.setGroupVisible(R.id.delete_group, showDelete);
    }
}
