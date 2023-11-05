package edu.pucp.gtics.lab11_gtics_20232.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "paquetejuegos")
@Getter
@Setter
public class PaqueteJuegos implements Serializable {


    @EmbeddedId
    private PaqueteJuegosPK id;

    @Column(name = "cantidadJuego")
    private int cantidadJuego;

    public PaqueteJuegos() {
        this.id = new PaqueteJuegosPK();
    }}
