package com.sportsshoes.controller;


import com.sportsshoes.bean.Product;
import com.sportsshoes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService service;


    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestParam("name") String name, @RequestParam("description") String description,
                                             @RequestParam("brand") String brand, @RequestParam("price") double price, @RequestParam("category") String category,
                                             @RequestParam("image")MultipartFile file) throws IOException {
        String response= service.addProduct(name, description, brand, price, category, file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable("name") String name) throws IOException {
        byte[] image = service.getProductImage(name);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("category") String category) {
        List<Product> products = service.getProductByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductByBrand(@PathVariable("brand") String brand) {
        List<Product> products = service.getProductByBrand(brand);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
