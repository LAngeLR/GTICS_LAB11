package edu.pucp.gtics.lab11_gtics_20232.controller;

import edu.pucp.gtics.lab11_gtics_20232.dao.DistribuidorasDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.GenerosDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.JuegosDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.PlataformasDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Generos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Plataformas;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/juegos")
public class JuegosController {

   final JuegosDao juegosDao;
   final PlataformasDao plataformasDao;
   final DistribuidorasDao distribuidorasDao;
   final GenerosDao generosDao;

    public JuegosController(JuegosDao juegosDao, PlataformasDao plataformasDao, DistribuidorasDao distribuidorasDao, GenerosDao generosDao) {
        this.juegosDao = juegosDao;
        this.plataformasDao = plataformasDao;
        this.distribuidorasDao = distribuidorasDao;
        this.generosDao = generosDao;
    }

    @GetMapping(value = { "/lista"})
    public String listaJuegos (Model model){
        model.addAttribute("listajuegos", juegosDao.listar());
        return "juegos/lista";
    }

    @GetMapping(value = "/misJuegos")
    public String listaMisJuegos (Model model, HttpServletRequest request){

/*
        User usuario = (User) request.getSession().getAttribute("usuario");
*/
        model.addAttribute("listajuegos", juegosDao.listarMisJuegos(/*usuario.getIdusuario()*/1));
        return "juegos/comprado";
    }

    @GetMapping(value = { "/nuevo"})
    public String nuevoJuegos(Model model, @ModelAttribute("juego") Juegos juego){
        model.addAttribute("listaPlataformas", plataformasDao.listar());
        model.addAttribute("listaDistribuidoras", distribuidorasDao.listar());
        model.addAttribute("listaGeneros", generosDao.listar());
        return "juegos/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult,
                                  Model model, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("listaPlataformas", plataformasDao.listar());
            model.addAttribute("listaDistribuidoras", distribuidorasDao.listar());
            model.addAttribute("listaGeneros", generosDao.listar());
            return "juegos/editarFrm";
        } else {
            String msg = "Juego " + (juego.getIdjuego() == null ? "creado" : "actualizado") + " exitosamente";
            attr.addFlashAttribute("msg", msg);
            // productRepository.save(product);
            juegosDao.guardar(juego); //voy a hacer la validación de guardar o actualizar en el dao.
            return "redirect:/juegos/lista";
        }
    }

    @GetMapping("/editar")
    public String editarJuego(@ModelAttribute("juego") Juegos juego,
                                      Model model, @RequestParam("id") int id) {

        Juegos juego1 = juegosDao.buscarPorId(id);

        if(juego1 != null) {
            juego = juego1;
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", plataformasDao.listar());
            model.addAttribute("listaDistribuidoras", distribuidorasDao.listar());
            model.addAttribute("listaGeneros", generosDao.listar());
            return "juegos/editarFrm";
        } else {
            return "redirect:/juegos/lista";
        }
    }

    @GetMapping("/delete")
    public String borrarJuego(Model model, @RequestParam("id") int id, RedirectAttributes attr) {

        juegosDao.deleteProductById(id);
        attr.addFlashAttribute("msg", "Juego borrado exitosamente");
        //}
        return "redirect:/juegos/lista";

    }
/*
    @PutMapping(value = {"", "/"})
    public String editarJuegos(@RequestParam("id") int id, Model model){
        Optional<Juegos> opt = juegosRepository.findById(id);
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
        List<Generos> listaGeneros = generosRepository.findAll();
        if (opt.isPresent()){
            Juegos juego = opt.get();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        }else {
            return "redirect:/juegos/lista";
        }
    }

    @PostMapping("/juegos/guardar")
    public String guardarJuegos(Model model, RedirectAttributes attr, @ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult ){
        if(bindingResult.hasErrors( )){
            List<Plataformas> listaPlataformas = plataformasRepository.findAll();
            List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
            List<Generos> listaGeneros = generosRepository.findAll();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        } else {
            if (juego.getIdjuego() == null) {
                attr.addFlashAttribute("msg", "Juego creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
            }
            juegosRepository.save(juego);
            return "redirect:/juegos/lista";
        }


    }

    @GetMapping("/juegos/borrar")
    public String borrarJuegos(@RequestParam("id") int id){
        Optional<Juegos> opt = juegosRepository.findById(id);
        if (opt.isPresent()) {
            juegosRepository.deleteById(id);
        }
        return "redirect:/juegos/lista";
    }


*/


}
