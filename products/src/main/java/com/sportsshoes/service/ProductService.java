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

    public String addProduct(String  name, String description, String brand, double price, String category, MultipartFile file) throws IOException {

        File directory = new File(FOLDER_PATH);

        if (!directory.exists()) {
            directory.mkdir();
        }

        File destinationFile=new File(directory, file.getOriginalFilename());
        file.transferTo(destinationFile);

        Product product=new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImagePath(file.getOriginalFilename());

        productRepository.save(product);
        return "Product uploaded successfully";

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public byte[] getProductImage(String name) throws IOException {
        File imageFile = new File(FOLDER_PATH + "/" + name);

        if (!imageFile.exists()) {
            throw new IOException("Image not found");
        }

        return Files.readAllBytes(imageFile.toPath());
    }
}
