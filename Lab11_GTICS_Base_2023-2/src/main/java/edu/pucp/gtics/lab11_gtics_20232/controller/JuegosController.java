package edu.pucp.gtics.lab11_gtics_20232.controller;

import edu.pucp.gtics.lab11_gtics_20232.dao.*;
import edu.pucp.gtics.lab11_gtics_20232.entity.*;
import edu.pucp.gtics.lab11_gtics_20232.repository.PlataformasRepository;
import edu.pucp.gtics.lab11_gtics_20232.repository.JuegosRepository;
import edu.pucp.gtics.lab11_gtics_20232.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab11_gtics_20232.repository.GenerosRepository;
import edu.pucp.gtics.lab11_gtics_20232.repository.PaisesRepository;

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

    final DistribuidorasRepository distribuidorasRepository;

    final PaisesRepository paisesRepository;

    final GenerosRepository generosRepository;

    final PlataformasRepository plataformasRepository;

    final JuegosRepository juegosRepository;

    public JuegosController(JuegosDao juegosDao, PlataformasDao plataformasDao, DistribuidorasDao distribuidorasDao, GenerosDao generosDao, DistribuidorasRepository distribuidorasRepository, PaisesRepository paisesRepository, GenerosRepository generosRepository, PlataformasRepository plataformasRepository, JuegosRepository juegosRepository) {
        this.juegosDao = juegosDao;
        this.plataformasDao = plataformasDao;
        this.distribuidorasDao = distribuidorasDao;
        this.generosDao = generosDao;
        this.distribuidorasRepository = distribuidorasRepository;
        this.paisesRepository = paisesRepository;
        this.generosRepository = generosRepository;
        this.plataformasRepository = plataformasRepository;
        this.juegosRepository = juegosRepository;
    }

    @GetMapping(value = {"", "/"})
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
    @PostMapping(value = {"", "/"})
    public String nuevoJuegos(Model model, @ModelAttribute("juego") Juegos juego){
        model.addAttribute("listaPlataformas", plataformasDao.listar());
        model.addAttribute("listaDistribuidoras", distribuidorasDao.listar());
        model.addAttribute("listaGeneros", generosDao.listar());
        return "juegos/editarFrm";
    }

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
            if (juego.getIdjuego() == 0) {
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





}
