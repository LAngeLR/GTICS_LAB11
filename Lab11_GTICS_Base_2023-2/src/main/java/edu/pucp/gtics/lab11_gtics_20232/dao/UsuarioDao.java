package edu.pucp.gtics.lab11_gtics_20232.dao;

import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioDao {

    public void guardarUsuario(User usuario){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8081/api/usuarios/registro/xwwwform";
        HttpEntity<User> httpEntity = new HttpEntity<>(usuario, headers);

        RestTemplate restTemplate = new RestTemplate();
        if(usuario.getIdusuario() > 0){
            restTemplate.postForEntity(url, httpEntity, User.class);
        } else {
            restTemplate.put(url, httpEntity, User.class);
        }

    }

}
