package edu.pucp.gtics.lab11_gtics_20232.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", nullable = false)
    private int idusuario;

    @NotBlank(message = "El apellido no puede estar en blanco")
    @Size(min=2, message = "El apellido no puede tener menos de 2 caracteres")
    @Pattern(regexp = "^[^0-9]*$", message = "El apellido solo puede contener letras")
    private String apellidos;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min=2, message = "El nombre no puede tener menos de 2 caracteres")
    @Pattern(regexp = "^[^0-9]*$", message = "El nombre solo puede contener letras")
    private String nombres;

    @NotBlank(message = "El correo no puede estar en blanco")
    @Size(min=2, message = "El correo no puede tener menos de 2 caracteres")
    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min=6, message = "La contraseña no puede tener menos de 6 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "La contraseña debe contener al menos una mayúscula y un número.")
    private String password;
    private String autorizacion;
    private int enabled;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnable(int enabled) {
        this.enabled = enabled;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
