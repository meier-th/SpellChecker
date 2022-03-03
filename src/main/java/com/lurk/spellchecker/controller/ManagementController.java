package com.lurk.spellchecker.controller;

import com.lurk.spellchecker.service.TextImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ManagementController {

    private final TextImporter importer;

    @GetMapping("/import")
    public void initImport() throws IOException {
        importer.importText("articlesln.txt");
    }

}
