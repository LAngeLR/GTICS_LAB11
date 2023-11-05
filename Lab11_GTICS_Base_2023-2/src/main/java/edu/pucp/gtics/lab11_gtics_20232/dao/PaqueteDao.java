package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.PaqueteJuegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PaqueteDao {

    public PaqueteJuegos buscarPaquete(Integer idCarrito, Integer idJuego){
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("elarios@pucp.pe", "prueba").build();
        String url = "http://localhost:8081/api/paquetes/buscarPaquete?idCarrito="+idCarrito+"&idJuego="+idJuego;
        return restTemplate.getForObject(url, PaqueteJuegos.class);

    }

    public void eliminarPaquete(Integer idCarrito, Integer idJuego){
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("elarios@pucp.pe", "prueba").build();
        String url = "http://localhost:8081/api/paquetes/eliminarPaquete?idCarrito="+idCarrito+"&idJuego="+idJuego;
        restTemplate.delete(url);
    }

    public void agregarJuego(PaqueteJuegos paqueteJuegos){
        HttpHeaders headers = new HttpHeaders();
        PaqueteDao paqueteDao = new PaqueteDao();

        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8081/api/paquetes/registro";
        HttpEntity<PaqueteJuegos> httpEntity = new HttpEntity<>(paqueteJuegos, headers);

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("elarios@pucp.pe", "prueba").build();

        if(paqueteDao.buscarPaquete(paqueteJuegos.getId().getCarritoCompras().getIdcarritocompras(),
                paqueteJuegos.getId().getJuegos().getIdjuego()) != null){

            paqueteJuegos.setCantidadJuego(paqueteJuegos.getCantidadJuego() + 1);
            restTemplate.put(url, httpEntity, PaqueteJuegos.class);
        } else {
            restTemplate.postForEntity(url, httpEntity, PaqueteJuegos.class);
        }
    }

    public List<PaqueteJuegos> listarPaquetesporUsuario(Integer idUsuario){
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("elarios@pucp.pe", "prueba").build();
        String url = "http://localhost:8081/api/paquetes/lista?idUsuario="+idUsuario;
        ResponseEntity<PaqueteJuegos[]> responseEntity = restTemplate.getForEntity(url, PaqueteJuegos[].class);

        return Arrays.asList(responseEntity.getBody());
    }
}
