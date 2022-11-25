package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reserva")
public class Reserva {

    public Reserva(){
    }

    public Reserva(Boolean activa, Boolean pagada, Boolean reservada, Proyeccion proyeccion, Cliente cliente) {
        this.activa = activa;
        this.pagada = pagada;
        this.reservada = reservada;
        this.proyeccion = proyeccion;
        this.cliente = cliente;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean activa;

    @Column(nullable = false)
    private Boolean pagada;

    @Column(nullable = false)
    private Boolean reservada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proyeccion_id", nullable = false)
    private Proyeccion proyeccion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrada> entradas = new ArrayList<>();

}