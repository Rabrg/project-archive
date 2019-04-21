package com.gmail.rabrg96.squad.dataset;

import java.util.List;

public final class Article {

    private List<Paragraph> paragraphs;
    private String title;

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "paragraphs=" + paragraphs +
                ", title='" + title + '\'' +
                '}';
    }
}
