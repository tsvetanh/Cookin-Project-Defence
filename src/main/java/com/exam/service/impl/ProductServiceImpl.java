package com.exam.service.impl;

import com.exam.model.entities.Menu;
import com.exam.model.entities.Product;
import com.exam.model.service.ProductServiceModel;
import com.exam.repository.ProductRepository;
import com.exam.service.ProductService;
import com.exam.view.ProductViewModel;
import com.exam.view.RecipeViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ModelMapper modelMapper;
    private ProductRepository productRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductViewModel> getAllProducts() {
        List<ProductViewModel> viewModels = new ArrayList<>();

        List<Product> products = productRepository.findAll();

        products.forEach(product -> {
            ProductViewModel productViewModel = new ProductViewModel();
            modelMapper.map(product, productViewModel);
            productViewModel.setName(product.getName());
            viewModels.add(productViewModel);
        });

        return viewModels;
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.getByName(name);
    }

    @Override
    public void createProduct(ProductServiceModel productServiceModel) {
        Product product = modelMapper.map(productServiceModel, Product.class);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductServiceModel p) {
        productRepository.updateProduct(p.getId(), p.getName(), p.getPrice(), p.getImgUrl());
    }

    @Override
    public ProductViewModel findById(Long id) {
        return productRepository
                .findById(id)
                .map(recipeEntity -> {
                    return modelMapper
                            .map(recipeEntity, ProductViewModel.class);
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


}
