package es.mariaac.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Table(name = "sala")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @JsonIgnore
    @OneToMany(orphanRemoval = true)
    private List<Asiento> asientos = new ArrayList<>();

    public boolean addAsiento(Asiento asiento){
        return this.asientos.add(asiento);
    }
}