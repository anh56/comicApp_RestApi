package com.example.comicApp.repository;

import com.example.comicApp.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {

}