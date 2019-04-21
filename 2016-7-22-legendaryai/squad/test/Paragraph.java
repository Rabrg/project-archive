import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Paragraph {

    private String context;
    private List<QuestionAnswerService> qas;

    private List<String> contextSentences;
    private List<List<String>> contextSentenceLemmas;
    private Map<String, Integer> lemmaCount;

    public String getContext() {
        return context;
    }

    public List<QuestionAnswerService> getQas() {
        return qas;
    }

    public List<String> getContextSentences() {
        if (contextSentences == null) {
            final Document document = new Document(context);
            contextSentences = document.sentences().stream().map(Sentence::text).collect(Collectors.toList());
            Coref.replaceContextPronouns(this);
        }
        return contextSentences;
    }

    public Map<String, Integer> getLemmaCount() {
        if (lemmaCount == null) {
            lemmaCount = new HashMap<>();
            for (final String text : getContextSentences()) {
                final Sentence sentence = new Sentence(text);
                for (final String lemma : sentence.lemmas()) {
                    lemmaCount.put(lemma, lemmaCount.getOrDefault(lemma, 0) + 1);
                }
            }
        }
        return lemmaCount;
    }

    public List<String> getContextSentenceLemmas(final int i) {
        if (contextSentenceLemmas == null) {
            contextSentenceLemmas = new ArrayList<>();
            for (final String contextSentence : getContextSentences())
                contextSentenceLemmas.add(new ArrayList<>(new Sentence(contextSentence).lemmas()));
        }
        return contextSentenceLemmas.get(i);
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "context='" + context + '\'' +
                ", qas=" + qas +
                ", contextSentences=" + getContextSentences() +
                '}';
    }
}
