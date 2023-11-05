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

    @Id
    @ManyToOne
    @JoinColumn(name = "idcarritocompras")
    private CarritoCompras carritoCompras;

    @Column(name = "cantidadJuego")
    private int cantidadJuego;

    @ManyToOne
    @JoinColumn(name = "idjuegoseleccionado")
    private Juegos juegos;
}
