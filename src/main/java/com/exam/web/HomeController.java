package com.exam.web;

import com.exam.repository.RecipeRepository;
import com.exam.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private RecipeService recipeService;

    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(value={"/", "/home", "/index"})
    public String index(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

//        System.out.println(recipeService.getAllRecipes());
        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "home";
    }

}
