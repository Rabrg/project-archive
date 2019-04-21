package com.gmail.rabrg96.squad.dataset;

import com.google.gson.annotations.SerializedName;

public final class Answer {

    @SerializedName("answer_start")
    private int answerStart;
    private String text;

    public int getAnswerStart() {
        return answerStart;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerStart=" + answerStart +
                ", text='" + text + '\'' +
                '}';
    }
}
