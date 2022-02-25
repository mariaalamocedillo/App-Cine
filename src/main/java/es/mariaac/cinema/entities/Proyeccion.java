package es.mariaac.cinema.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "proyeccion", uniqueConstraints = {
        @UniqueConstraint(name = "uc_sala_comienzo", columnNames = {"comienzo", "sala_id"})
})
public class Proyeccion {
    public Proyeccion(LocalTime comienzo, LocalDate dia, Sala sala, Pelicula pelicula) {
        this.comienzo = comienzo;
        this.dia = dia;
        this.sala = sala;
        this.pelicula = pelicula;
    }
    public Proyeccion(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonRawValue
    @Column(nullable = false)
    private LocalTime comienzo;

    @JsonRawValue
    @Column(nullable = false)
    private LocalDate dia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;


//    `equipo_limpieza_id` int NOT NULL,
//    UNIQUE INDEX `Projection_ak_1` (`auditorium_id`,`screening_start`),

}