package com.exam.web;

import com.exam.model.binding.RecipeAddBindingModel;
import com.exam.model.binding.UserRegisterBindingModel;
import com.exam.model.entities.Product;
import com.exam.model.entities.User;
import com.exam.model.service.RecipeServiceModel;
import com.exam.service.ProductService;
import com.exam.service.RecipeService;
import com.exam.view.RecipeViewModel;
import org.apache.catalina.connector.Request;
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

    @GetMapping("/all")
    public String all(Model  model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "allRecipes";
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


        recipeServiceModel.setAddedBy(modelMapper.map(httpSession.getAttribute("user"), User.class));


        recipeService.createRecipe(recipeServiceModel);


        return "redirect:/";

    }



    @GetMapping("/details{id}")
    public String details(@PathVariable("id") Long id, Model model){

        RecipeViewModel recipeViewModel = recipeService.findById(id);

        model.addAttribute("recipe", recipeViewModel);
        model.addAttribute("products", recipeViewModel.getProducts());

        return "details/recipe";
    }

}
