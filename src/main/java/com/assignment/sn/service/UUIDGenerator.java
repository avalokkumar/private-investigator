package com.assignment.sn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

import static com.assignment.sn.util.Constant.SPACE;

@Component
public class UUIDGenerator {

    @Autowired
    private MessageDigest messageDigest;

    @Value("${investigator.start.word.index:2}")
    private int startingWordIndex;

    public String generateUUID(String input) {
        byte[] hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        return UUID.nameUUIDFromBytes(hash).toString();
    }

    public String generateUniqueUUIDKey(List<String> wordsInSentence, int skippedWordIndex) {
        StringBuilder subStr = new StringBuilder();

        for (int i = startingWordIndex; i < wordsInSentence.size(); i++) {
            if (i == skippedWordIndex) {
                continue;
            }
            subStr.append(wordsInSentence.get(i)).append(SPACE);
        }

        return generateUUID(subStr.toString());
    }
}
