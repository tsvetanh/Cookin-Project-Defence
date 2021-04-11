package com.exam.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductAddBindingModel {
    private String name;
    private String imgUrl;
    private BigDecimal price;

    public ProductAddBindingModel() {
    }

    @NotBlank(message = "Name cannot be empty string")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "Image URL   cannot be empty string")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @NotBlank(message = "Price cannot be empty")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}