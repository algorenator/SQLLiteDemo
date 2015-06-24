package com.comp3617.week2.sqllitedemo;


import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private long id;
    private String title;
    private String body;


    public Note() {

    }

    public Note(Parcel in){
        id = in.readLong();
        title = in.readString();
        body = in.readString();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
//return title;
        return title + "\n[" + body +"]";

    }
    @Override
    public int describeContents() {
//Called by Android to determine the type of
//special object - for common data types,
//just return 0
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getTitle());
        dest.writeString(getBody());
    }

    //A static field must always be defined and should be called CREATOR
    public static final Parcelable.Creator<Note> CREATOR = new Creator<Note>() {

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }

        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }
    };
}