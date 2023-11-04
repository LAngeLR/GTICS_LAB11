package edu.pucp.gtics.lab11_gtics_20232.controller;


import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
        @RequestMapping(value = {"/","/login"})
        public String login(@ModelAttribute("usuario")User usuario, Model model, RedirectAttributes attr){
            return "user/signIn";
        }

        @RequestMapping(value = "/registro")
        public String registro(@ModelAttribute("usuario")User usuario, Model model, RedirectAttributes attr){
            return "user/signUp";
        }



}