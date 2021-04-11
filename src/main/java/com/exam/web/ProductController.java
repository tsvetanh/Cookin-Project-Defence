package com.exam.web;

import com.exam.model.binding.ProductAddBindingModel;
import com.exam.model.binding.ProductEditBindingModel;
import com.exam.model.service.ProductServiceModel;
import com.exam.service.ProductService;
import com.exam.view.ProductViewModel;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "all/allProducts";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (!model.containsAttribute("productAddBindingModel")) {
            model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
        }

        return "/add/addProduct";
    }

    @GetMapping("/details{id}")
    public String details(@PathVariable("id") Long id, Model model){
        ProductViewModel productViewModel = productService.findById(id);
        model.addAttribute("product", productViewModel);
        return "details/product";
    }

    @GetMapping("/edit{id}")
    public String edit(@PathVariable("id")Long id,Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }
        if (!model.containsAttribute("productEditBindingModel")) {
            model.addAttribute("productEditBindingModel", new ProductEditBindingModel());
        }
        model.addAttribute("product", productService.findById(id));

        return "/edit/editProduct";
    }


    @PostMapping("/edit{id}")
    private String edit(@PathVariable("id") Long id, @Valid ProductEditBindingModel productEditBindingModel,
                        HttpSession httpSession,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeEditBindingModel", productEditBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.productEditBindingModel", bindingResult);

            return "redirect:add";
        }


        productService.updateProduct(modelMapper.map(productEditBindingModel, ProductServiceModel.class));

        return "redirect:details"+id;

    }



    @PostMapping("/add")
    public String add(@Valid ProductAddBindingModel productAddBindingModel,
                      HttpSession httpSession,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:add";
        }


        ProductServiceModel productServiceModel = modelMapper.map(
                productAddBindingModel,
                ProductServiceModel.class);


        productService.createProduct(productServiceModel);


        return "redirect:/";
    }

    @RequestMapping("/delete{id}")
    private String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:all";
    }

}
