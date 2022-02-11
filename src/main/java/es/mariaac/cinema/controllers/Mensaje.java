package es.mariaac.cinema.controllers;

import jakarta.inject.Named;
import jakarta.mvc.RedirectScoped;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Named("mensaje")
@RedirectScoped
public class Mensaje implements Serializable {

  private static final long serialVersionUID = 1L;

  private String texto;

}
