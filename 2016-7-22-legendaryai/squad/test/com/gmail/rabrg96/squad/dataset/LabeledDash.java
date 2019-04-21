package com.gmail.rabrg96.squad.dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

final class LabeledDash {

    private static final char[] SUBTRACT_KEYWORDS = { '+', '*', '/' };
    private static final String[] SCORE_KEYWORDS = { "defeated", "record" };
    private static final String[] DATE_RANGE_KEYWORDS = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December", "centur" };
    private static final char[] MONEY_KEYWORDS = { '$', '€', '¥', '£' };

    public static void main(final String[] args) throws IOException {
        for (final Data data : loadData()) {
            if (data.label.contains("NAME")) // ignore name
                continue;

            final String text = data.text;
            if (text.contains("-") || text.contains("–")) {
                final String noWhitespace = text.replaceAll("\\s+", "");
                final int index = noWhitespace.indexOf(text.contains("–") ? '–' : '-');
                final String within3 = noWhitespace.substring(index - 3, index + 3);
                final String within6 = noWhitespace.substring(Math.max(index - 6, 0), Math.min(index + 6, noWhitespace.length()));
                final char beforeChar = noWhitespace.charAt(index - 1);
                final char afterChar = noWhitespace.charAt(index + 1);
                String detected = "NULL";

                // NUMBER_RANGE
                if (Character.isDigit(beforeChar) && Character.isDigit(afterChar))
                    detected = "NUMBER_RANGE";

                // SCORE
                int count = 0;
                for (final String keyword : SCORE_KEYWORDS)
                    if (text.contains(keyword))
                        count++;
                if (count > 0 && Character.isDigit(beforeChar) && Character.isDigit(afterChar))
                    detected = "SCORE";

                // DATE_RANGE
                int before = 0, after = 0, keywords = 0;
                for (int i = 0; i < within6.length(); i++)
                    if (Character.isDigit(within6.charAt(i)))
                        if (i > 3)
                            after++;
                        else
                            before++;
                for (final String keyword : DATE_RANGE_KEYWORDS)
                    if (text.contains(keyword))
                        keywords++;
                if ((before + after >= 5 || keywords > 0 || within3.contains("mid") || within3.contains("pre")) && !within6.contains("around"))
                    detected = "DATE_RANGE";

                // UNIT
                if (Character.isDigit(beforeChar) && Character.isAlphabetic(afterChar))
                    detected = "UNIT";

                // PAGE_RANGE
                if (within6.contains(":"))
                    detected = "PAGE_RANGE";

                // TIME_RANGE
                if (within6.contains("PM") || within6.contains("AM"))
                    detected = "TIME_RANGE";

                // MONEY_RANGE
                count = 0;
                for (final char c : MONEY_KEYWORDS)
                    if (within6.contains("" + c))
                        count++;
                if (count > 0)
                    detected = "MONEY_RANGE";

                // SUBTRACT
                count = 0;
                for (final char c : within3.toCharArray())
                    for (final char symbol : SUBTRACT_KEYWORDS)
                        if (c == symbol)
                            count++;
                if (count > 0)
                    detected = "SUBTRACT";

                System.out.print(data);
                // Look for error
                if (data.label.contains("UNIT") && !detected.contains("UNIT")
                        || data.label.contains("SUBTRACT")&& !detected.contains("SUBTRACT")
                        || data.label.contains("SCORE") && !detected.contains("SCORE")
                        || data.label.contains("PAGE_RANGE") && !detected.contains("PAGE_RANGE")
                        || data.label.contains("DATE_RANGE") && !detected.contains("DATE_RANGE")
                        || data.label.contains("TIME_RANGE") && !detected.contains("TIME_RANGE")
                        || data.label.contains("MONEY_RANGE") && !detected.contains("MONEY_RANGE")
                        || data.label.contains("NUMBER_RANGE") && !detected.contains("NUMBER_RANGE"))
                    System.out.print("\t" + detected);
                System.out.println();
            }
        }
    }

    private static final List<Data> loadData() throws IOException {
        final List<Data> data = new ArrayList<>();
        for (final String line : Files.readAllLines(Paths.get("./res/labeled-dash.tsv"))) {
            final String[] split = line.split("\t");
            data.add(new Data(split[0], split[1]));
        }
        return data;
    }

    private static class Data {
        private final String text;
        private final String label;

        public Data(String text, String label) {
            this.text = text;
            this.label = label;
        }

        @Override
        public String toString() {
            return text + "\t" + label;
        }
    }
}
