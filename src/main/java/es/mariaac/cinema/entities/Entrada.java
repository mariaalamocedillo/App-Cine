package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "entrada", uniqueConstraints = {
        @UniqueConstraint(name = "uc_entrada", columnNames = {"asiento_id", "reserva_id"})
})
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asiento_id", nullable = false)
    private Asiento asiento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "precio_id", nullable = false)
    private Precios precio;

}