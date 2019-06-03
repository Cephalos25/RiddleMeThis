package com.example.riddlemethis;

import android.os.Parcel;
import android.os.Parcelable;

public class Riddle implements Parcelable {
    private String name;
    private String text;
    private String correctAnswer;

    public Riddle() {
        this("", "", "");
    }

    public Riddle(String name, String text, String correctAnswer) {
        this.name = name;
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    protected Riddle(Parcel in) {
        name = in.readString();
        text = in.readString();
        correctAnswer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(text);
        dest.writeString(correctAnswer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Riddle> CREATOR = new Creator<Riddle>() {
        @Override
        public Riddle createFromParcel(Parcel in) {
            return new Riddle(in);
        }

        @Override
        public Riddle[] newArray(int size) {
            return new Riddle[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
