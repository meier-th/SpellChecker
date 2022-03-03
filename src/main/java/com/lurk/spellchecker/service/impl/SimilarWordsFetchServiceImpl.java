package com.lurk.spellchecker.service.impl;

import com.lurk.spellchecker.repo.DictionaryRepository;
import com.lurk.spellchecker.service.SimilarWordsFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimilarWordsFetchServiceImpl implements SimilarWordsFetchService {

    private final DictionaryRepository repository;

    @Override
    public List<String> fetchSimilarWords(String word) {
        return null;
    }
}
