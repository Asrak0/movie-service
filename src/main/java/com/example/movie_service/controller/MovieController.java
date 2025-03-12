package com.example.movie_service.controller;

import com.example.movie_service.dto.Preferences;
import com.example.movie_service.model.Movie;
import com.example.movie_service.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;



    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/{id}/recommendations")
    public List<Movie> getRecommendations(@PathVariable Long id) {
        Preferences userPref = movieService.fetchUserPreferences(id);
        return movieService.recommendMovies(userPref, 10);
    }
}
