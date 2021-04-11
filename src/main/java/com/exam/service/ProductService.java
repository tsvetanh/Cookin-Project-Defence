package com.exam.service;

import com.exam.model.entities.Product;
import com.exam.model.service.ProductServiceModel;
import com.exam.view.ProductViewModel;
import com.exam.view.RecipeViewModel;

import java.util.List;

public interface ProductService {
   List<ProductViewModel> getAllProducts();

    Product getProductByName(String name);

    void createProduct(ProductServiceModel productServiceModel);

    void updateProduct(ProductServiceModel productServiceModel);

    ProductViewModel findById(Long id);

    void deleteById(Long id);
}
