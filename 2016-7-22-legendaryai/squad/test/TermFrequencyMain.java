import com.gmail.rabrg96.squad.dataset.SheetDataFinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TermFrequencyMain {

//    public static void main(final String[] args) throws IOException {
//        final Gson gson = new GsonBuilder().create();
//        final Dataset dataset = gson.fromJson(new FileReader("./res/dev-v1.0.json"), Dataset.class);
//        int correct = 0, total = 0;
//        for (final Article article : dataset.getData()) {
//            for (final Paragraph paragraph : article.getParagraphs()) {
//                for (final QuestionAnswerService qas : paragraph.getQas()) {
//                    final List<String> questionLemmas = qas.getQuestionLemmas();
//                    questionLemmas.removeAll(STOP_WORDS);
//                    final int[] commonLemmaCount = new int[paragraph.getContextSentences().size()];
//                    for (int i = 0; i < paragraph.getContextSentences().size(); i++) {
//                        final List<String> contextLemmas = paragraph.getContextSentenceLemmas(i);
//                        contextLemmas.removeAll(STOP_WORDS);
//                        for (final String lemma : contextLemmas) {
//                            if (questionLemmas.contains(lemma)) {
//                                commonLemmaCount[i] = commonLemmaCount[i] + 1;
//                            }
//                        }
//                    }
//                    int most = 0, mostIndex = 0;
//                    for (int i = 0; i < commonLemmaCount.length; i++) {
//                        if (commonLemmaCount[i] > most)
//                            mostIndex = i;
//                    }
//                    if (paragraph.getContextSentences().get(mostIndex).contains(qas.getAnswers().get(0).getText()))
//                        correct++;
//                    total++;
//                    System.out.println(correct + "/" + total);
//                }
//            }
//        }
//    }

    public static void main(final String[] args) throws IOException {
        System.out.println(SheetDataFinder.getData("The American Football Conference (AFC) champion Denver Broncos defeated the National Football Conference (NFC) champion Carolina Panthers 24–10 to earn their third Super Bowl"));
//        final Gson gson = new GsonBuilder().create();
//        final Dataset dataset = gson.fromJson(new FileReader("./res/dev-v1.0.json"), Dataset.class);
//        int correct = 0, total = 0;
//        final Map<String, Integer> correctMap = new HashMap<>();
//        final Map<String, Integer> totalMap = new HashMap<>();
////        for (final Article article : dataset.getData()) {
//            for (final Paragraph paragraph : dataset.getData().get(0).getParagraphs()) {
//                final List<SheetDataFinder.Data> contextData = new ArrayList<>();
//                for (final String sentence : paragraph.getContextSentences())
//                    contextData.add(SheetDataFinder.getData(sentence));
//
//                for (final QuestionAnswerService qas : paragraph.getQas()) {
//                    final int[] score = new int[paragraph.getContextSentences().size()];
//                    final SheetDataFinder.Data questionData = SheetDataFinder.getData(qas.getQuestion());
//                    for (int i = 0; i < contextData.size(); i++) {
//                        final SheetDataFinder.Data context = contextData.get(i);
//                        if (context.getSubject() != null && questionData.getSubject() != null && context.getSubject().equalsIgnoreCase(questionData.getSubject()))
//                            score[i]++;
//                        if (context.getDirectObject() != null && questionData.getDirectObject() != null && context.getDirectObject().equalsIgnoreCase(questionData.getDirectObject()))
//                            score[i]++;
//                        if (context.getVerb() != null && questionData.getVerb() != null && context.getVerb().equalsIgnoreCase(questionData.getVerb()))
//                            score[i]++;
//                        if (context.getObjectComplement() != null && questionData.getObjectComplement() != null && context.getObjectComplement().equalsIgnoreCase(questionData.getObjectComplement()))
//                            score[i]++;
//                    }
//                    int most = 0, mostIndex = -1;
//                    for (int i = 0; i < score.length; i++) {
//                        if (score[i] > most)
//                            mostIndex = i;
//                    }
//                    if (mostIndex >= 0 && paragraph.getContextSentences().get(mostIndex).contains(qas.getAnswers().get(0).getText())) {
//                        correct++;
//                        correctMap.put(questionData.getWhWord() == null ? "null" : questionData.getWhWord(), correctMap.getOrDefault(questionData.getWhWord() == null ? "null" : questionData.getWhWord(), 0) + 1);
//                    } else {
//                        for (final SheetDataFinder.Data data : contextData) {
//                            if (data.getText().contains(qas.getAnswers().get(0).getText())) {
//                                System.out.println("~~~FAILED~~~");
//                                System.out.println("question=" + qas.getQuestion() + ", subject=" + questionData.getSubject() + ", directObject=" + questionData.getDirectObject() + ", verb=" + questionData.getVerb() + ", objectComplement=" + questionData.getObjectComplement());
//                                System.out.println("answer=" + qas.getAnswers().get(0));
//                                System.out.println("contextSentence=" + data.getText() + ", subject=" + data.getSubject() + ", directObject=" + data.getDirectObject() + ", verb=" + data.getVerb() + ", objectComplement=" + data.getObjectComplement());
//                                System.out.println();
//                                break;
//                            }
//                        }
//                    }
//                    total++;
//                    totalMap.put(questionData.getWhWord() == null ? "null" : questionData.getWhWord(), totalMap.getOrDefault(questionData.getWhWord() == null ? "null" : questionData.getWhWord(), 0) + 1);
//                }
////                for (final Map.Entry<String, Integer> entry : correctMap.entrySet()) {
////                    if (entry.getKey() == null)
////                        continue;
////                    System.out.println(entry.getKey() + " [" + entry.getValue() + "/" + totalMap.getOrDefault(entry.getKey(), 0) + "] [" + ((double) entry.getValue()) / totalMap.getOrDefault(entry.getKey(), 0) + "]");
////                }
////                System.out.println("Total [" + correct + "/" + total + "] [" + (((double) correct) / total) + "]");
//            }
    }

    private static final List<String> STOP_WORDS = Arrays.asList("a", "about", "above", "above", "across", "after",
            "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always",
            "am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything",
            "anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes",
            "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between",
            "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could",
            "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight",
            "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone",
            "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five",
            "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give",
            "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein",
            "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in",
            "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly",
            "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more",
            "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never",
            "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere",
            "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our",
            "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re",
            "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side",
            "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime",
            "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them",
            "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon",
            "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout",
            "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un",
            "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever",
            "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon",
            "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why",
            "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves",
            "the");
}
