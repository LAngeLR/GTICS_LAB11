package edu.pucp.gtics.lab11_gtics_20232.dao;
import edu.pucp.gtics.lab11_gtics_20232.entity.*;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
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
        String endPoint = "http://localhost:8081/api/distribuidora/lista";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Distribuidoras> httpEntity = new HttpEntity<>(distribuidoras,httpHeaders);

        if(distribuidoras.getIddistribuidora() == null){
            restTemplate.postForEntity(endPoint,httpEntity,Distribuidoras.class);
        }else{
            restTemplate.put(endPoint,httpEntity,Distribuidoras.class);
        }

    }

    public Distribuidoras buscarPorId(int id){

        Distribuidoras distribuidora = null;

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/api/distribuidora/lista?id=" + id;

        ResponseEntity<Distribuidoras> forEntity = restTemplate.getForEntity(url, Distribuidoras.class);

        if (forEntity.getStatusCode().is2xxSuccessful()) {
            distribuidora = forEntity.getBody();
        }

        return distribuidora;
    }

    public void borrarDistribuidora(int id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8081/api/distribuidora/lista?id=" + id);
    }


}