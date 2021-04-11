package com.exam.web;

import com.exam.model.binding.ProductAddBindingModel;
import com.exam.model.binding.ProductEditBindingModel;
import com.exam.model.binding.RecipeAddBindingModel;
import com.exam.model.binding.RecipeEditBindingModel;
import com.exam.model.entities.Product;
import com.exam.model.entities.User;
import com.exam.model.service.ProductServiceModel;
import com.exam.model.service.RecipeServiceModel;
import com.exam.service.ProductService;
import com.exam.service.RecipeService;
import com.exam.service.UserService;
import com.exam.view.RecipeViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/recipe")
public class RecipeController {

    private final ModelMapper modelMapper;
    private final RecipeService recipeService;
    private final ProductService productService;
    private final UserService userService;

    public RecipeController(ModelMapper modelMapper, RecipeService recipeService, ProductService productService, UserService userService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.productService = productService;
        this.userService = userService;
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

    @GetMapping("/all")
    public String all(Model  model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "all/allRecipes";
    }

    @GetMapping("/edit{id}")
    public String edit(@PathVariable("id")Long id,Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }
        if (!model.containsAttribute("recipeEditBindingModel")) {
            model.addAttribute("recipeEditBindingModel", new RecipeEditBindingModel());
        }
        model.addAttribute("recipe", recipeService.findById(id));

        return "/edit/editRecipe";
    }


    @PostMapping("/edit{id}")
    private String edit(@PathVariable("id") Long id, @Valid RecipeEditBindingModel recipeEditBindingModel,
                        HttpSession httpSession,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeEditBindingModel", recipeEditBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.recipeEditBindingModel", bindingResult);

            return "redirect:add";
        }


        recipeService.updateRecipe(modelMapper.map(recipeEditBindingModel, RecipeServiceModel.class));

        return "redirect:details"+id;

    }

    @PostMapping("/add")
    public String add(HttpSession httpSession, HttpServletRequest request,
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

        String productsStr = recipeAddBindingModel.getProducts();
        List<String> productsStrList = Arrays.stream(productsStr.split(", ")).collect(Collectors.toList());
        List<Product> products = new ArrayList<>();
        for (String s : productsStrList) {
            products.add(productService.getProductByName(s));

        }
        recipeServiceModel.setProducts(products);


        recipeServiceModel.setAddedBy(modelMapper.map(httpSession.getAttribute("user"), User.class).getUsername());


        recipeService.createRecipe(recipeServiceModel);


        return "redirect:/";

    }



    @GetMapping("/details{id}")
    public String details(@PathVariable("id") Long id, Model model){

        RecipeViewModel recipeViewModel = recipeService.findById(id);
        recipeViewModel.setAddedBy(recipeViewModel.getAddedBy());
        model.addAttribute("recipe", recipeViewModel);
        model.addAttribute("products", recipeViewModel.getProducts());

        return "details/recipe";
    }

    @RequestMapping("/delete{id}")
    private String delete(@PathVariable("id") Long id) {
        recipeService.deleteById(id);
        return "redirect:all";
    }

}
