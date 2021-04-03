package com.exam.service.impl;

import com.exam.model.entities.Product;
import com.exam.repository.ProductRepository;
import com.exam.service.ProductService;
import com.exam.view.ProductViewModel;
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
        System.out.println(products);

        products.forEach(product -> {
            ProductViewModel productViewModel = new ProductViewModel();
            modelMapper.map(product, productViewModel);
            productViewModel.setName(product.getName());
            viewModels.add(productViewModel);
        });

        return viewModels;
    }
}
