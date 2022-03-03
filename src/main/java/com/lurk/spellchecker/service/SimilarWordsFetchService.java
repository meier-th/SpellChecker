package com.lurk.spellchecker.service;

import java.util.List;

public interface SimilarWordsFetchService {

    List<String> fetchSimilarWords(String word);

}
