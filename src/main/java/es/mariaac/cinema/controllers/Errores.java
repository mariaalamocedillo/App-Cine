package es.mariaac.cinema.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.mvc.binding.ParamError;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Named("error")
@RequestScoped
public class Errores {

  private List<ParamError> errores = new ArrayList<>();

  public String getMensajes() {
    return errores.stream()
      .map(ParamError::getMessage)
      .collect(Collectors.joining("<br>"));
  }

  public String getMensaje(String param) {
    return errores.stream()
      .filter(v -> v.getParamName().equals(param))
      .map(ParamError::getMessage)
      .findFirst()
      .orElse("");
  }
}
