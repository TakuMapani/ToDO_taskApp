package com.tbm.todo.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "todo_table")
public class ToDoItem implements Parcelable {

    @PrimaryKey
    @NonNull
    private String noteID;
    @ColumnInfo
    private String noteText;
    @ColumnInfo
    private int noteCompleted;

    @NonNull
    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(@NonNull String noteID) {
        this.noteID = noteID;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getNoteCompleted() {
        return noteCompleted;
    }


    public void setNoteCompleted(int noteCompleted) {
        if (noteCompleted > 1 || noteCompleted < 0) {
            this.noteCompleted = 0;
        }else{
            this.noteCompleted = noteCompleted;
        }
    }
    @Ignore
    public ToDoItem(@NonNull String note_ID, String note_Text, int note_Completed) {
        if (note_ID == null) {
            note_ID = UUID.randomUUID().toString();
        }
        noteID = note_ID;
        noteText = note_Text;
        setNoteCompleted(note_Completed);
    }

    public ToDoItem() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteID);
        dest.writeString(this.noteText);
        dest.writeInt(this.noteCompleted);
    }

    protected ToDoItem(Parcel in) {
        this.noteID = in.readString();
        this.noteText = in.readString();
        this.noteCompleted = in.readInt();
    }

    public static final Parcelable.Creator<ToDoItem> CREATOR = new Parcelable.Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel source) {
            return new ToDoItem(source);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };
}
