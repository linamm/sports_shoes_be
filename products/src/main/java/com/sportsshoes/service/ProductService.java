package com.sportsshoes.service;

import com.sportsshoes.bean.Product;
import com.sportsshoes.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ProductService {
    private static final String FOLDER_PATH = "/Users/lina.shepherd/Downloads/images";

    @Autowired
    private ProductRepository productRepository;

    public String addProduct(String  name, String description, String brand, Double price, String category, Integer quantity, MultipartFile file) throws IOException {

        File directory = new File(FOLDER_PATH);

        if (!directory.exists()) {
            directory.mkdir();
        }

        File destinationFile = new File(directory, file.getOriginalFilename());
        file.transferTo(destinationFile);

        Product product=new Product(name, description, brand, category, price, quantity, file.getOriginalFilename());
        product.setName(name);
        product.setDescription(description);
        product.setImagePath(file.getOriginalFilename());

        productRepository.save(product);
        return "Product uploaded successfully";

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    public byte[] getProductImage(String name) throws IOException {
        File imageFile = new File(FOLDER_PATH + "/" + name);

        if (!imageFile.exists()) {
            throw new IOException("Image not found");
        }

        return Files.readAllBytes(imageFile.toPath());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product existingProduct) {
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Product existingProduct) {
        productRepository.delete(existingProduct);
    }
}
