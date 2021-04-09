package com.exam.web;

import com.exam.model.binding.MenuAddBindingModel;
import com.exam.model.binding.RecipeAddBindingModel;
import com.exam.model.entities.Product;
import com.exam.model.entities.Recipe;
import com.exam.model.entities.User;
import com.exam.model.service.MenuServiceModel;
import com.exam.model.service.RecipeServiceModel;
import com.exam.service.MenuService;
import com.exam.service.RecipeService;
import com.exam.view.MenuViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private RecipeService recipeService;
    private ModelMapper modelMapper;
    private MenuService menuService;

    public MenuController(RecipeService recipeService, ModelMapper modelMapper, MenuService menuService) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
        this.menuService = menuService;
    }

    @GetMapping("/add")
    public String add(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (!model.containsAttribute("menuAddBindingModel")) {
            model.addAttribute("menuAddBindingModel", new MenuAddBindingModel());
        }

        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "add/addMenu";
    }

    @GetMapping("/all")
    public String all(Model model){
        List<MenuViewModel> menus = menuService.getAllMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("imgUrl", menus.get(0).getRecipes().get(0).getImgUrl());
        return "allMenus";
    }

    @PostMapping("/add")
    public String add(HttpSession httpSession, HttpServletRequest request,
                      @Valid MenuAddBindingModel menuAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("menuAddBindingModel", menuAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.menuAddBindingModel", bindingResult);

            return "redirect:add";
        }


        MenuServiceModel menuServiceModel = modelMapper.map(
                menuAddBindingModel,
                MenuServiceModel.class);

        String recipesStr = menuAddBindingModel.getRecipes();
        List<String> recipesStrList = Arrays.stream(recipesStr.split(", ")).collect(Collectors.toList());
        List<Recipe> recipes = new ArrayList<>();
        for (String s : recipesStrList) {
            recipes.add(recipeService.getRecipeEntityByName(s));

        }

        menuServiceModel.setRecipes(recipes);

        menuService.createMenu(menuServiceModel);


        return "redirect:/";

    }
}
