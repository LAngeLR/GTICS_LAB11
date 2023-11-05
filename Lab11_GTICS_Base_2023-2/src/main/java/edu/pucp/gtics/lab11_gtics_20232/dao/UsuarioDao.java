package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class UsuarioDao {

    public List<User> listarUsuarios(){

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/api/usuarios/listaUsuarios";
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url, User[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    public void guardarUsuario(User usuario){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8081/api/usuarios/registro";
        HttpEntity<User> httpEntity = new HttpEntity<>(usuario, headers);

        RestTemplate restTemplate = new RestTemplate();
        if(usuario.getIdusuario() > 0){
            restTemplate.put(url, httpEntity, User.class);
        } else {
            restTemplate.postForEntity(url, httpEntity, User.class);
        }

    }

    public User obtenerUsuario(Integer idUsuario){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/api/usuarios/buscarUsuario?id="+idUsuario;
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);
        return responseEntity.getBody();
    }

}
