package com.vapers.productservice.controller;

import com.vapers.productservice.dto.ProductDto;
import com.vapers.productservice.repository.ProductEntity;
import com.vapers.productservice.service.ProductService;
import com.vapers.productservice.vo.RequestCreateProduct;
import com.vapers.productservice.vo.ResponseProduct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    private Environment env;
    private ProductService productService;

    @Autowired
    public ProductController(Environment env, ProductService productService) {
        this.env = env;
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody RequestCreateProduct product){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ProductDto productDto = mapper.map(product, ProductDto.class);
        productService.createProduct(productDto);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ResponseProduct>> getProducts(HttpServletRequest request){
        String category = request.getParameter("category");
        String name = request.getParameter("name");

        if(category != null){
            return getProductsByCategory(Integer.parseInt(category));
        }else if (name != null){
            return getProductsByNameContaining(name);
        }else {
            Iterable<ProductEntity> productEntities = productService.getProductByAll();

            List<ResponseProduct> result = new ArrayList<>();
            productEntities.forEach(v -> {
                result.add(new ModelMapper().map(v, ResponseProduct.class));
            });

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ResponseProduct> getProductById(@PathVariable("id") Long id){
        ProductEntity productEntity = productService.getProductById(id);
        ResponseProduct result = new ModelMapper().map(productEntity, ResponseProduct.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<List<ResponseProduct>> getProductsByCategory(Integer category){
        Iterable<ProductEntity> productEntities = productService.getProductByCategory(category);

        List<ResponseProduct> result = new ArrayList<>();
        productEntities.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseProduct.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<List<ResponseProduct>> getProductsByNameContaining(String name){
        Iterable<ProductEntity> productEntities = productService.getProductByNameContaining(name);

        List<ResponseProduct> result = new ArrayList<>();
        productEntities.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseProduct.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
