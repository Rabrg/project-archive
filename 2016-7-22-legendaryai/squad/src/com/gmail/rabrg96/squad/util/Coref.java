package com.gmail.rabrg96.squad.util;

import com.gmail.rabrg96.squad.dataset.Paragraph;
import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Rabrg on 7/13/2016.
 */
public class Coref {

    private static final boolean DEBUG = false;

    private static final List<String> PRONOUNS = Arrays.asList("hers", "herself", "him", "himself", "hisself", "it",
            "itself", "me", "myself", "one", "oneself", "ours", "ourselves", "ownself", "self", "she", "thee",
            "theirs", "them", "themselves", "they", "thou", "thy", "us", "her", "his", "mine", "my", "our", "ours",
            "their", "thy", "your"); // TODO: use POS tagger?

    private static final Properties props;
    private static final StanfordCoreNLP pipeline;

    static {
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        pipeline = new StanfordCoreNLP(props);
    }

    public static void replaceContextPronouns(final Paragraph paragraph) {
        final Annotation document = new Annotation(paragraph.getContext());
        pipeline.annotate(document);

        final Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
        for (Map.Entry<Integer, CorefChain> entry : graph.entrySet()) {
            CorefChain chain = entry.getValue();
            final List<CorefChain.CorefMention> mentions = chain.getMentionsInTextualOrder();
            if (mentions.size() > 1) { // TODO: word precision when not using exclusively pronouns
                StringBuilder builder = new StringBuilder();
                builder.append("Context before: " + paragraph.getContextSentences() + "\n");
                boolean replaced = false;
                final String first = mentions.get(0).mentionSpan;
                for (int i = 1; i < mentions.size(); i++) {
                    final CorefChain.CorefMention mention = mentions.get(i);
                    if (PRONOUNS.contains(mention.mentionSpan.toLowerCase())) {
                        replaced = true;
                        builder.append("Replacing " + mention.mentionSpan + " with " + first + "\n");
                        paragraph.getContextSentences().set(mention.sentNum - 1,
                                paragraph.getContextSentences().get(mention.sentNum - 1).replace(mention.mentionSpan, first));
                    }
                }
                builder.append("Context after: " + paragraph.getContextSentences() + "\n");
                builder.append("============\n");
                if (DEBUG && replaced) {
                    System.out.print(builder);
                }
            }
        }
    }
}
