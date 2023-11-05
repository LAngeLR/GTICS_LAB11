package edu.pucp.gtics.lab11_gtics_20232.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carritoCompras")
public class CarritoController {




    @GetMapping(value = { "","/"})
    public String listaCarrito (Model model){
        return "carrito/lista";
    }

    public String nuevoCarrito(@RequestParam("id") int id){

        return "redirect:/vista";
    }

    public String editarCarrito(){

        return "redirect:/juegos/lista";
    }

    public String borrarCarrito(){

        return "redirect:/carrito/lista";
    }


}
