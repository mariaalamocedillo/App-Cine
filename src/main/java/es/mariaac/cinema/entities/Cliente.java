package es.mariaac.cinema.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Long tlfn;

    @Column(nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private Set<Reserva> reserva = new LinkedHashSet<>();


//TODO hacer un usuario para reservas online, o registrar las reservas que tengan hechas (con el codigo e email quedan exentos de llevar la entrada física)
}