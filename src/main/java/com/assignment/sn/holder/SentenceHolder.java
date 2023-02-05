package com.assignment.sn.holder;

import com.assignment.sn.service.UUIDGenerator;
import com.assignment.sn.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.assignment.sn.util.Constant.SPACE;
public class SentenceHolder {

    private String sentenceId;
    private List<String> wordsInSentence;

    public SentenceHolder(UUIDGenerator uuidGenerator, String line) {
        this.wordsInSentence = Arrays.asList(line.split(SPACE));
        this.sentenceId = uuidGenerator.generateUUID(line);
    }

    public String getSentenceText() {
        return String.join(" ", wordsInSentence).trim();
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public boolean isValid() {
        return InputValidator.isValid(wordsInSentence);

    }

    public String getWordAtIndex(int index) {
        return wordsInSentence.get(index);
    }

    public List<String> getWordsInSentence() {
        return wordsInSentence;
    }
}
