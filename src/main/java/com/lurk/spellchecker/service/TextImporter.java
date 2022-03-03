package com.lurk.spellchecker.service;

import java.io.IOException;

public interface TextImporter {

    void importText(String path) throws IOException;

}
