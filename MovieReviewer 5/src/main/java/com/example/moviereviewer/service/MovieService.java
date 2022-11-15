package com.example.moviereviewer.service;

import com.example.moviereviewer.model.entity.Movie;
import com.example.moviereviewer.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service //Сервис, работающий с фильмами
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //Page позволяет выводить объекты по страницам
    public Page<Movie> getAllMovies(Integer pageNumber, Integer size) {
        if (pageNumber < 0 || size < 1) {
            throw new IllegalArgumentException("Invalid page number or size");
        }

        //В отличие от обычного findAll мы еще передаем объект Pageable, для отображения по страницам
        return movieRepository.findAll(PageRequest.of(pageNumber, size));
    }

    public Movie findById(Long id) {
        //Тут нам из репозитория прилетает Optional - этакий объект, который если не пустой, то мы можем получить наш объект
        //А если пустой, то можем обработать этот случай по нашему желанию (мы будем выкидывать ошибку)
        return movieRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Movie not found")
        );
    }
}
