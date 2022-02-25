package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mostrador")
public class Mostrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //TODO ELIMINAR???
}