import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class QAQCSheet {

    public static void main(final String[] args) throws Exception {
        final Map<String, Map<String, List<String>>> parentMap = new HashMap<>();
        final List<String> lines = Files.readAllLines(Paths.get("./res/train_5500.label.txt"), Charset.forName("Cp1252"));
        for (final String line : lines) {
            final String[] classesSplit = line.substring(0, line.indexOf(' ')).split(":");
            final String parentClass = classesSplit[0];
            final String childClass = classesSplit[1];
            final String sentence = line.substring(line.indexOf(' ') + 1);

            final Map<String, List<String>> childMap = parentMap.getOrDefault(parentClass, new HashMap<>());
            final List<String> sentences = childMap.getOrDefault(childClass, new ArrayList<>());
            sentences.add(sentence);

            childMap.putIfAbsent(childClass, sentences);
            parentMap.putIfAbsent(parentClass, childMap);
        }

        int counter = 0;
        final Map<String, Integer> result = new HashMap<>();
        try (final Scanner scanner = new Scanner(System.in)) {
            String line;
            while ((line = scanner.nextLine()) != null) {
                final String mode = line.substring(0, line.indexOf(':'));
                final String term =  line.substring(line.indexOf(':') + 1);
                for (final Map.Entry<String, Map<String, List<String>>> parentEntry : parentMap.entrySet()) {
                    for (final Map.Entry<String, List<String>> childEntry : parentEntry.getValue().entrySet()) {
                        final String key = parentEntry.getKey() + "." + childEntry.getKey();
                        final int value = result.getOrDefault(key, 0) + 1; // FIXME: throws null pointer exception
                        for (String sentence : childEntry.getValue()) {
                            sentence = sentence.toLowerCase();
                            if ("start".equals(mode) ? sentence.startsWith(term) : "end".equals(mode)
                                    ? sentence.endsWith(term) : sentence.contains(term)) {
                                counter++;
                                result.put(key, value);
                            }
                        }
                    }
                }
                for (final Map.Entry<String, Integer> entry : result.entrySet()) {
                    if (entry.getValue() > 0) {
                        System.out.println(entry.getKey() + " probability of " + (((double) entry.getValue()) / counter));
                    }
                }
                counter = 0;
                result.clear();
            }
        }
    }
}
