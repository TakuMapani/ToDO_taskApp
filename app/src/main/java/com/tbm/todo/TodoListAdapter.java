package com.tbm.todo;


import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbm.todo.database.ToDoDatabase;
import com.tbm.todo.database.ToDoItem;

import java.io.Serializable;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ToDoViewHolder> {
    public static final String TODO_TEXT = "todo_text";
    public static final String TODO_ID = "todo_id";
    public static final String ITEM_KEY = "item_key";
    private Context context;

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        CheckBox mCheckBox;
        View mVIew;

        private ToDoViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.im_view);
            mTextView = itemView.findViewById(R.id.tv_list);
            mCheckBox = itemView.findViewById(R.id.todo_completed);
            mVIew = itemView;
        }
    }

    private final LayoutInflater mInflater;
    private List<ToDoItem> mToDoList; // Cached copy of words

    TodoListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context); }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.todo_list_item, parent, false);
        return new ToDoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ToDoViewHolder holder, int position) {
        final ToDoItem item = mToDoList.get(position);
//        List<TodoListItem> mToDoList = MainActivity.getTodoList();
//        RecyclerView.Adapter mAdapter = MainActivity.getAdapter();
        //final ToDoDatabase mDB = MainActivity.getDb();

        String tdText = item.getNoteText();
        /*
        checking if there is a line feed in the text
        if there is a line feed  return "..." at position
         */
        int posLineFeed = tdText.indexOf(10);
        if (posLineFeed != -1) {
            tdText = tdText.substring(0,posLineFeed) + "...";
        }

        //setting the text and image for the file
        holder.mTextView.setText(tdText);
        holder.mImageView.setImageResource(R.drawable.ic_file_temp);

        // TODO: 19/09/2018 Update the checkbox for AsyncTask
        /**updating the tickbox if there is a selection
         *
         */
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.mCheckBox.isChecked()){
                    item.setNoteCompleted(1);
                }else{
                    item.setNoteCompleted(0);
                }

                if(item.getNoteCompleted() == 1){
                    holder.mCheckBox.setChecked(true);
                    holder.mTextView.setPaintFlags(holder.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.mCheckBox.setChecked(false);
                    holder.mTextView.setPaintFlags( holder.mTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

                ToDoViewModel viewModel = MainActivity.getmToDoViewModel();
                viewModel.update(item);

               // mDB.toDoDao().updateToDo(item);

            }
        });


        /**
        crossing out the text when the checkbox is active
         */
        if(item.getNoteCompleted() == 1){
            holder.mCheckBox.setChecked(true);
            holder.mTextView.setPaintFlags(holder.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.mCheckBox.setChecked(false);
            holder.mTextView.setPaintFlags( holder.mTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        /**
         * opening the ToDoItem to update it in @see com.tbm.todo.ToDoEditor.onCreate()
         * The intent should send the object to the editor activity
         *
         */
        final String finalTdText = tdText;
        holder.mVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToDoEditor.class);
                intent.putExtra("1001", "update");
                intent.putExtra(ITEM_KEY,  item);
                /*intent.putExtra(TODO_TEXT, finalTdText);
                intent.putExtra(TODO_ID,item.getNoteID());*/
                ((MainActivity) context).startActivityForResult(intent, 101);
            }
        });
    }

    void setWords(List<ToDoItem> words){
        mToDoList = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mToDoList != null)
            return mToDoList.size();
        else return 0;
    }
}
