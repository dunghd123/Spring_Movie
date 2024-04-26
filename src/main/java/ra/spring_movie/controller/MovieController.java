package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Movie;
import ra.spring_movie.model.MovieCustom;
import ra.spring_movie.services.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("insertmovie")
    public ResponseEntity<MessageResponse> insertMovie(@RequestBody MovieCustom movie){
        return new ResponseEntity<>(movieService.insertMovie(movie), HttpStatus.CREATED);
    }
    @PutMapping("updatemovie/{id}")
    public ResponseEntity<MessageResponse> updateMovie(@PathVariable int id,@RequestBody MovieCustom movie){
        return new ResponseEntity<>(movieService.updateMovie(id,movie), HttpStatus.OK);
    }
    @DeleteMapping("deleteMovie/{id}")
    public ResponseEntity<MessageResponse> deleteMovie(@PathVariable int id){
        return new ResponseEntity<>(movieService.deleteMovie(id), HttpStatus.OK);
    }
    @GetMapping("viewMovie")
    public List<Movie> viewMovie(){
        return movieService.viewMovie();
    }
}
