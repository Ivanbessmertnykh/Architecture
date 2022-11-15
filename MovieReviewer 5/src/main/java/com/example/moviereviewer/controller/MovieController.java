package com.example.moviereviewer.controller;

import com.example.moviereviewer.model.request.ReviewRequest;
import com.example.moviereviewer.service.MovieService;
import com.example.moviereviewer.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController //Контроллер для фильмов
@RequestMapping("/movie") //Сюда прилетают запросы по адресу http://localhost:8080/movie/и далее
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    //Параметры запросы передаются так ?pageNumber={ваше значение}
    @GetMapping()
    public ResponseEntity<?> getAllMovies(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size) {
        //Нумерация страниц у jpa репозиториев c нуля, поэтому минус 1
        return ResponseEntity.ok(movieService.getAllMovies(pageNumber - 1, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getReviewsOnMovie(
            @PathVariable("id") Long id,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size) {
        return ResponseEntity.ok(reviewService.getAllReviews(pageNumber - 1, size, id));
    }

    @PostMapping("/{id}/add-review")
    public ResponseEntity<?> addReviewToMovie(@PathVariable("id") Long id, @RequestBody @Valid ReviewRequest request) {
        //Аннотация valid позволяет валидировать объекты до выполнения ниже написанных методов
        reviewService.addReview(request, id);
        return ResponseEntity.status(CREATED).build();
    }
}
