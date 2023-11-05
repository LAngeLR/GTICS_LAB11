package edu.pucp.gtics.lab11_gtics_20232.controller;


import edu.pucp.gtics.lab11_gtics_20232.dao.GenerosDao;
import edu.pucp.gtics.lab11_gtics_20232.dao.PlataformasDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Plataformas;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/plataformas")
public class PlataformasController {

    final PlataformasDao plataformasDao;
    final GenerosDao generosDao;

    public PlataformasController(PlataformasDao plataformasDao, GenerosDao generosDao) {
        this.plataformasDao = plataformasDao;
        this.generosDao = generosDao;
    }


    @GetMapping(value = {"/lista"})
    public String listaPlataformas (Model model){
        model.addAttribute("listaplataformas", plataformasDao.listar());
        return "plataformas/lista";
    }
    @GetMapping("/editar")
    public String editarPlataformas(@ModelAttribute("plataforma") Plataformas plataforma,
                                      Model model, @RequestParam("id") int id) {

        Plataformas plataforma1 = plataformasDao.buscarPorId(id);

        if(plataforma1 != null) {
            plataforma = plataforma1;
            model.addAttribute("plataforma", plataforma);
            model.addAttribute("listaPlataformas", plataformasDao.listar());
            model.addAttribute("listaGeneros", generosDao.listar());
            return "plataformas/editarFrm";
        } else {
            return "redirect:/plataformas/lista";
        }
    }
    @GetMapping("/nuevo")
    public String nuevaPlataforma(@ModelAttribute("plataforma") Plataformas plataforma){
            return "plataformas/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarPlataforma(Model model, RedirectAttributes attr, @ModelAttribute("plataforma") @Valid Plataformas plataforma, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.hasFieldErrors("idplataforma"));
            model.addAttribute("plataforma", plataforma);
            return "plataformas/editarFrm";
        } else {
            if (plataforma.getIdplataforma() == null) {
                attr.addFlashAttribute("msg", "Plataforma creada exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Plataforma actualizada exitosamente");
            }
            plataformasDao.guardar(plataforma);
            return "redirect:/plataformas/lista";
        }
    }
    @GetMapping("/borrar")
    public String borrarPlataformas(Model model, @RequestParam("id") int id, RedirectAttributes attr) {
        System.out.println("entro a borrar");

        Plataformas plataformaBuscar = plataformasDao.buscarPorId(id);

        if (plataformaBuscar != null) {
            plataformasDao.borrarPlataforma(id);
            attr.addFlashAttribute("msg", "Plataforma borrada exitosamente");
        }
        return "redirect:/plataformas/lista";

    }


}
