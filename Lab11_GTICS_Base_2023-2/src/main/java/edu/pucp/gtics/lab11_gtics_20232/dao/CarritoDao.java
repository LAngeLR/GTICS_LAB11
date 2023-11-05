package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.CarritoCompras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.PaqueteJuegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CarritoDao {



    public void guardarCarrito(CarritoCompras carrito){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8081/api/carrito/registro";
        HttpEntity<CarritoCompras> httpEntity = new HttpEntity<>(carrito, headers);

        RestTemplate restTemplate = new RestTemplate();
        if(carrito.getIdcarritocompras() > 0){
            restTemplate.put(url, httpEntity, User.class);
        } else {
            restTemplate.postForEntity(url, httpEntity, User.class);
        }
    }

    public CarritoCompras buscarCarrito(Integer idUsuario){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/api/carrito/buscarCarrito?id="+idUsuario;
        return restTemplate.getForObject(url, CarritoCompras.class);
    }

    public void eliminarCarrito(CarritoCompras carritoCompras){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8081/api/carrito/actualizar";
        HttpEntity<CarritoCompras> httpEntity = new HttpEntity<>(carritoCompras, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url, httpEntity, User.class);
    }
}
