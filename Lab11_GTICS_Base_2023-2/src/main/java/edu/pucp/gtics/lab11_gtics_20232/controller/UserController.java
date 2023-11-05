package edu.pucp.gtics.lab11_gtics_20232.controller;


import edu.pucp.gtics.lab11_gtics_20232.dao.CarritoDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.JuegosDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.UsuarioDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.CarritoCompras;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private CarritoDao carritoDao;

    @Autowired
    private JuegosDao juegosDao;

/*
    final PasswordEncoder passwordEncoder;

    public UserController() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
*/

    @GetMapping(value = {"/",""})
    public String index(Model model){

        model.addAttribute("listaJuegos", juegosDao.listar());
        return "juegos/vista";
    }

    @GetMapping("/login")
    public String login(){return "user/signIn";}

    @GetMapping(value = "/registro")
    public String registro(@ModelAttribute("usuario") User usuario){return "user/signUp";}

    @GetMapping(value = "/listaUsuarios")
    public String listarUsuarios(Model model){
        model.addAttribute("listaUsuarios", usuarioDao.listarUsuarios());
        return "user/lista";
    }

    @PostMapping(value = "/guardarUsuario")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid User usuario, BindingResult bindingResult, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            return "user/signUp";
        } else {
            usuario.setEnable(1);
            usuario.setAutorizacion("USER");
//            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            CarritoCompras carritoCompras = new CarritoCompras();
            carritoCompras.setIdcarritocompras(usuario.getIdusuario());
            carritoCompras.setUsuario(usuario);
            carritoCompras.setCantidadTotal(0);


            attr.addFlashAttribute("msg","Usuario registrado correctamente");
            usuarioDao.guardarUsuario(usuario);
            carritoDao.guardarCarrito(carritoCompras);

            return "redirect:/login";
        }
    }

    @GetMapping("/actualizarUsuario")
    public String cambiarRol(@RequestParam("id") Integer idUsuario, RedirectAttributes attr) {

        User usuario = usuarioDao.obtenerUsuario(idUsuario);
        if(usuario.getAutorizacion().equals("USER")){
            usuario.setAutorizacion("ADMIN");
        } else {
            usuario.setAutorizacion("USER");
        }
        usuarioDao.guardarUsuario(usuario);
        attr.addFlashAttribute("msg","Usuario actualizado correctamente");
        return "redirect:/listaUsuarios";
    }






}