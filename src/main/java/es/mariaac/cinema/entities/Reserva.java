package es.mariaac.cinema.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean activa = false;

    @Column(nullable = false)
    private Boolean pagada = false;

    @Column(nullable = false)
    private Boolean reservada = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proyeccion_id", nullable = false)
    private Proyeccion proyeccion;



    @ManyToOne(optional = true)     //es opcional porque podría ser una reserva online
    @JoinColumn(name = "mostrador_id")
    private Mostrador mostrador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}