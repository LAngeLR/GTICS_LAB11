package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Paises;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PaisesDao {
    public List<Paises> listar(){
        List<Paises> lista = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("elarios@pucp.pe", "prueba").build();
        String endPoint = "http://localhost:8081/api/paises/lista";
        ResponseEntity<Paises[]> responseEntity = restTemplate.getForEntity(endPoint,Paises[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Paises[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }
        return lista;
    }



}