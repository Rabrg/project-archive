import rita.RiWordNet;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(final String[] args) {
        final RiWordNet wordnet = new RiWordNet("C:\\Users\\Ryan\\Downloads\\WordNet-3.0\\WordNet-3.0\\dict");
        System.out.println(getHypernyms(wordnet, "poor"));
    }

    private static List<String> getHypernyms(final RiWordNet wordNet, final String lemma) {
        final List<String> hypernyms = new ArrayList<>();
        for (final String hypernym : wordNet.getHypernyms(lemma.toLowerCase(), "n")) {
            hypernyms.add(hypernym);
        }

        final List<String> subHypernyms = new ArrayList<>();
        for (final String hypernym : hypernyms) {
            for (final String subHypernym : wordNet.getHypernyms(hypernym, "n")) {
                if (!hypernyms.contains(subHypernym) && !subHypernyms.contains(subHypernym)) {
                    subHypernyms.add(subHypernym);
                }
            }
        }
        hypernyms.addAll(subHypernyms);
        return hypernyms;
    }
}
