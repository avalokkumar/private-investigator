package com.assignment.sn.util;

import org.springframework.stereotype.Component;

import java.util.List;

import static com.assignment.sn.util.Constant.*;

@Component
public class InvestigationResultFormatter implements ResultFormatter {

    @Override
    public String format(List<Pairs<List<String>, List<String>>> input) {
        StringBuilder resultBuilder = new StringBuilder();

        input.forEach( pairs -> {
            List<String> sentences = pairs.getFirst();
            sentences.forEach(sentence -> resultBuilder.append(sentence).append(NEW_LINE));
            resultBuilder
                    .append(LABEL_CHANGING_WORD)
                    .append(NEW_TAB_SPACE)
                    .append(pairs.getSecond())
                    .append(NEW_LINE)
                    .append(NEW_LINE);
        });

        return resultBuilder.toString();
    }
}
