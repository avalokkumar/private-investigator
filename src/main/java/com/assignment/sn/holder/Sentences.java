package com.assignment.sn.holder;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Component
public class Sentences {
    HashMap<String, SentenceHolder> sentences;

    public Sentences() {
        sentences = new HashMap<>();
    }

    public void add(SentenceHolder sentence) {
        sentences.put(sentence.getSentenceId(), sentence);
    }

    public boolean existByUUID(String uuid){
        if (!StringUtils.hasText(uuid)) {
            return false;
        }
        return sentences.containsKey(uuid);
    }

    public SentenceHolder getSentenceByUUID(String uuid){
        return sentences.get(uuid);
    }
}
