package com.example.moviereviewer.repository;

import com.example.moviereviewer.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Репозиторий, который работает с сущностью фильма. Грубо говоря, за нас выполняет все необходимые sql запросы
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
