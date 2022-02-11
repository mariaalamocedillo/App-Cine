package es.mariaac.cinema.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pelicula")
public class Pelicula {

    public Pelicula(){
    }

    public Pelicula(String titulo, String director, Integer duracion, String poster) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.director = director;
        this.poster = poster;
    }

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
    private List<Proyeccion> proyecciones = new ArrayList<>();

    @Column(nullable = true)
    private String estudio;

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private Boolean enProyeccion;


}