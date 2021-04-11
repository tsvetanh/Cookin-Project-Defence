package com.exam.init;

import com.exam.model.entities.Product;
import com.exam.model.entities.Recipe;
import com.exam.model.entities.User;
import com.exam.model.entities.UserRole;
import com.exam.model.entities.emuns.Difficulty;
import com.exam.model.entities.emuns.Role;
import com.exam.model.service.UserServiceModel;
import com.exam.repository.ProductRepository;
import com.exam.repository.RecipeRepository;
import com.exam.repository.UserRepository;
import com.exam.repository.UserRoleRepository;
import com.exam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class DatabaseInit implements CommandLineRunner {

    private ProductRepository productRepository;
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private UserService userService;

    public DatabaseInit(ProductRepository productRepository, RecipeRepository recipeRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, UserService userService) {
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole().setRole(Role.ADMIN);
            UserRole userRole = new UserRole().setRole(Role.USER);
            userRoleRepository.saveAll(List.of(adminRole, userRole));

            UserServiceModel user = new UserServiceModel();

            user.setUsername("admin");
            user.setPassword("123456789");
            user.setEmail("admin@admin");
            userService.register(user, true);
        }

        if (recipeRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            JSONParser jsonParser = new JSONParser(new FileReader("src/main/resources/recipes.json"));
            List<Object> recipes = jsonParser.parseArray();


            for (Object recipe : recipes) {

                LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) recipe;

                Recipe newRecipe = new Recipe();
                newRecipe.setName((String) hashMap.get("name"));
                newRecipe.setImgUrl((String) hashMap.get("imgUrl"));
                newRecipe.setDifficulty(Difficulty.valueOf((String) hashMap.get("difficulty")));

                List<String> products = (List<String>) hashMap.get("products");
                List<Product> currProducts = new ArrayList<>();
                for (String productName : products) {
                    if (!productRepository.existsByName(productName)) {
                        Product newProduct = new Product();
                        newProduct.setName(productName);
                        BigDecimal price = BigDecimal.valueOf(Math.random() * (15 - 0.50 + 1) + 0.50);
                        newProduct.setPrice(price);
                        currProducts.add(newProduct);
                        productRepository.save(newProduct);
                    } else {
                        currProducts.add(productRepository.getByName(productName));
                    }
                }
                newRecipe.setProducts(currProducts);
                newRecipe.setDescription((String) hashMap.get("description"));
                newRecipe.setAddedBy((String) hashMap.get("added_by_id"));

                recipeRepository.save(newRecipe);
            }
        }
    }
}
