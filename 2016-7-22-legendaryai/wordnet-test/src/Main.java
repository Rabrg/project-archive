import rita.RiWordNet;

public class Main {
    public static void main(String[] args) {
        try {
            // Would pass in a PApplet normally, but we don't need to here
            RiWordNet wordnet = new RiWordNet("C:\\Users\\Ryan\\Downloads\\WordNet-3.0\\WordNet-3.0\\dict");
            // Demo finding parts of speech
            String word = "team";
            System.out.println("\nFinding parts of speech for " + word + ".");
            String[] partsofspeech = wordnet.getPos(word);
            for (int i = 0; i < partsofspeech.length; i++) {
                System.out.println(partsofspeech[i]);
            }

            //word = "eat";
            String pos = wordnet.getBestPos(word);
            System.out.println("\n\nDefinitions for " + word + ":");
            // Get an array of glosses for a word
            String[] glosses = wordnet.getAllGlosses(word, pos);
            // Display all definitions
            for (int i = 0; i < glosses.length; i++) {
                System.out.println(glosses[i]);
            }

            // Demo finding a list of related words (synonyms)
            //word = "first name";
            String[] poss = wordnet.getPos(word);
            for (int j = 0; j < poss.length; j++) {
                System.out.println("\n\nSynonyms for " + word + " (pos: " + poss[j] + ")");
                String[] synonyms = wordnet.getAllSynonyms(word, poss[j], 10);
                for (int i = 0; i < synonyms.length; i++) {
                    System.out.println(synonyms[i]);
                }
            }

            // Demo finding a list of related words
            // X is Hypernym of Y if every Y is of type X
            // Hyponym is the inverse
            //word = "nurse";
            pos = wordnet.getBestPos(word);
            System.out.println("\n\nHyponyms for " + word + ":");
            String[] hyponyms = wordnet.getAllHyponyms(word, pos);
            //System.out.println(hyponyms.length);
            //if(hyponyms!=null)
            for (int i = 0; i < hyponyms.length; i++) {


                System.out.println(hyponyms[i]);
            }

            System.out.println(pos);
            System.out.println("\n\nHypernyms for " + word + ":");
            String[] hypernyms = wordnet.getAllHypernyms(word, pos);
            //if(hypernyms!=null)
            for (int i = 0; i < hypernyms.length; i++) {
                System.out.println(hypernyms[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
  }