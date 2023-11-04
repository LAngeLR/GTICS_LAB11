package edu.pucp.gtics.lab11_gtics_20232.controller;


import edu.pucp.gtics.lab11_gtics_20232.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab11_gtics_20232.repository.PaisesRepository;
import edu.pucp.gtics.lab11_gtics_20232.dao.PlataformasDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.Plataformas;
import edu.pucp.gtics.lab11_gtics_20232.repository.PlataformasRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/plataformas")
public class PlataformasController {

    final PlataformasDao plataformasDao;

    final DistribuidorasRepository distribuidorasRepository;

    final PaisesRepository paisesRepository;

    final PlataformasRepository plataformasRepository;



    public PlataformasController(PlataformasDao plataformasDao, DistribuidorasRepository distribuidorasRepository, PaisesRepository paisesRepository, PlataformasRepository plataformasRepository) {
        this.plataformasDao = plataformasDao;
        this.distribuidorasRepository = distribuidorasRepository;
        this.paisesRepository = paisesRepository;
        this.plataformasRepository = plataformasRepository;
    }

    @GetMapping(value = {"/lista"})
    public String listaPlataformas (Model model){
        model.addAttribute("listajuegos", plataformasDao.listar());
        return "plataformas/lista";
    }

    @GetMapping("/editar")
    public String editarPlataformas(@RequestParam("id") int id, Model model){
        Optional<Plataformas> opt = plataformasRepository.findById(id);

        if (opt.isPresent()){
            Plataformas plataforma = opt.get();
            model.addAttribute("plataforma", plataforma);
            return "plataformas/editarFrm";
        }else {
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
            if (plataforma.getIdplataforma() == 0) {
                attr.addFlashAttribute("msg", "Plataforma creada exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Plataforma actualizada exitosamente");
            }
            plataformasRepository.save(plataforma);
            return "redirect:/plataformas/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarPlataforma(@RequestParam("id") int id){
        Optional<Plataformas> opt = plataformasRepository.findById(id);
        if (opt.isPresent()) {
            plataformasRepository.deleteById(id);
        }
        return "redirect:/plataformas/lista";
    }

}
