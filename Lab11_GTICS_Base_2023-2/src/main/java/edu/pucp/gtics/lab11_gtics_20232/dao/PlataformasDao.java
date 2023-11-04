package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Plataformas;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PlataformasDao {
    public List<Plataformas> listar(){
        List<Plataformas> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8080/plataformas";
        ResponseEntity<Plataformas[]> responseEntity = restTemplate.getForEntity(endPoint,Plataformas[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Plataformas[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }
        return lista;
    }
}