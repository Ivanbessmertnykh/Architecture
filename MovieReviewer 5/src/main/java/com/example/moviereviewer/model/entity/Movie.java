package com.example.moviereviewer.model.entity;

import javax.persistence.*;
import java.time.LocalTime;

@Entity //Сущность фильма
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Эта аннотация говорит, что айди при сохранении нового объекта будет генерироваться автоматически (путем инкрементации)
    private Long movieId;

    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    public Movie(Long movieId, LocalTime duration, String title, String description) {
        this.movieId = movieId;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    public Movie() {}

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
