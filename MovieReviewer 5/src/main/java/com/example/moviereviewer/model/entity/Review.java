package com.example.moviereviewer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "review") //Сущность отзыва
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private String reviewer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reviewText;

    @ManyToOne(cascade = CascadeType.REFRESH) //т.к. есть связь многие-к-одному
    @JoinColumn(name = "movie_id") //по колонке movie_id он к сущности отзыва еще и цепляет сущность фильма
    @JsonIgnore //при выводе это поле не показываем
    private Movie movie;

    public Review() {}

    public Review(Long reviewId, String reviewer, String reviewText, Movie movie) {
        this.reviewId = reviewId;
        this.reviewer = reviewer;
        this.reviewText = reviewText;
        this.movie = movie;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
