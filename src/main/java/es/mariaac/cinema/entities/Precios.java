package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "precios")
public class Precios {

    public Precios(String nombre, String condiciones, Double precioFinal) {
        this.nombre = nombre;
        this.condiciones = condiciones;
        this.precioFinal = precioFinal;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String condiciones;

    @Column(name = "precio_final", nullable = false, length = 100)
    private Double precioFinal;

}
