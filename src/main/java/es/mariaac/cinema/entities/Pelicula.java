package es.mariaac.cinema.entities;

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

    @Column()
    private String estudio;

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private Boolean enProyeccion;

    public boolean addProyeccion(Proyeccion proyeccion){
        return this.proyecciones.add(proyeccion);
    }


    public Pelicula(String titulo, String director, String descripcion,
                    Integer duracion, String estudio, String poster, Boolean enProyeccion) {
        this.titulo = titulo;
        this.director = director;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.estudio = estudio;
        this.poster = poster;
        this.enProyeccion = enProyeccion;
    }

    public Pelicula() {

    }
}