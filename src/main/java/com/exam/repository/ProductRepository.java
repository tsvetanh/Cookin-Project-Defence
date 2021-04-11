package com.exam.repository;

import com.exam.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("update Product p set p.name = :name, p.price = :price, p.imgUrl = :imgUrl where p.id = :id")
    void updateProduct(@Param("id") Long id, @Param("name") String name, @Param("price")BigDecimal price, @Param("imgUrl")String imgUrl);

    Product getByName(String productName);
}
