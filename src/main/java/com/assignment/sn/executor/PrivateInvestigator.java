package com.assignment.sn.executor;

import com.assignment.sn.holder.InvestigationResultHolder;
import com.assignment.sn.holder.SentenceHolder;
import com.assignment.sn.holder.Sentences;
import com.assignment.sn.service.UUIDGenerator;
import com.assignment.sn.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrivateInvestigator {

    @Autowired
    private InvestigationResultHolder investigationResult;

    @Autowired
    private Sentences allSentences;

    @Autowired
    private UUIDGenerator uuidGenerator;

    public void initiateInvestigation(List<String> sentences) {

        sentences.forEach(inputLine -> {
            String sanitizedInput = InputValidator.sanitize(inputLine);
            SentenceHolder sentenceHolder = new SentenceHolder(uuidGenerator, sanitizedInput);

            if (sentenceHolder.isValid() && !allSentences.existByUUID(sentenceHolder.getSentenceId())) {
                investigationResult.check(sentenceHolder);
                allSentences.add(sentenceHolder);
            }
        });
    }

    public void writeResults(String outputFilePath) {
        investigationResult.writeResults(outputFilePath, allSentences);
    }
}