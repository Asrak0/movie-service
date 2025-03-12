package com.example.movie_service.service;

import com.example.movie_service.dto.Preferences;
import com.example.movie_service.model.Movie;
import com.example.movie_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RestTemplate restTemplate;

    //    Movie recommendation logic - ***TO BE MOVED TO MOVIE RECOMMENDATION SERVICE LAYER***

    public Preferences fetchUserPreferences(Long userId){
        String userServiceUrl = "http://localhost:8080/users/" + userId + "/preferences";
        return restTemplate.getForObject(userServiceUrl, Preferences.class);
    }

    public List<Movie> recommendMovies(Preferences preferences, int topN){
        List<Movie> allMovies = movieRepository.findAll();
        return allMovies.stream()
                .sorted((m1, m2) -> Double.compare(
                        calculateSimilarity(m2, preferences),
                        calculateSimilarity(m1, preferences)
                ))
                .limit(topN)
                .collect(Collectors.toList());
    }

    public double calculateSimilarity(Movie movie, Preferences preferences){
        double genreSimilarity = calculateJacquardSimilarity(movie.getGenres(), preferences.getGenres());
        double actorSimilarity = calculateJacquardSimilarity(movie.getActors(), preferences.getActors());
        double directorSimilarity = calculateJacquardSimilarity(movie.getDirectors(), preferences.getDirectors());

        // Weighted average of similarity scores
        return 0.5 * genreSimilarity + 0.3 * actorSimilarity + 0.2 * directorSimilarity;
    }

    public double calculateJacquardSimilarity(List<String> list1, List<String> list2){
        Set<String> union = new HashSet<>(list1);
        union.addAll(list2);

        Set<String> intersection = new HashSet<>(list1);
        intersection.retainAll(list2);

        return (double) intersection.size() / union.size();
    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id){
        return movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Movie not found"));
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }

}
