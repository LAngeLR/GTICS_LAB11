package edu.pucp.gtics.lab11_gtics_20232.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "plataformas")
public class Plataformas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Plataforma no puede estar vacío")
    private Integer idplataforma;

    @Size(min=3, max = 45, message = "Debe contener entre 3 y 45 caracteres")
    private String nombre;

    @Size(min=3, max = 198, message = "Debe contener entre 3 y 198 caracteres")
    private String descripcion;


}
