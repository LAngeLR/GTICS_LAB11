package edu.pucp.gtics.lab11_gtics_20232.dao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Generos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component


public class GenerosDao {
    public List<Generos> listar(){
        List<Generos> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8080/generos";
        ResponseEntity<Generos[]> responseEntity = restTemplate.getForEntity(endPoint,Generos[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Generos[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }
        return lista;
    }

}