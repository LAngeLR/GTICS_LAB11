package edu.pucp.gtics.lab11_gtics_20232.controller;



import edu.pucp.gtics.lab11_gtics_20232.dao.DistribuidorasDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.PaisesDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
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

    public DistribuidorasController(DistribuidorasDao distribuidorasDao, PaisesDao paisesDao) {
        this.distribuidorasDao = distribuidorasDao;
        this.paisesDao = paisesDao;
    }



    @GetMapping(value = {"/lista"})
    public String listaDistribuidoras (Model model){
        model.addAttribute("listadistribuidoras", distribuidorasDao.listar());
        return "distribuidoras/lista";
    }
/*
    @GetMapping("/editar")
    public String editarDistribuidoras(@RequestParam("id") int id, Model model){
        Optional<Distribuidoras> opt = distribuidorasRepository.findById(id);
        List<Paises> listaPaises = paisesRepository.findAll();
        if (opt.isPresent()){
            Distribuidoras distribuidora = opt.get();
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaPaises", listaPaises);
            return "distribuidoras/editarFrm";
        }else {
            return "redirect:/distribuidoras/lista";
        }

    }*/

    @GetMapping("/nuevo")
    public String nuevaDistribuidora(Model model, @ModelAttribute("distribuidora") Distribuidoras distribuidora){
        List<Paises> listaPaises = paisesDao.listar();
        model.addAttribute("listaPaises", listaPaises);
        return "distribuidoras/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora(Model model, RedirectAttributes attr, @ModelAttribute("distribuidora") @Valid Distribuidoras distribuidora , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
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
/*
    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id){
        Optional<Distribuidoras> opt = distribuidorasRepository.findById(id);
        if (opt.isPresent()) {
            distribuidorasRepository.deleteById(id);
        }
        return "redirect:/distribuidoras/lista";
    }
*/
}
