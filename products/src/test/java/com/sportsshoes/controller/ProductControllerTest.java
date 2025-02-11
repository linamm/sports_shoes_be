package com.sportsshoes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsshoes.bean.Product;
import com.sportsshoes.exceptions.ResourceNotFoundException;
import com.sportsshoes.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testAddProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "dummy".getBytes());
        when(service.addProduct(anyString(), anyString(), anyString(), anyDouble(), anyString(), anyInt(), any(MultipartFile.class)))
                .thenReturn("Product added successfully!");

        mockMvc.perform(multipart("/products/add")
                        .file(file)
                        .param("name", "Test Product")
                        .param("description", "Test Description")
                        .param("brand", "Test Brand")
                        .param("price", "99.99")
                        .param("category", "Electronics")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully!"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(1L, "Test Product", "Test Description", "Test Brand", 99.99, "Electronics", 10, null));
        when(service.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(products.size()));
    }

    @Test
    void testGetProductImage() throws Exception {
        byte[] imageBytes = new byte[]{1, 2, 3};
        when(service.getProductImage("test.jpg")).thenReturn(imageBytes);

        mockMvc.perform(get("/products/image/test.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageBytes));
    }

    @Test
    void testGetProductByCategory() throws Exception {
        List<Product> products = Arrays.asList(new Product(1L, "Test Product", "Test Description", "Test Brand", 99.99, "Electronics", 10, null));
        when(service.getProductByCategory("Electronics")).thenReturn(products);

        mockMvc.perform(get("/products/category/Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(products.size()));
    }

    @Test
    void testGetProductByBrand() throws Exception {
        List<Product> products = Arrays.asList(new Product(1L, "Test Product", "Test Description", "Test Brand", 99.99, "Electronics", 10, null));
        when(service.getProductByBrand("Test Brand")).thenReturn(products);

        mockMvc.perform(get("/products/brand/Test Brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(products.size()));
    }

    @Test
    void testUpdateProduct() throws Exception {

        Product existingProduct = new Product(1L, "Old Name", "Old Description", "Old Brand", 50.0, "Old Category", 5, null);
        Product updatedProduct = new Product(1L, "New Name", "New Description", "New Brand", 100.0, "New Category", 10, null);

        when(service.getProductById(1L)).thenReturn(existingProduct);
        when(service.saveProduct(any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProduct))) // Send updated product as JSON
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = new Product(1L, "Test Product", "Test Description", "Test Brand", 99.99, "Electronics", 10, null);

        when(service.getProductById(1L)).thenReturn(product);
        doNothing().when(service).deleteProduct(any(Product.class));

        mockMvc.perform(delete("/products/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        when(service.getProductById(1L)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(delete("/products/delete/1"))
        );

        // Verify the exception contains the expected message
        assertTrue(exception.getCause() instanceof ResourceNotFoundException);
        assertEquals("Product not found with id: 1", exception.getCause().getMessage());
    }
}
