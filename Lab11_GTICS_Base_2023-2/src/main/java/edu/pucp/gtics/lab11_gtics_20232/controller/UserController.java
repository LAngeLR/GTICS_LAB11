package edu.pucp.gtics.lab11_gtics_20232.controller;


import edu.pucp.gtics.lab11_gtics_20232.dao.UsuarioDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UsuarioDao usuarioDao;

/*    @GetMapping({"/","/login"})
    public String login(){return "user/signIn";}*/

    @GetMapping(value = "/registro")
    public String registro(@ModelAttribute("usuario") User usuario){return "user/signUp";}

    @PostMapping(value = "/guardarUsuario")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid User usuario, BindingResult bindingResult, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            return "user/signUp";
        } else {
            usuario.setEnable(1);
            usuario.setAutorizacion("USER");
            attr.addFlashAttribute("msg","Usuario registrado correctamente");
            usuarioDao.guardarUsuario(usuario);
            return "redirect:/login";
        }
    }




}