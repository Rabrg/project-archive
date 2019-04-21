package com.gmail.rabrg96.qaqc;

import com.gmail.rabrg96.utils.MapUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class QAQCDataset {

    private final Map<String, List<String>> dataset = new HashMap<>();
    private final Map<String, Integer> sum = new HashMap<>();

    private final List<Statistic> statistics = new ArrayList<>();

    private int sentences;

    public static void main(final String[] args) throws IOException {
        final QAQCDataset dataset = new QAQCDataset();
        dataset.loadDataset("./res/train_all.label.txt");
        dataset.loadStatistics("./res/statistics-qaqc.tsv");
        dataset.checkAccuracy();
    }

    private void generateSaveStatistics(final String path) throws IOException {
        try (final Scanner scanner = new Scanner(System.in)) {
            String line;
            while ((line = scanner.nextLine()) != null && line.length() > 0) {
                final String[] split = line.split(":");
                addStatistics(split[0], "capital".equals(split[0]) ? Integer.parseInt(split[1]) : split[1]);
            }
        }
        saveStatistics(path);
    }

    private void checkAccuracy() {
        final Map<String, Integer> correct = new HashMap<>();
        final Map<String, Integer> total = new HashMap<>();
        int correctSum = 0, totalSum = 0;
        for (final Map.Entry<String, List<String>> entry : dataset.entrySet()) {
            for (final String sentence : entry.getValue()) {
                final Map<String, Double> probability = getSentenceProbability(sentence);
                if (probability.isEmpty())
                    continue;
                if (probability.entrySet().iterator().next().getKey().equals(entry.getKey())) {
                    correct.put(entry.getKey(), correct.getOrDefault(entry.getKey(), 0) + 1);
                    correctSum++;
                }
                total.put(entry.getKey(), total.getOrDefault(entry.getKey(), 0) + 1);
                totalSum++;
            }
        }
        for (final Map.Entry<String, Integer> entry : total.entrySet()) {
            System.out.println(entry.getKey() + "\t" + (((double) correct.getOrDefault(entry.getKey(), 0)) / entry.getValue()));
        }
        System.out.println("TOTAL\t" + ((double) correctSum) / totalSum);
    }

    public void loadDataset(final String path) throws IOException {
        clearDataset();

        final List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName("Cp1252"));
        for (final String line : lines) {
            final String[] classesSplit = line.substring(0, line.indexOf(' ')).split(":");
            final String key = classesSplit[0] + "." + classesSplit[1];
            final List<String> sentences = dataset.getOrDefault(key, new ArrayList<>());
            sentences.add(line.substring(line.indexOf(' ') + 1));
            dataset.putIfAbsent(key, sentences);
        }

        for (final Map.Entry<String, List<String>> entry : dataset.entrySet())
            sum.put(entry.getKey(), entry.getValue().size());

        sentences = lines.size();
        System.out.println("Loaded " + sentences + " sentences.");
    }

    public void loadStatistics(final String path) throws IOException {
        clearStatistics();

        final List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName("Cp1252"));
        for (final String line : lines) {
            final String[] split = line.split("\t");
            statistics.add(new Statistic(split[0], split[1], "capital".equals(split[1]) ? Integer.parseInt(split[2]) : split[2], Double.parseDouble(split[3])));
        }
        System.out.println("Loaded " + statistics.size() + " statistics.");
    }

    public void saveStatistics(final String path) throws IOException {
        try (final FileWriter writer = new FileWriter(path, true)) {
            for (final Statistic statistic : statistics)
                writer.write(statistic.toString() + "\n");
        }
        System.out.println("Saved " + statistics.size() + " statistics.");
    }

    private void clearDataset() {
        dataset.clear();
        sum.clear();
        sentences = 0;
    }

    private void clearStatistics() {
        statistics.clear();
    }

    public Map<String, Double> getSentenceProbability(final String sentence) {
        if (dataset.isEmpty() || statistics.isEmpty())
            throw new IllegalStateException("Dataset and statistics must both be loaded before get sentence probability");
        final Map<String, Double> probability = new HashMap<>();
        final Map<String, Integer> total = new HashMap<>();
        for (final Statistic statistic : statistics) {
            if (evaluate(sentence, statistic.getMode(), statistic.getArgument())) {
                final double prob = "capital".equals(statistic.getMode()) ? statistic.getProbability() * 0.0 : statistic.getProbability() * 1; // TODO: fix weight...
                probability.put(statistic.getKey(), probability.getOrDefault(statistic.getKey(), 0D) + prob);
                total.put(statistic.getKey(), total.getOrDefault(statistic.getKey(), 0) + 1);
            }
        }
//        for (final Map.Entry<String, Double> entry : probability.entrySet())
//            entry.setValue(entry.getValue() / total.get(entry.getKey()));
        return MapUtils.sortByValue(probability);
    }

    public void addStatistics(final String mode, final Object argument) {
        if (dataset.isEmpty())
            throw new IllegalStateException("Dataset must be loaded  before get mode probability");

        final Map<String, Double> probability = new HashMap<>();
        for (final Map.Entry<String, List<String>> entry : dataset.entrySet())
            for (final String sentence : entry.getValue())
                probability.put(entry.getKey(), probability.getOrDefault(entry.getKey(), 0D) + (evaluate(sentence, mode, argument) ? 1 : 0));

        for (final Map.Entry<String, Double> entry : probability.entrySet())
            if (entry.getValue() > 0)
                statistics.add(new Statistic(entry.getKey(), mode, argument, entry.getValue() / sum.get(entry.getKey())));
    }

    private boolean evaluate(final String sentence, final String mode, final Object argument) {
        if ("capital".equals(mode) && !(argument instanceof Integer) || "contain".equals(mode) && !(argument instanceof String))
            throw new IllegalArgumentException("Mode and argument mismatch [mode=" + mode + ", argument=" + argument + "]");

        switch (mode) {
            case "capital":
                final char[] chars = sentence.toCharArray();

                int count = 0;
                for (int i = 1; i < chars.length; i++)
                    if (Character.isUpperCase(chars[i]))
                        count++;
                return count == (int) argument;
            case "contain":
                return sentence.toLowerCase().contains((String) argument);
            default:
                throw new IllegalArgumentException("Unrecognized mode [mode=" + mode + ", argument=" + argument + "]");
        }
    }

    private static class Statistic {

        private final String key;
        private final String mode;
        private final Object argument;
        private final double probability;

        Statistic(final String key, final String mode, final Object argument, final double probability) {
            this.key = key;
            this.mode = mode;
            this.argument = argument;
            this.probability = probability;
        }

        String getKey() {
            return key;
        }

        String getMode() {
            return mode;
        }

        Object getArgument() {
            return argument;
        }

        double getProbability() {
            return probability;
        }

        @Override
        public String toString() {
            return key + "\t" + mode + "\t" + argument + "\t" + probability;
        }
    }
}
