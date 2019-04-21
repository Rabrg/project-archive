package com.gmail.rabrg96.squad.dataset;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetDataFinder {

    private static final LexicalizedParser parser = LexicalizedParser.loadModel();
    private static final PennTreebankLanguagePack languagePack = new PennTreebankLanguagePack();
    private static final GrammaticalStructureFactory structureFactory = languagePack.grammaticalStructureFactory();

    public static Data getData(final String text) {
        try {
            final Tree parent = parser.parse(text);
//            parent.pennPrint();
            final Tree tree = findWHTree(parent);

            final String whType = tree != null ? tree.value() : null;
            final String whWord = tree != null ? tree.getChild(0).yieldWords().get(0).word() : null;
            final String whTargetWord = tree != null && tree.children().length > 1 ? combineWords(tree) : null;
            final String whTargetType = tree != null && tree.children().length > 1 ? tree.getChild(1).value() : null;
            final String whTargetPlural = whTargetWord != null && whTargetType != null
                    && (whTargetType.equals("NNS") || whTargetWord.equals("NNPS")) ? "Plural" : "Singular";

            final List<TypedDependency> dependencies = structureFactory.newGrammaticalStructure(parser.parse(text))
                    .typedDependenciesCCprocessed();
            for (final TypedDependency dependency : dependencies)
                System.out.println(dependency);
            final String subject = getSubject(dependencies);
            final String directObject = getDirectObject(dependencies);
            final String verb = getVerb(dependencies);
            final String objectComplement = getObjectComplement(dependencies);
            final Map<String, String> abbreviations = getAbbreviations(dependencies);

            final Sentence sentence = new Sentence(text);
            final List<String> tags = sentence.nerTags();
            final List<String> words = sentence.words();

            // TODO: find list of all tags
            final List<String> organizationTags = getNERTags(tags, words, "ORGANIZATION");
            final List<String> locationTags = getNERTags(tags, words, "LOCATION");
            final List<String> personTags = getNERTags(tags, words, "PERSON");
            final List<String> numberTags = getNERTags(tags, words, "NUMBER");
            final List<String> ordinalTags = getNERTags(tags, words, "ORDINAL");
            final List<String> miscTags = getNERTags(tags, words, "MISC");

            return new Data(text, whType, whWord, whTargetType, whTargetWord, whTargetPlural, subject, directObject,
                    verb, objectComplement, organizationTags, locationTags, personTags, numberTags, ordinalTags,
                    miscTags, abbreviations);
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Failed: " + text);
        }
        return null;
    }

    private static String combineWords(final Tree tree) {
        final StringBuilder combined = new StringBuilder();
        final Tree[] children = tree.children();
        for (int i = 1; i < children.length; i++)
            combined.append(children[i].yieldWords().get(0).word()).append(' ');
        return combined.substring(0, combined.length() - 1);
    }

    private static Tree findWHTree(final Tree parent) {
        for (final Tree child : parent.children())
            return parent.value().startsWith("WH") && !child.value().startsWith("WH") ? parent : findWHTree(child);
        return null;
    }

    private static String getSubject(final List<TypedDependency> dependencies) {
        String rootSubject = null, subject = null;
        for (int i = dependencies.size() - 1; i >= 0; i--) {
            final TypedDependency dependency = dependencies.get(i);
            if (dependency.reln().toString().contains("subj"))
                rootSubject = subject = dependency.dep().word();
            else if (dependency.reln().toString().contains("compound") && dependency.gov().word().equals(rootSubject))
                subject = dependency.dep().word() + " " + subject;
        }
        if (subject == null)
            return null;
        String lemmaSubject = "";
        for (final String lemma : new Sentence(subject).lemmas()) {
            lemmaSubject += lemma + " ";
        }
        return lemmaSubject.trim();
    }

    private static String getDirectObject(final List<TypedDependency> dependencies) {
        String rootDirectObject = null, directObject = null;
        for (int i = dependencies.size() - 1; i >= 0; i--) {
            final TypedDependency dependency = dependencies.get(i);
            if (dependency.reln().toString().contains("dobj"))
                rootDirectObject = directObject = dependency.dep().word();
            else if (dependency.reln().toString().contains("compound") && dependency.gov().word().equals(rootDirectObject))
                directObject = dependency.dep().word() + " " + directObject;
        }
        if (directObject == null)
            return null;
        String lemmaDirectObject = "";
        for (final String lemma : new Sentence(directObject).lemmas()) {
            lemmaDirectObject += lemma + " ";
        }
        return lemmaDirectObject.trim();
    }

    private static String getVerb(final List<TypedDependency> dependencies) {
        for (final TypedDependency dependency : dependencies)
            if (dependency.reln().toString().contains("root"))
                return new Sentence(dependency.dep().word()).lemmas().get(0);
        return null;
    }

    private static String getObjectComplement(final List<TypedDependency> dependencies) {
        for (final TypedDependency dependency : dependencies)
            if (dependency.reln().toString().contains("xcomp"))
                return new Sentence(dependency.dep().word()).lemmas().get(0);
        return null;
    }

    private static List<String> getNERTags(final List<String> tags, final List<String> words, final String relevantTag) {
        final List<String> relevantTags = new ArrayList<>();
        String tag = "";
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).equalsIgnoreCase(relevantTag)) {
                tag += " " + words.get(i);
            } else if (tag.length() > 0) {
                relevantTags.add(tag.trim());
                tag = "";
            }
        }
        return relevantTags;
    }

    private static Map<String, String> getAbbreviations(final List<TypedDependency> dependencies) {
        final Map<String, String> abbreviations = new HashMap<>();

        String key = null, rootValue = null, value = null;
        for (int i = dependencies.size() - 1; i >= 0; i--) {
            final TypedDependency dependency = dependencies.get(i);
            if (dependency.reln().toString().contains("appos")) {
                if (key != null)
                    abbreviations.put(key, value);
                key = dependency.dep().word();
                value = rootValue = dependency.gov().word();
            } else if (key != null && dependency.reln().toString().contains("compound") && dependency.gov().word().contains(rootValue)) {
                value = dependency.dep().word() + " " + value;
            }
        }
        if (key != null)
            abbreviations.put(key, value);
        return abbreviations;
    }

    public static final class Data {

        private final String text;
        private final String whType;
        private final String whWord;
        private final String whTargetType;
        private final String whTargetWord;
        private final String whTargetWordPlural;
        private final String subject;
        private final String directObject;
        private final String verb;
        private final String objectComplement;
        private final List<String> organizationTags;
        private final List<String> locationTags;
        private final List<String> personTags;
        private final List<String> numberTags;
        private final List<String> ordinalTags;
        private final List<String> miscTags;
        private final Map<String, String> abbreviations;

        public Data(String text, String whType, String whWord, String whTargetType, String whTargetWord, String whTargetWordPlural, String subject, String directObject, String verb, String objectComplement, List<String> organizationTags, List<String> locationTags, List<String> personTags, List<String> numberTags, List<String> ordinalTags, List<String> miscTags, Map<String, String> abbreviations) {
            this.text = text;
            this.whType = whType;
            this.whWord = whWord;
            this.whTargetType = whTargetType;
            this.whTargetWord = whTargetWord;
            this.whTargetWordPlural = whTargetWordPlural;
            this.subject = subject;
            this.directObject = directObject;
            this.verb = verb;
            this.objectComplement = objectComplement;
            this.organizationTags = organizationTags;
            this.locationTags = locationTags;
            this.personTags = personTags;
            this.numberTags = numberTags;
            this.ordinalTags = ordinalTags;
            this.miscTags = miscTags;
            this.abbreviations = abbreviations;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "text='" + text + '\'' +
                    ", whType='" + whType + '\'' +
                    ", whWord='" + whWord + '\'' +
                    ", whTargetType='" + whTargetType + '\'' +
                    ", whTargetWord='" + whTargetWord + '\'' +
                    ", whTargetWordPlural='" + whTargetWordPlural + '\'' +
                    ", subject='" + subject + '\'' +
                    ", directObject='" + directObject + '\'' +
                    ", verb='" + verb + '\'' +
                    ", objectComplement='" + objectComplement + '\'' +
                    ", organizationTags=" + organizationTags +
                    ", locationTags=" + locationTags +
                    ", personTags=" + personTags +
                    ", numberTags=" + numberTags +
                    ", ordinalTags=" + ordinalTags +
                    ", miscTags=" + miscTags +
                    ", abbreviations=" + abbreviations +
                    '}';
        }
    }
}
