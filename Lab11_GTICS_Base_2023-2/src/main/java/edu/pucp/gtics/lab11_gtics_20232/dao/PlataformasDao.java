package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
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
public class PlataformasDao {
    public List<Plataformas> listar(){
        List<Plataformas> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8081/api/plataformas/lista";
        ResponseEntity<Plataformas[]> responseEntity = restTemplate.getForEntity(endPoint,Plataformas[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Plataformas[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }
        return lista;
    }
    public void guardar(Plataformas plataforma){

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8081/api/plataformas/lista";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Plataformas> httpEntity = new HttpEntity<>(plataforma,httpHeaders);

        if(plataforma.getIdplataforma() ==null){
            restTemplate.postForEntity(endPoint,httpEntity,Plataformas.class);
        }else{
            restTemplate.put(endPoint,httpEntity,Plataformas.class);
        }

    }

}