package com.example.moviereviewer.repository;

import com.example.moviereviewer.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository //Репозиторий, работающий с таблицей отзывов
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //Для упрощения поиска отзывов напишем простой sql запрос, куда будем передавать айди фильма
    @Query(nativeQuery = true, value = "SELECT * FROM review WHERE movie_id = :id")
    Page<Review> findAllByMovie(Pageable pageable, @Param("id") Long movieId);
}
