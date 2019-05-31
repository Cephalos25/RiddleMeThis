package com.example.riddlemethis;

public class Riddle {
    private String name;
    private String text;
    private String correctAnswer;

    public Riddle(String name, String text, String correctAnswer) {
        this.name = name;
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

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
