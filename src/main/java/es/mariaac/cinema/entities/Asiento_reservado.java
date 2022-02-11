package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "asiento_reservado")
public class Asiento_reservado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proyeccion_id", nullable = false)
    private Proyeccion proyeccion;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "asiento_id", nullable = false)
    private Asiento asiento;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

}