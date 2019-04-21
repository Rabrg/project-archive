package me.rabrg.stanfordnlp;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Quadruple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Triple {

    private final String sentenceText;

    private final List<String> subjects = new ArrayList<>();
    private final List<String> relations = new ArrayList<>();
    private final List<String> objects = new ArrayList<>();

    public Triple(final String sentenceText) {
        this(new Sentence(sentenceText));
    }

    public Triple(final Sentence sentence) {
        // built-in version
        boolean success = false;
        for (final Quadruple<String, String, String, Double> triple : sentence.openie()) {
            System.out.println("Stanford: " + sentence.text() + ": " + triple);
            success = true;
            break;
        }
        if (!success)
            System.out.println("Stanford: " + sentence.text() + ": FAIL");

        sentenceText = sentence.text();


        // I tried a couple of different methods to do this and this worked the best and is the most simple
        final List<String> words = sentence.words();
        final List<String> posTags = sentence.posTags();
        for (int i = 0; i < words.size(); i++) {
            final String word = words.get(i);
            final String posTag = posTags.get(i);
            if (relations.size() == 0 && isNoun(posTag))
                subjects.add(word);
            else if (objects.size() == 0 && isVerb(posTag))
                relations.add(word);
            else if (relations.size() > 0 && isNoun(posTag))
                objects.add(word);
        }
    }

    public static void main(final String[] args) {
        final Document document = new Document(readFile("document.txt"));
        for (final Sentence sentence : document.sentences()) {
            System.out.println("Ryan: " + new Triple(sentence));
        }
    }

    private static String readFile(final String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to read file " + path);
        }
    }

    private static boolean isNoun(final String tag) {
        return tag.startsWith("N") || tag.startsWith("P");
    }

    private static boolean isVerb(final String tag) {
        return tag.startsWith("V");
    }

    public String getSentenceText() {
        return sentenceText;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getRelations() {
        return relations;
    }

    public List<String> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        return "sentenceText='" + sentenceText + '\'' +
                ", subjects='" + subjects + '\'' +
                ", relations='" + relations + '\'' +
                ", objects='" + objects + '\'';
    }
}