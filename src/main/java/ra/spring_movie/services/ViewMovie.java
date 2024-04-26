package ra.spring_movie.services;

import ra.spring_movie.dto.response.ListMovieResponse;

import java.util.List;

public interface ViewMovie {

    List<ListMovieResponse> printList();
}
