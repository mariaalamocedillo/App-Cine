package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "cliente")
public class Cliente {
    public Cliente(){

    }
    public Cliente(String nombre, Long tlfn, String email, String contrasena) {
        this.nombre = nombre;
        this.tlfn = tlfn;
        this.email = email;
        this.contrasena = contrasena;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Long tlfn;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String contrasena;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private Set<Reserva> reserva = new LinkedHashSet<>();

}