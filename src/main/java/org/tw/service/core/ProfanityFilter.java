package org.tw.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tw.domain.SanitizedText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfanityFilter {

    private static Logger logger = LoggerFactory.getLogger(ProfanityFilter.class);

    private Map<String, String[]> allBadWords = new HashMap<>();
    private static int largestWordLength = 0;

    public ProfanityFilter() {
        loadBadWords();
    }


    public SanitizedText getCensoredText(final String input) {

        if (StringUtils.isAllBlank(input)) {
            return null;
        }

        String modifiedInput = input;

        modifiedInput = modifiedInput.replaceAll("1", "i").replaceAll("!", "i").replaceAll("3", "e").replaceAll("4", "a")
                .replaceAll("@", "a").replaceAll("5", "s").replaceAll("7", "t").replaceAll("0", "o").replaceAll("9", "g");

        modifiedInput = modifiedInput.toLowerCase().replaceAll("[^a-zA-Z]", "");

        ArrayList<String> badWordsFound = new ArrayList<>();

        for (int start = 0; start < modifiedInput.length(); start++) {

            for (int offset = 1; offset < (modifiedInput.length() + 1 - start) && offset < largestWordLength; offset++) {
                String wordToCheck = modifiedInput.substring(start, start + offset);
                if (allBadWords.containsKey(wordToCheck)) {
                    String[] ignoreCheck = allBadWords.get(wordToCheck);
                    boolean ignore = false;
                    for (int stringIndex = 0; stringIndex < ignoreCheck.length; stringIndex++) {
                        if (modifiedInput.contains(ignoreCheck[stringIndex])) {
                            ignore = true;
                            break;
                        }
                    }

                    if (!ignore) {
                        badWordsFound.add(wordToCheck);
                    }
                }
            }
        }

        String inputToReturn = input;
        for (String swearWord : badWordsFound) {
            char[] charsStars = new char[swearWord.length()];
            Arrays.fill(charsStars, '*');
            final String stars = new String(charsStars);

            inputToReturn = inputToReturn.replaceAll("(?i)" + swearWord, stars);
        }

        return new SanitizedText(inputToReturn, badWordsFound);
    }

    private void loadBadWords() {
        int readCounter = 0;

        try {
            FileReader fr = new FileReader("models/wordfilter.csv");
            BufferedReader reader = new BufferedReader(fr);
            String currentLine = "";
            while ((currentLine = reader.readLine()) != null) {
                readCounter++;
                String[] content = null;
                try {
                    if (1 == readCounter) {
                        continue;
                    }

                    content = currentLine.split(",");
                    if (content.length == 0) {
                        continue;
                    }

                    final String word = content[0];

                    if (word.startsWith("-----")) {
                        continue;
                    }

                    if (word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }

                    String[] ignore_in_combination_with_words = new String[]{};
                    if (content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    allBadWords.put(word.replaceAll(" ", "").toLowerCase(), ignore_in_combination_with_words);
                } catch (Exception except) {

                    logger.error("ERROR", except);
                }
            }
        } catch (IOException except) {

            logger.error("ERROR", except);
        } finally {
            logger.info("bad words Load completed");
        }
    }

}