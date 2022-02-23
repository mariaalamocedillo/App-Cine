package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "asiento_reservado", uniqueConstraints = {
        @UniqueConstraint(name = "uc_asiento_reservado", columnNames = {"proyeccion_id", "asiento_id", "reserva_id"})
})
public class Asiento_reservado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proyeccion_id", nullable = false)
    private Proyeccion proyeccion;


    @ManyToOne(optional = false)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asiento_id", nullable = false)
    private Asiento asiento;

}