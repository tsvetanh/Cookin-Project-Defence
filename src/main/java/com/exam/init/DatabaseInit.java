package com.exam.init;

import com.exam.repository.ProductRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Component
public class DatabaseInit implements CommandLineRunner {

    private ProductRepository productRepository;

    public DatabaseInit(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("E:/players_data.json"));
            String str = "{ \"number\": [3, 4, 5, 6] }";
            JSONObject obj = new JSONObject(str);
            JSONArray arr = obj.getJSONArray("number");
            for (int i = 0; i < arr.length(); i++)
                System.out.println(arr.getInt(i));
        }
    }
}
