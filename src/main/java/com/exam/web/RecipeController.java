package com.exam.web;

import com.exam.model.binding.RecipeAddBindingModel;
import com.exam.model.binding.UserRegisterBindingModel;
import com.exam.model.entities.User;
import com.exam.model.service.RecipeServiceModel;
import com.exam.service.ProductService;
import com.exam.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final ModelMapper modelMapper;
    private final RecipeService recipeService;
    private final ProductService productService;

    public RecipeController(ModelMapper modelMapper, RecipeService recipeService, ProductService productService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.productService = productService;
    }

    @GetMapping("/add")
    public String add(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (!model.containsAttribute("recipeAddBindingModel")) {
            model.addAttribute("recipeAddBindingModel", new RecipeAddBindingModel());
        }

        model.addAttribute("products", productService.getAllProducts());

        return "add/addRecipe";
    }

    @PostMapping("/add")
    public String add(HttpSession httpSession, Model model,
                      @Valid RecipeAddBindingModel recipeAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.recipeAddBindingModel", bindingResult);

            return "redirect:add";
        }


        RecipeServiceModel recipeServiceModel = modelMapper.map(
                recipeAddBindingModel,
                RecipeServiceModel.class);

        recipeServiceModel.setAddedBy(modelMapper.map(httpSession.getAttribute("user"), User.class));


        recipeService.createRecipe(recipeServiceModel);


        return "redirect:/";

    }
}
