package com.example.moviereviewer.service;

import com.example.moviereviewer.model.entity.Movie;
import com.example.moviereviewer.model.entity.Review;
import com.example.moviereviewer.model.request.ReviewRequest;
import com.example.moviereviewer.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service //Сервис, работающий с отзывами
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieService movieService;

    public ReviewService(ReviewRepository reviewRepository, MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.movieService = movieService;
    }

    public Page<Review> getAllReviews(Integer pageNumber, Integer size, Long movieId) {
        if (pageNumber < 0 || size < 1) {
            throw new IllegalArgumentException("Invalid page number or size");
        }
        return reviewRepository.findAllByMovie(PageRequest.of(pageNumber, size), movieId);
    }

    public void addReview(ReviewRequest request, Long movieId) {
        Movie movie = movieService.findById(movieId);
        reviewRepository.save(new Review(null, request.getReviewer(), request.getReviewText(), movie));
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Review not found")
        );
        reviewRepository.delete(review);
    }
}
