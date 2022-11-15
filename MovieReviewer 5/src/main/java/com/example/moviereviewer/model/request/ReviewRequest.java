package com.example.moviereviewer.model.request;

import javax.validation.constraints.NotBlank;

//этот класс передаем в качестве тела запроса при добавлении отзыва
public class ReviewRequest {

    @NotBlank //Эта аннотация проверяет, чтобы поле не было пустым. В противном случае будет падать с ошибкой
    private String reviewer;

    @NotBlank
    private String reviewText;

    public ReviewRequest(String reviewer, String reviewText) {
        this.reviewer = reviewer;
        this.reviewText = reviewText;
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
}
