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

    // Update Product API
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product productDetails) {
        // Retrieve the existing product by ID
        Product existingProduct = service.getProductById(id);

        // If the product is not found, return a 404 response
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the existing product's fields with new data
        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setDescription(productDetails.getDescription());

        // Save the updated product
        Product updatedProduct = service.saveProduct(existingProduct);

        // Return the updated product with status 200 OK
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete Product API
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        // Retrieve the existing product by ID
        Product existingProduct = service.getProductById(id);

        // If the product is not found, return a 404 response
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete the existing product
        service.deleteProduct(existingProduct);

        // Return a 200 response
        return ResponseEntity.ok().build();
    }
}
