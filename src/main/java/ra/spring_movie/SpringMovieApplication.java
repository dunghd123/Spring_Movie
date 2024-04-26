package ra.spring_movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "ra.spring_movie.repository")
public class SpringMovieApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMovieApplication.class, args);
    }

}
