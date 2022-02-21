package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "asiento")
public class Asiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 1)
    private String fila;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

}