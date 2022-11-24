package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "entrada")
public class Entrada {

    public Entrada(){
    }

    public Entrada(Asiento asiento, Precios precio) {
        this.asiento = asiento;
        this.precio = precio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asiento_id", nullable = false)
    private Asiento asiento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "precio_id", nullable = false)
    private Precios precio;

}