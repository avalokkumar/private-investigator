package com.assignment.sn.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.assignment.sn.util.Constant.SPACE;

public class InputValidator {

    @Value("${investigator.start.word.index:2}")
    private static int startingWordIndex;

    public static String sanitize(String line) {
        return line.trim().replaceAll(" +", SPACE);
    }

    public static boolean isValid(List<String> wordsInSentence) {
        return wordsInSentence.size() > startingWordIndex;
    }
}
