package pizza;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class PizzaRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<>();
        supportedApplicationIds.add("amzn1.echo-sdk-ams.app.3b878820-2ef8-48aa-a1b4-44c004392117");
    }

    public PizzaRequestStreamHandler() {
        super(new PizzaSpeechlet(), supportedApplicationIds);
    }

    public PizzaRequestStreamHandler(Speechlet speechlet,
                                     Set<String> supportedApplicationIds) {
        super(speechlet, supportedApplicationIds);
    }

}
