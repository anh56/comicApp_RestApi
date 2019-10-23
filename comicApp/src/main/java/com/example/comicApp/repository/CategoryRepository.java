package com.example.comicApp.repository;

import com.example.comicApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByTitle(String title);

    @Query(value = "select * from Category c where c.title = ?1", nativeQuery = true)
    List<Category> findByTitleNativeQuery(String title);

    @Query(value = "select c from Category c where c.title = ?1", nativeQuery = false)
    List<Category> findByTitleJPQLQuery(String title);
}
