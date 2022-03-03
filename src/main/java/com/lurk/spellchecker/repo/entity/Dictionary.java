package com.lurk.spellchecker.repo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.Set;

@Document("dictionary")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Dictionary {

    @Id
    private String charSet;
    private Set<String> words;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(charSet, that.charSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(charSet);
    }
}
