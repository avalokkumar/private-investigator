package com.assignment.sn.holder;

import com.assignment.sn.util.CustomFunction;
import com.assignment.sn.util.Pairs;
import com.assignment.sn.util.ResultFormatter;
import com.assignment.sn.writer.Writer;
import com.assignment.sn.service.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.assignment.sn.util.Constant.*;

@Slf4j
@Component
public class InvestigationResultHolder {

    @Value("${investigator.start.word.index:2}")
    private int startingWordIndex;

    @Autowired
    private UUIDGenerator uuidService;

    @Autowired
    private Writer textWriter;

    @Autowired
    private ResultFormatter resultFormatter;

    private HashMap<String, Set<String>> similarSentenceMapping;
    private Set<String> similarSentences;

    @PostConstruct
    public void init() {
        similarSentenceMapping = new LinkedHashMap<>();
        similarSentences = new LinkedHashSet<>();
    }

    /**
     * This method checks for all the possible combination of words in a sentence and
     * assign the mapping of sentence combination unique key with sentence id
     *
     * @param sentence
     */
    public void check(SentenceHolder sentence) {

        for (int skippedWordIndex = startingWordIndex; skippedWordIndex < sentence.getWordsInSentence().size(); skippedWordIndex++) {
            String sentenceWithSkippedWordKey = uuidService.generateUniqueUUIDKey(sentence.getWordsInSentence(), skippedWordIndex);
            String similarSentenceMapKey = sentenceWithSkippedWordKey + SEPARATOR + skippedWordIndex;
            addToSimilarSentenceMap.accept(similarSentenceMapKey, sentence.getSentenceId());
        }
    }

    /**
     * Method to write the result in an output file
     *
     * @param outputFilePath
     * @param sentences
     */
    public void writeResults(String outputFilePath, Sentences sentences) {
        String finalResult = resultFormatter.format(getResults(sentences));
        log.info("\n\n{}", finalResult);
        textWriter.write(outputFilePath, finalResult);
    }

    /**
     * BiFunction to create the mapping of similar sentence with sentence id.
     */
    private final BiConsumer<String, String> addToSimilarSentenceMap = (similarSentenceMapKey, sentenceID) -> {
        if (similarSentenceMapping.containsKey(similarSentenceMapKey)) {
            similarSentenceMapping.get(similarSentenceMapKey).add(sentenceID);
            similarSentences.add(similarSentenceMapKey);
        } else {
            Set<String> matchingSentenceIds = new LinkedHashSet<>();
            matchingSentenceIds.add(sentenceID);
            similarSentenceMapping.put(similarSentenceMapKey, matchingSentenceIds);
        }
    };

    private List<Pairs<List<String>, List<String>>> getResults(Sentences sentences) {

        return similarSentences.stream()
                .map(key -> getMatchingSentences.apply(key.split(SEPARATOR_REGEX)[1], similarSentenceMapping.get(key), sentences))
                .collect(Collectors.toList());
    }

    private final CustomFunction<String, Set<String>, Sentences, Pairs<List<String>, List<String>>> getMatchingSentences =
            (changingWordIndex, sentencesToGet, allSentences) -> {
                List<String> matchingSentences = new ArrayList<>();
                List<String> changingWords = new ArrayList<>();
                Pairs<List<String>, List<String>> matchingPairs = new Pairs<>(matchingSentences, changingWords);

                sentencesToGet.forEach(id -> {
                    SentenceHolder sentence = allSentences.getSentenceByUUID(id);
                    matchingSentences.add(sentence.getSentenceText());
                    changingWords.add(sentence.getWordAtIndex(Integer.parseInt(changingWordIndex)));
                });

                return matchingPairs;
            };
}
