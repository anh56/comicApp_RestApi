package com.example.comicApp.repository;

import com.example.comicApp.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByTitle(String title);

    @Query(value = "select * from Category c where c.title = ?1", nativeQuery = true)
    List<Chapter> findByTitleNativeQuery(String title);

    @Query(value = "select c from Category c where c.title = ?1", nativeQuery = false)
    List<Chapter> findByTitleJPQLQuery(String title);

}