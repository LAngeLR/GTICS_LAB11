package edu.pucp.gtics.lab11_gtics_20232.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Table(name = "generos")

public class Generos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Género no puede estar vacío")
    private Integer idgenero;
    private String nombre;
    private String descripcion;

}
