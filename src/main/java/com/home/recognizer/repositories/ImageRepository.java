package com.home.recognizer.repositories;

import com.home.recognizer.entities.Image;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter}")
    List<Image> findAll();
}
