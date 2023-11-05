package edu.pucp.gtics.lab11_gtics_20232.controller;

import edu.pucp.gtics.lab11_gtics_20232.dao.CarritoDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.JuegosDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.PaqueteDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.CarritoCompras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.PaqueteJuegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/carritoCompras")
public class CarritoController {


    @Autowired
    CarritoDao carritoDao;

    @Autowired
    JuegosDao juegosDao;

    @Autowired
    PaqueteDao paqueteDao;

    @GetMapping(value = { "","/"})
    public String listaCarrito (Model model, HttpServletRequest request){

        User usuario = (User) request.getSession().getAttribute("usuario");
        model.addAttribute("listaCarrito", paqueteDao.listarPaquetesporUsuario(usuario.getIdusuario()));
        return "carrito/lista";
    }

    @GetMapping(value = "/pago")
    public String pagoCarrito (Model model, HttpServletRequest request){

        User usuario = (User) request.getSession().getAttribute("usuario");
        CarritoCompras carritoCompras = carritoDao.buscarCarrito(usuario.getIdusuario());
        model.addAttribute("carrito", carritoCompras);
        return "carrito/pago";
    }

    @GetMapping(value = "/anadirACarrito")
    public String anadirACarrito(@RequestParam("idJuego") int idJuego, Model model, RedirectAttributes attr, HttpServletRequest request){

        User usuario = (User) request.getSession().getAttribute("usuario");
        CarritoCompras carritoCompras = carritoDao.buscarCarrito(usuario.getIdusuario());
        Juegos juegos = juegosDao.buscarPorId(idJuego);

        carritoCompras.setCantidadTotal(carritoCompras.getCantidadTotal()+juegos.getPrecio());

        PaqueteJuegos paqueteJuegos = new PaqueteJuegos();
        paqueteJuegos.getId().setJuegos(juegos);
        paqueteJuegos.getId().setCarritoCompras(carritoCompras);
        paqueteJuegos.setCantidadJuego(1);

        paqueteDao.agregarJuego(paqueteJuegos);
        carritoDao.guardarCarrito(carritoCompras);
        return "redirect:/";
    }


    public String editarCarrito(){

        return "redirect:/juegos/lista";
    }

    public String borrarCarrito(){

        return "redirect:/carrito/lista";
    }


}
