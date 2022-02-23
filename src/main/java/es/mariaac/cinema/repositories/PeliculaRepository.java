package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Pelicula;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import java.util.List;

@Repository
public abstract class PeliculaRepository extends AbstractFullEntityRepository<Pelicula, Long> {

    public abstract Pelicula findById(String id);

    @Query("select p from Pelicula p where enProyeccion = true")
    public abstract List<Pelicula> findProyectando();

    public List<Pelicula> findCriteriaProyectando() {
        return criteria()
//      .gtOrEq(Pregunta_.dificultad, 3)
      .getResultList();
    }

}