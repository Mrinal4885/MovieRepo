package com.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.entity.Movie;
import com.movie.repository.MovieRepository;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

	@Autowired
	private MovieRepository MovieRepository;

	// get all Movies
	@GetMapping
	public List<Movie> getAllMovies() {
		return this.MovieRepository.findAll();
	}

	// get Movie by id
	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable(value = "id") long MovieId) throws Exception {
		Movie movie = null;
		try {
			movie = this.MovieRepository.findById(MovieId);
		} catch (Exception e) {
			throw new Exception("Movie not found with id :" + MovieId);
		}
		return movie;
	}

	// create Movie
	@PostMapping
	public ResponseEntity<Object> createMovie(@RequestBody Movie Movie) {
		Object response = null;
		response = this.MovieRepository.save(Movie);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// update Movie
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateMovie(@RequestBody Movie Movie, @PathVariable("id") long MovieId)
			throws Exception {
		Object response = null;
		Movie existingMovie = null;
		try {
			existingMovie = this.MovieRepository.findById(MovieId);
			existingMovie.setTitle(Movie.getTitle());
			existingMovie.setCategory(Movie.getCategory());
			existingMovie.setRating(Movie.getRating());
			response = this.MovieRepository.save(existingMovie);
		} catch (Exception e) {
			throw new Exception("Movie not found with id :" + MovieId);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// delete Movie by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable("id") long MovieId) throws Exception {
		Movie existingMovie = null;
		try {
			existingMovie = this.MovieRepository.findById(MovieId);
			this.MovieRepository.delete(existingMovie);
		} catch (Exception e) {
			throw new Exception("Movie not found with id :" + MovieId);
		}
		return ResponseEntity.ok().build();
	}

	public void info(){
		System.out.println("edit the content");
	}

}
