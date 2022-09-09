package com.vapers.productservice.controller;

import com.vapers.productservice.dto.ProductDto;
import com.vapers.productservice.repository.ProductEntity;
import com.vapers.productservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto.requestCreate request){
        productService.createProduct(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto.Info>> getProducts(HttpServletRequest request){
        String category = request.getParameter("category");
        String name = request.getParameter("name");

        if(category != null){
            return getProductsByCategory(Integer.parseInt(category));
        }else if (name != null){
            return getProductsByNameContaining(name);
        }else {
            List<ProductDto.Info> productDto = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(productDto);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto.Info> getProductById(@PathVariable("id") Long id){
        ProductDto.Info product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    public ResponseEntity<List<ProductDto.Info>> getProductsByCategory(Integer category){
        List<ProductDto.Info> products = productService.getProductsByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<List<ProductDto.Info>> getProductsByNameContaining(String name){
        List<ProductDto.Info> products = productService.getProductsByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}
