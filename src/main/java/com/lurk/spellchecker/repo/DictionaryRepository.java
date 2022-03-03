package com.lurk.spellchecker.repo;

import com.lurk.spellchecker.repo.entity.Dictionary;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends MongoRepository<Dictionary, String> {
/*
    @Query(fields="{ charSet : 0 }")
    List<DictionaryRepository> findAllIds();
*/
}
