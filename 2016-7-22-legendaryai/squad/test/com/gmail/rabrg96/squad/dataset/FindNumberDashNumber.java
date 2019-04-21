package com.gmail.rabrg96.squad.dataset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;

public class FindNumberDashNumber {

    public static void main(final String[] args) throws IOException {
        final Gson gson = new GsonBuilder().create();
        final Dataset dataset = gson.fromJson(new FileReader("./res/dev-v1.0.json"), Dataset.class);
        int counter = 0;
        ARTICLE_LOOP:
        for (final Article article : dataset.getData()) {
            for (final Paragraph paragraph : article.getParagraphs()) {
                for (final String sentence : paragraph.getContextSentences()) {
                    if (sentence.contains("-") || sentence.contains("–")) {
                        final char c = sentence.contains("–") ? '–' : '-';
                        final String noWhitespace = sentence.replaceAll("\\s+", "");
                        if (Character.isDigit(noWhitespace.charAt(noWhitespace.indexOf(c) - 1)) || Character.isDigit(noWhitespace.charAt(noWhitespace.indexOf(c) + 1))) {
                            System.out.println(sentence);
                            counter++;
                            if (counter == 5) {
                                counter = 0;
                                continue ARTICLE_LOOP;
                            }
                        }
                    }
                }
            }
        }
    }
}
