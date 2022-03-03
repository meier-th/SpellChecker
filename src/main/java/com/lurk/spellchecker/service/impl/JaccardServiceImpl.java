package com.lurk.spellchecker.service.impl;

import com.lurk.spellchecker.service.JaccardService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class JaccardServiceImpl implements JaccardService {

    @Override
    public String calcCharSet(String word) {
        Set<Character> chars = new HashSet<>();
        for (char ch : word.toCharArray()) {
            chars.add(ch);
        }

        StringBuilder builder = new StringBuilder();

        for (Character aChar : chars) {
            builder.append(aChar);
        }

        String str = builder.toString();
        char[] chrs = str.toCharArray();
        Arrays.sort(chrs);

        return String.copyValueOf(chrs);
    }
}
