package edu.pucp.gtics.lab11_gtics_20232.dao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Paises;
import edu.pucp.gtics.lab11_gtics_20232.entity.Plataformas;
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

public class DistribuidorasDao {
    public List<Distribuidoras> listar(){
        List<Distribuidoras> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8081/api/distribuidora/lista";
        ResponseEntity<Distribuidoras[]> responseEntity = restTemplate.getForEntity(endPoint,Distribuidoras[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Distribuidoras[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }
        return lista;
    }

    public void guardar(Distribuidoras distribuidoras){

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8081/distribuidora/lista";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Distribuidoras> httpEntity = new HttpEntity<>(distribuidoras,httpHeaders);

        if(distribuidoras.getIddistribuidora() == null){
            restTemplate.postForEntity(endPoint,httpEntity,Distribuidoras.class);
        }else{
            restTemplate.put(endPoint,httpEntity,Distribuidoras.class);
        }

    }
}