package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reserva")
public class Reserva {
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


    @ManyToOne(optional = true)     //es opcional porque podría ser una reserva online
    @JoinColumn(name = "mostrador_id")
    private Mostrador mostrador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "precio")
    private Integer precio;

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}