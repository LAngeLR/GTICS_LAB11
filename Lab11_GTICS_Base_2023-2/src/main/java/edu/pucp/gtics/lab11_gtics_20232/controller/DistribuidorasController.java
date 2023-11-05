package edu.pucp.gtics.lab11_gtics_20232.controller;



import edu.pucp.gtics.lab11_gtics_20232.dao.DistribuidorasDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.GenerosDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.PaisesDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.PlataformasDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Paises;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/distribuidoras")

public class DistribuidorasController {

   final DistribuidorasDao distribuidorasDao;
   final PaisesDao paisesDao;
   final GenerosDao generosDao;
   final PlataformasDao plataformasDao;

    public DistribuidorasController(DistribuidorasDao distribuidorasDao, PaisesDao paisesDao, GenerosDao generosDao, PlataformasDao plataformasDao) {
        this.distribuidorasDao = distribuidorasDao;
        this.paisesDao = paisesDao;
        this.generosDao = generosDao;
        this.plataformasDao = plataformasDao;
    }



    @GetMapping(value = {"/lista"})
    public String listaDistribuidoras (Model model){
        model.addAttribute("listadistribuidoras", distribuidorasDao.listar());
        return "distribuidoras/lista";
    }
    
    @GetMapping("/editar")
    public String editarDistribuidora(@ModelAttribute("distribuidora") Distribuidoras distribuidora,
                              Model model, @RequestParam("id") int id) {

        Distribuidoras distribuidora1 = distribuidorasDao.buscarPorId(id);

        if(distribuidora1 != null) {
            distribuidora = distribuidora1;
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaSedes", paisesDao.listar());
            return "distribuidoras/editarFrm";
        } else {
            return "redirect:/distribuidoras/lista";
        }
    }

    @GetMapping("/nuevo")
    public String nuevaDistribuidora(Model model, @ModelAttribute("distribuidora") Distribuidoras distribuidora){
        List<Paises> listaPaises = paisesDao.listar();
        model.addAttribute("listaPaises", listaPaises);
        return "distribuidoras/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora(@ModelAttribute("distribuidora") @Valid Distribuidoras distribuidora, BindingResult bindingResult,
                                  Model model, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            List<Paises> listaPaises = paisesDao.listar();
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaPaises", listaPaises);
            return "distribuidoras/editarFrm";
        } else {
            if (distribuidora.getIddistribuidora() == null) {
                attr.addFlashAttribute("msg", "Distribuidora creada exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Distribuidora actualizada exitosamente");
            }
            distribuidorasDao.guardar(distribuidora);
            return "redirect:/distribuidoras/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarDistribuidoras(Model model, @RequestParam("id") int id, RedirectAttributes attr) {

        Distribuidoras distribuidoraBuscar = distribuidorasDao.buscarPorId(id);

        if (distribuidoraBuscar != null) {
            distribuidorasDao.borrarDistribuidora(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/juegos/lista";

    }
}
