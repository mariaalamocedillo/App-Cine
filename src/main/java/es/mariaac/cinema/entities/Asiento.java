package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "asiento", uniqueConstraints = {
        @UniqueConstraint(name = "uc_asiento_fila_letra_sala_id", columnNames = {"fila", "letra", "sala_id"})
})
public class Asiento {

    public Asiento(String fila, String letra) {
        this.fila = fila;
        this.letra = letra;
    }

    public Asiento(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 1)
    private String fila;

    @Column(nullable = false, length = 1)
    private String letra;


    public String getName() {
        return getFila() + getLetra();
    }
}