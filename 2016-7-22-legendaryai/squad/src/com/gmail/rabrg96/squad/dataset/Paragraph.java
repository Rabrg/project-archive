package com.gmail.rabrg96.squad.dataset;

import com.gmail.rabrg96.squad.util.Coref;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public final class Paragraph {

    private enum RelevancyMode {
        ORIGINAL,
        IGNORE_COMMON
    }

    private static final RelevancyMode mode = RelevancyMode.IGNORE_COMMON;

    private static List<String> commonWords;

    private String context;
    private List<QuestionAnswerService> qas;

    private List<String> contextSentences;

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

    // TODO: relevancy helper class. currently everything relevancy related in this class is disgusting
    public Map<String, Double> getRelevancyOrederedContextSentences(final QuestionAnswerService qas) {
        switch (mode) {
            case ORIGINAL:
                return originalRelevancy(qas);
            case IGNORE_COMMON:
                if (commonWords == null) {
                    try {
                        commonWords = Files.readAllLines(Paths.get("./res/common.txt"));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                return ignoreCommonRelevancy(qas);
        }
        return null;
    }

    // answer is in the first or second sentence over 83% of the time
    // could be improved by ignoring (or weighting) common words
    private Map<String, Double> originalRelevancy(final QuestionAnswerService qas) {
        final Map<String, Double> relevancyOrdered = new HashMap<>(getContextSentences().size());
        final Sentence questionSentence = new Sentence(qas.getQuestion());
        for (final String context : getContextSentences()) {
            final Sentence contextSentence = new Sentence(context);
            int sentenceCounter = 0;
            for (final String contextWord : contextSentence.lemmas()) {
                for (final String questionWord : questionSentence.lemmas()) {
                    if (contextWord.toLowerCase().equals(questionWord.toLowerCase())) {
                        sentenceCounter++;
                    }
                }
            }
            relevancyOrdered.put(context, ((double) sentenceCounter) / contextSentence.lemmas().size());
        }
        sortByValue(relevancyOrdered);
        return relevancyOrdered;
    }

    // ignores the most common words when calculating relevancy
    private Map<String, Double> ignoreCommonRelevancy(final QuestionAnswerService qas) {
        final Map<String, Double> relevancyOrdered = new HashMap<>(getContextSentences().size());
        final Sentence questionSentence = new Sentence(qas.getQuestion());

        final List<String> questionLemmas = new ArrayList<>(questionSentence.lemmas());
        for (int i = 0; i < questionLemmas.size(); i++) {
            questionLemmas.set(i, questionLemmas.get(i).toLowerCase());
        }
        questionLemmas.removeAll(commonWords);

        for (final String context : getContextSentences()) {
            final List<String> contextLemmas = new ArrayList<>(new Sentence(context).lemmas());
            for (int i = 0; i < contextLemmas.size(); i++) {
                contextLemmas.set(i, contextLemmas.get(i).toLowerCase());
            }
            contextLemmas.removeAll(commonWords);
            int sentenceCounter = 0;
            for (final String questionLemma : questionLemmas) {
                for (final String contextLemma : contextLemmas) {
                    if (questionLemma.equals(contextLemma)) {
                        sentenceCounter++;
                    }
                }
            }
            relevancyOrdered.put(context, ((double) sentenceCounter) / contextLemmas.size());
        }
        sortByValue(relevancyOrdered);
        return relevancyOrdered;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
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
