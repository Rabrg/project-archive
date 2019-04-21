package me.rabrg.stanfordnlp;

import edu.stanford.nlp.simple.Sentence;

/**
 * Created by Rabrg on 7/6/2016.
 */
public class LemmaTest {

    public static void main(final String[] args) {
        Sentence sent = new Sentence("Lucy is in the sky with diamonds.");
        System.out.println(sent.lemmas());
        sent = new Sentence("Lucy is located the sky with diamonds.");
        System.out.println(sent.lemmas());
    }
}
