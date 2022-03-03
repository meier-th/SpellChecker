package com.lurk.spellchecker.controller;

import com.lurk.spellchecker.service.SpellCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpellCheckController {

    private final SpellCheckService spellCheckService;

    @PostMapping("/check")
    public String checkSpell(@RequestBody String text) {
        return spellCheckService.getSpellCheckedText(text);
    }

}
