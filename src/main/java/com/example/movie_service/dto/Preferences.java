package com.example.movie_service.dto;

import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public class Preferences {

    private List<String> genres;
    private List<String> actors;
    private List<String> directors;

    public Preferences(){}

    public Preferences(List<String> genres, List<String> actors, List<String> directors) {
        this.genres = genres;
        this.actors = actors;
        this.directors = directors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }
}
