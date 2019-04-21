import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.List;

public final class QuestionAnswerService {

    private List<Answer> answers;
    private String id;
    private String question;

    private List<String> questionLemmas;

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getQuestionLemmas() {
        return questionLemmas == null ? (questionLemmas = new ArrayList<>(new Sentence(question).lemmas())) : questionLemmas;
    }

    @Override
    public String toString() {
        return "QuestionAnswerService{" +
                "answers=" + answers +
                ", id='" + id + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
