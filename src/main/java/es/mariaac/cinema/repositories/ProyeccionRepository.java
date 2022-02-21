package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Proyeccion;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;


@Repository
public abstract class ProyeccionRepository extends AbstractFullEntityRepository<Proyeccion, Long> {

    public abstract Proyeccion findById(String id);

    @Query("select p.dia, p.comienzo\n" +
            "from proyeccion p where \n" +
            "p.pelicula_id = IDDD\n" +
            "and p.dia > now( );")
    public abstract List<Proyeccion> findProyectando();

    public List<Proyeccion> findCriteriaProyectando() {
        return criteria()
//      .gtOrEq(Pregunta_.dificultad, 3)
                .getResultList();
    }
/*
sacar horas de proyeccion de un dia
select (p.comienzo) as proyecciones
from proyeccion p where p.pelicula_id = XXXXXXXXXXXXXXXXX
and p.dia = XXXXXXXXXXXXXXXX ;

* */

}