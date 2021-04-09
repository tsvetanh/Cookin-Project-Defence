package com.exam.service;

import com.exam.model.entities.Product;
import com.exam.view.ProductViewModel;

import java.util.List;

public interface ProductService {
   List<ProductViewModel> getAllProducts();

    Product getProductByName(String name);
}
