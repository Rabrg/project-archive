package com.gmail.rabrg96.squad.dataset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.util.Map;
import java.util.Set;

public class DatasetTest {

    public static void main(final String[] args) {
        try {
            final Gson gson = new GsonBuilder().create();
            final Dataset dataset = gson.fromJson(new FileReader("./res/dev-v1.0.json"), Dataset.class);

            double confidenceSum = 0;
            int correct = 0;
            int counter = 0;
            for (final Paragraph paragraph : dataset.getData().get(0).getParagraphs()) {
                paragraph.getContextSentences();
//                for (final String text : paragraph.getContextSentences()) {
//                    final Sentence sentence = new Sentence(text);
//                    final List<String> words = sentence.words();
//                    final List<String> nerTags = sentence.nerTags(); // TODO: verify 7 tag
//                    System.out.print("CONTEXT SENTENCE: ");
//                    for (int i = 0; i < words.size(); i++) {
//                        System.out.print(words.get(i) + " ");
//                        if (!"O".equals(nerTags.get(i))) {
//                            System.out.print("(" + nerTags.get(i) + ") ");
//                        }
//                    }
//                    System.out.println();
//                }

                for (final QuestionAnswerService qas : paragraph.getQas()) {
                    try {
                        boolean first = true;
                        for (final Map.Entry<String, Double> context : paragraph.getRelevancyOrederedContextSentences(qas).entrySet()) {
                            if (context.getKey().contains(qas.getAnswers().get(0).getText())) {
                                if (first) {
                                    correct++;
                                }
                                confidenceSum += context.getValue();
                                counter++;
                                System.out.println("Confidence: [" + context.getValue() + "], sentence: [" + context.getKey() + "], question: [" + qas.getQuestion() + "] answer: [" + qas.getAnswers().get(0).getText() + "]");
                                break;
                            }
                            first = false;
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Average relevancy rating: " + (confidenceSum / counter));
            System.out.println("Percent correct: " + (((double) correct) / counter));
//            final long start = System.currentTimeMillis();
//            int articles = 0, paragraphs = 0, questions = 0;
//            for (final Article article : dataset.getData()) {
//                articles++;
//                for (final Paragraph paragraph : article.getParagraphs()) {
//                    paragraphs++;
//                    for (final String sentenceText : paragraph.getContextSentences()) {
//                        System.out.println(new Sentence(sentenceText).nerTags() + ": " + sentenceText);
//                    }
//                    for (final QuestionAnswerService qas : paragraph.getQas()) {
//                        questions++;
//                        try {
//                            if ("who".equals(qas.getQuestionType())) {
//                                System.out.println(qas.getAnswers() + ": " + qas.getQuestion());
//                            }
//                        } catch (final IllegalStateException e) {
////                            System.out.println("Failed to detect type: " + qas.getQuestion());
//                        }
//                    }
//                    break; // one paragraph
//                }
//            }
//            final long elapsed = (System.currentTimeMillis() - start);
//            System.out.println("Parsed " + questions + " questions in " + elapsed + "ms (" + (((double) elapsed) / questions) + "ms/q)");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
