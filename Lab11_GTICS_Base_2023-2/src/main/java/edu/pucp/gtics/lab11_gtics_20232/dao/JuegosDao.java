package edu.pucp.gtics.lab11_gtics_20232.dao;


import edu.pucp.gtics.lab11_gtics_20232.entity.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component


public class JuegosDao {
   public List<Juegos> listar(){
       List<Juegos> lista = new ArrayList<>();



       RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("jchaponan@pucp.pe", "jchapo").build();
       String endPoint = "http://localhost:8081/api/juegos";
       ResponseEntity<Juegos[]> responseEntity = restTemplate.getForEntity(endPoint,Juegos[].class);
       if (responseEntity.getStatusCode().is2xxSuccessful()) {
           Juegos[] body = responseEntity.getBody();
           lista = Arrays.asList(body);
       }
       return lista;
   }

}