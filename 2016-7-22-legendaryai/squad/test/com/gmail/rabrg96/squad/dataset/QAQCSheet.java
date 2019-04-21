package com.gmail.rabrg96.squad.dataset;

import com.gmail.rabrg96.utils.MapUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class QAQCSheet {

    // TODO: algorithm to automatically weight statistics
    public static void main(final String[] args) throws Exception {
        final Map<String, List<String>> dataset = loadDataset();
//        inputStatistics(dataset);
        final List<String> statistics = Files.readAllLines(Paths.get("./res/statistics-qaqc.tsv"));

        int correct = 0, total = 0, empty = 0;
        for (final Map.Entry<String, List<String>> entry : dataset.entrySet()) {
            for (final String sentence : entry.getValue()) {
                final Map<String, Double> probability = getProbabilityStatistics(statistics, sentence, 1);
                if (probability.isEmpty()) {
                    total++;
                    empty++;
                    continue;
                }
                final String detected = probability.entrySet().iterator().next().getKey();
                if (!probability.isEmpty() && detected.equals(entry.getKey())) {
                    correct++;
                } else {
                    System.out.println(detected + "\t" + entry.getKey() + "\t" + sentence);
                }
                total++;
            }
        }
        System.out.println(((double) correct) / total + " "+  empty);
    }

    // TODO: flatten input usage into <parent.child> key map
    private static Map<String, List<String>> loadDataset() throws IOException {
        final Map<String, List<String>> parentMap = new HashMap<>();
        final List<String> lines = Files.readAllLines(Paths.get("./res/train_1000.label.txt"), Charset.forName("Cp1252"));
        for (final String line : lines) {
            final String[] classesSplit = line.substring(0, line.indexOf(' ')).split(":");
            final String parentClass = classesSplit[0];
            final String childClass = classesSplit[1];
            final String sentence = line.substring(line.indexOf(' ') + 1);

            final List<String> sentences = parentMap.getOrDefault(parentClass + "." + childClass, new ArrayList<>());
            sentences.add(sentence.toLowerCase());
            parentMap.putIfAbsent(parentClass + "." + childClass, sentences);
        }
        return parentMap;
    }

    private static void inputStatistics(final Map<String, List<String>> parentMap)  throws IOException {
        int counter = 0;
        final Map<String, Integer> result = new HashMap<>();
        final List<String> previous = new ArrayList<>();
        try (final Scanner scanner = new Scanner(System.in);
             final FileWriter writer = new FileWriter("./res/statistics-qaqc.tsv", true)) {
            String line;
            while ((line = scanner.nextLine()) != null && line.length() > 0) {
                if (previous.contains(line))
                    continue;
                previous.add(line);
                final String mode = line.substring(0, line.indexOf(':'));
                final String term =  line.substring(line.indexOf(':') + 1);
                for (final Map.Entry<String, List<String>> parentEntry : parentMap.entrySet()) {
                        final String key = parentEntry.getKey();
                        for (String sentence : parentEntry.getValue()) {
                            sentence = sentence.toLowerCase();
                            if ("start".equals(mode) && sentence.startsWith(term)
                                    || "end".equals(mode) && sentence.endsWith(term)
                                    || "contain".equals(mode) && sentence.contains(term)) {
                                result.put(key, result.getOrDefault(key, 0) + 1);
                                counter++;
                            }
                    }
                }
                for (final Map.Entry<String, Integer> entry : result.entrySet()) {
                    writer.write(entry.getKey() + "\t"
                            + truncate(entry.getValue() / (double) counter) + "\t" + mode + "\t" + term + "\n");
                }
                writer.flush();
                counter = 0;
                result.clear();
            }
        }
    }

    private static double truncate(final double i) {
        return Math.floor(i * 1000) / 1000;
    }

    private static Map<String, Double> getProbabilityStatistics(final List<String> statistics, String sentence, final double setWeight) {
        sentence = sentence.toLowerCase();

        final Map<String, Double> probability = new HashMap<>();
        for (final String statistic : statistics) {
            final String[] split = statistic.split("\t");
            final String key = split[0];
            final double setProbability = Double.parseDouble(split[1]);
            final String mode = split[2];
            final String term = split[3];
            if ("start".equals(mode) && sentence.startsWith(term)
                    || "end".equals(mode) && sentence.endsWith(term)
                    || "contain".equals(mode) && sentence.contains(term)) {
                probability.put(key, probability.getOrDefault(key, 0D) + (setProbability * setWeight));
            }
        }
        return MapUtils.sortByValue(probability);
    }
}
