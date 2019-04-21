package com.gmail.rabrg96.squad.dataset;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.simple.Sentence;

import java.util.Arrays;
import java.util.List;

public final class QuestionAnswerService {

    private List<Answer> answers;
    private String id;
    private String question;

    private String questionType;
    private boolean multiplePossibleType;

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    private static final List<String> QUESTION_TYPES = Arrays.asList("who", "whom", "whose", "what", "which", "when",
            "how", "why", "where", "can", "will", "could", "should", "would", "is", "are", "shall", "may", "did", "do",
            "does");

    // TODO: enum?
    public String getQuestionType() {
        if (questionType != null)
            return questionType;

        question = question.replace("whence", "where").replace("Whence", "Where");

        // TODO: multiple sentence question
        final Sentence sentence = new Sentence(question);

        if (question.contains("_"))
            return (questionType = "blank"); // TODO: fill in the blank

        final List<String> words = sentence.words();

        // Check for multiple question words for possible errors for debugging (if there is only one question type
        // it can only be that type; it must be correct)
        int count = 0;
        for (final String word : words) {
            if (QUESTION_TYPES.contains(word.toLowerCase())) {
                if (count++ > 1) {
                    multiplePossibleType = true;
                    break;
                }
            }
        }

        // Look for question types as first to words and if so use the first
        final String first = words.get(0).toLowerCase();
        final String second = words.get(1).toLowerCase();
        if (QUESTION_TYPES.contains(first) && QUESTION_TYPES.contains(second))
            return (questionType = first);

        // Check if the question contains "When ..., <type>"
        if (question.toLowerCase().startsWith("when")) {
            for (final String type : QUESTION_TYPES) {
                if (question.contains(", " + type)) {
                    return (questionType = type);
                }
            }
        }

        // Check if last word is question type
        final String last = Character.isLetterOrDigit(words.get(words.size() - 1).charAt(0))
                ? words.get(words.size() - 1).toLowerCase() : words.get(words.size() - 2).toLowerCase();
        if (!last.equals("do") && QUESTION_TYPES.contains(last))
            return (questionType = last);

        // If not begin with two question words, use dependencies
        final SemanticGraph graph = sentence.dependencyGraph();
        for (final SemanticGraphEdge edge : graph.edgeListSorted()) {
            final String source = edge.getSource().word().toLowerCase();
            final String target = edge.getTarget().word().toLowerCase();

            String type = QUESTION_TYPES.contains(target) ? target : source; // TODO: randomly gave target priority over source

            if (QUESTION_TYPES.contains(type)) {
                if (type.equals("do") || type.equals("is") || type.equals("are")) {
                    if (question.toLowerCase().contains("what")) {
                        type = "what";
                    } else if (question.toLowerCase().contains("which")) {
                        type = "which";
                    } else if (question.toLowerCase().contains("how")) { // many
                        type = "how";
                    } else if (question.toLowerCase().contains("who")) {
                        type = "who";
                    }
                }
                return (questionType = type);
            }
        }
        throw new IllegalStateException("No question type detected for sentence \"" + question + "\"");
    }

    public boolean isMultiplePossibleType() {
        if (questionType == null)
            getQuestionType();
        return multiplePossibleType;
    }

    @Override
    public String toString() {
        return "QuestionAnswerService{" +
                "answers=" + answers +
                ", id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", questionType='" + questionType + '\'' +
                ", multiplePossibleType=" + multiplePossibleType +
                '}';
    }
}
