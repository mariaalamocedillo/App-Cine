package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "proyeccion", uniqueConstraints = {
        @UniqueConstraint(name = "uc_sala_comienzo", columnNames = {"comienzo", "sala_id"})
})
public class Proyeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalTime comienzo;

    @Column(nullable = false)
    private LocalDate dia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;


//    `equipo_limpieza_id` int NOT NULL,
//    UNIQUE INDEX `Projection_ak_1` (`auditorium_id`,`screening_start`),

}