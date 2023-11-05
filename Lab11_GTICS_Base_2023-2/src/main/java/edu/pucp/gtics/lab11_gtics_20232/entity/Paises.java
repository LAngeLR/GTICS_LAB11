package edu.pucp.gtics.lab11_gtics_20232.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;

@Getter
@Entity
@Table(name = "paises")
public class Paises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Sede no puede estar vacío")
    private int idpais;
    private String iso;
    private String nombre;

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
