package es.mariaac.cinema.configuration;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.repositories.PeliculaRepository;
import org.apache.deltaspike.core.api.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

//@Configuration
public class LoaderDB {

/*    private static final Logger log = LoggerFactory.getLogger(LoaderDB.class);

    @Bean
    public CommandLineRunner initDatabase(PeliculaRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Pelicula("Drive my car", "Ryūsuke Hamaguchi", 180, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSdQfe69qSKksn3drVCLfT1b2ADMzDBfgJQWmX9Ni0wZLzVptof")));
            log.info("Preloading " + repository.save(new Pelicula("Belfast", "Kenneth Branagh", 93, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlZBF6Lwu7dcOKeYQk50lf5Z0hZvvq4YRyjDXVYTXLwir3CTIC")));
        };
    }*/
}