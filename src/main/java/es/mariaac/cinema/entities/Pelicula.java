package es.mariaac.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String director;

    @Column(length = 1024)
    @Type(type = "org.hibernate.type.TextType")
    private String descripcion;

    @Column(nullable = false)
    private Integer duracion;

    @OneToMany(mappedBy = "pelicula", orphanRemoval = true)
    @JsonManagedReference
    private List<Proyeccion> proyecciones = new ArrayList<>();

    @Column(nullable = true)
    private String estudio;

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private Boolean enProyeccion;


}