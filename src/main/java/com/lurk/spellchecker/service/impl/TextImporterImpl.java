package com.lurk.spellchecker.service.impl;

import com.lurk.spellchecker.repo.DictionaryRepository;
import com.lurk.spellchecker.repo.entity.Dictionary;
import com.lurk.spellchecker.service.JaccardService;
import com.lurk.spellchecker.service.TextImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TextImporterImpl implements TextImporter {

    private final Set<Dictionary> cache = new HashSet<>();

    private static int counter = 0;

    private final DictionaryRepository repository;
    private final JaccardService jaccardService;

    @Override
    public void importText(String path) throws IOException {
        Files.lines(Path.of(path))
                .forEach(this::processWords);
        flush();
    }

    private void processWords(String line) {
        counter++;
        if (counter == 100) {
            flush();
            counter = 0;
            cache.clear();
        }
        String[] words = line.split("[^\\p{IsAlphabetic}]");
        for (String word : words) {
            if (!word.isEmpty()) {
                //System.out.println(word.toLowerCase());
                word = word.toLowerCase();
                String key = jaccardService.calcCharSet(word);
                Dictionary dict = repository.findById(key).orElse(new Dictionary(key, new HashSet<>()));
                dict.getWords().add(word);
                cache.add(dict);
            }
        }
    }

    private void flush() {
        repository.saveAll(cache);
    }

}
