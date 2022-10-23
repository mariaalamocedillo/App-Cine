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
        @UniqueConstraint(name = "uc_sala_comienzo_dia", columnNames = {"comienzo", "sala_id", "dia"})
})
public class Proyeccion {
    public Proyeccion(Pelicula pelicula, Sala sala, LocalDate dia, LocalTime comienzo) {
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

}