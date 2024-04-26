package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.spring_movie.dto.response.ListMovieResponse;
import ra.spring_movie.services.impl.ViewMovieImpl;

import java.util.List;

@RestController
@RequestMapping("/sortmoviebyticket/")
public class SortMovieController {
    @Autowired
    private ViewMovieImpl viewMovie;
    @GetMapping("displaymovie")
    public List<ListMovieResponse> displayMovie(){
        return viewMovie.printList();
    }
}
