package com.mycopany.apisparkmovies.dao;

/**
 *
 * @author mauro
 */

import java.util.List;


import com.mycompany.apisparkmovies.model.Movie;
public interface MoviesRespository {
        Movie addMovie(final Movie movie);

	void deleteMovie(final Long id);

	Movie updateMovie(final Movie movie);

	Movie getMovie(final Long id);
        
        Movie getMovietitle(final String title);

	List<Movie> getMovies();
}
