package me.rabrg.stanfordnlp;

import rita.*;

import java.util.Arrays;

public class SimpleExample {

  public static void main(String[] args) {

    // Would pass in a PApplet normally, but we don't need to here
    RiWordNet wordnet = new RiWordNet("C:\\Program Files (x86)\\WordNet\\2.1");

    // Get a random noun
    String word = "in";//wordnet.getRandomWord("n");
    // Get max 15 synonyms
    String[] synonyms = wordnet.getAllSynonyms(word, "v");

    System.out.println("Random noun: " + word);
    if (synonyms != null) {
      // Sort alphabetically
      Arrays.sort(synonyms);
      for (int i = 0; i < synonyms.length; i++) {
        System.out.println("Synonym " + i + ": " + synonyms[i]);
      }
    } else {
      System.out.println("No synyonyms!");
    }
  }
}