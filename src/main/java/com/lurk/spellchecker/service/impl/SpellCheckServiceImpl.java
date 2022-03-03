package com.lurk.spellchecker.service.impl;

import com.lurk.spellchecker.repo.DictionaryRepository;
import com.lurk.spellchecker.repo.entity.Dictionary;
import com.lurk.spellchecker.service.SpellCheckService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpellCheckServiceImpl implements SpellCheckService {

    private final static double threshold = 0.2;
    private final static Set<String> SINGLE_LETTER_WORDS = Set.of("и", "а");

    private final DictionaryRepository repository;
    private final JaccardDistance jaccardDistance = new JaccardDistance();
    private final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
    private List<Dictionary> dictionary;

    @PostConstruct
    private void init() {
        dictionary = repository.findAll();
    }

    @Override
    public String getSpellCheckedText(String originalText) {
        StringBuilder builder = new StringBuilder();
        String[] words = originalText.split("[^a-zA-Zа-яА-Я]+", Pattern.UNICODE_CHARACTER_CLASS);
        for (String word : words) {
            if (word.length() == 0) {
                continue;
            }
            if (word.length() == 1) {
                if (SINGLE_LETTER_WORDS.contains(word.toLowerCase())) {
                    builder.append(word).append(' ');
                } else {
                    builder.append(String.format("%s (Perhaps you meant а/и) ", word));
                }
            } else {
                String lowerCaseWord = word.toLowerCase();
                List<String> closestWords = findClosestWords(lowerCaseWord);

                Optional<String> option = closestWords.stream().max((str1, str2) -> {
                    double firstSimilarity = jaroWinklerDistance.apply(lowerCaseWord, str1);
                    double secondSimilarity = jaroWinklerDistance.apply(lowerCaseWord, str2);
                    if (firstSimilarity >= secondSimilarity) {
                        if (firstSimilarity == secondSimilarity) {
                            return 0;
                        }
                        return 1;
                    }
                    return -1;
                });
                if (option.isPresent() && jaroWinklerDistance.apply(lowerCaseWord, option.get()) != 1.0) {
                    builder.append(String.format("%s (Perhaps you meant %s) ", word, option.get()));
                } else if (option.isPresent()) {
                    builder.append(word).append(' ');
                } else {
                    builder.append(String.format("'%s' (Not found in dictionary) ", word));
                }
            }
        }
        return builder.toString();
    }

    private List<String> findClosestWords(String word) {
        return dictionary
                .stream()
                .filter(entry -> jaccardDistance.apply(word, entry.getCharSet()) <= threshold)
                .flatMap(entry -> entry.getWords().stream())
                .collect(Collectors.toList());
    }

}
