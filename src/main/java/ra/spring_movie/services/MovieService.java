package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Movie;
import ra.spring_movie.model.MovieCustom;

import java.util.List;

public interface MovieService {
     MessageResponse insertMovie(MovieCustom movie);
     MessageResponse updateMovie(int id,MovieCustom movie);
     MessageResponse deleteMovie(int movieid);
     List<Movie> viewMovie();
}
