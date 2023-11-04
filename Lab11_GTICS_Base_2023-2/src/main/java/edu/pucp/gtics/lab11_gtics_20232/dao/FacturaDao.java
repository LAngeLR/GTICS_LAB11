package edu.pucp.gtics.lab11_gtics_20232.dao;
import edu.pucp.gtics.lab11_gtics_20232.entity.JuegosxUsuario;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
@Component



public class FacturaDao {
    private Integer fechaEnvio;
    private String tarjeta;
    private String codigoVerificacion;
    private String direccion;
    private BigDecimal monto;
    private JuegosxUsuario idjuegosxusuario;
}