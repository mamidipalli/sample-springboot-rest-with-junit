package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void addProduct() throws Exception {

        Product mockProduct = new Product("S1","SKU1", "Shirt 1","Shirt Description","M","White");

	Mockito.when(productService.addProduct(Mockito.any(Product.class))).thenReturn(mockProduct);
        
        String product = "{\"id\":\"S1\",\"sku\":\"SKU1\",\"name\":\"Shirt 1\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"White\"}";

        RequestBuilder request = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(product).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("Product added successfully", response.getContentAsString());
    }
    
    @Test
    public void getEmptyList() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("{\"All\":[]}", response.getContentAsString());
    }

    @Test
    public void getProductList() throws Exception {
        
        List<Product> products = new ArrayList<Product>();
        Product mockProduct = new Product("S1","SKU1", "Shirt 1","Shirt Description","M","White");
        products.add(mockProduct);
        mockProduct = new Product("S2","SKU2", "Shirt 2","Shirt Description","L","Black");
        products.add(mockProduct);
        Mockito.when(productService.getProductList()).thenReturn(products);

        String product1 = "{\"id\":\"S1\",\"sku\":\"SKU1\",\"name\":\"Shirt 1\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"White\"}";
        String product2 = "{\"id\":\"S2\",\"sku\":\"SKU2\",\"name\":\"Shirt 2\",\"description\":\"Shirt Description\",\"size\":\"L\",\"color\":\"Black\"}";
        String expected = "[" + product1 + "," + product2 + "]";

        RequestBuilder getRequest = MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(getRequest).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(expected, response.getContentAsString());
    }

    @Test
    public void testInvalidProductID() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/products/SF1").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertNull(null, response.getContentAsString());
    }

    @Test
    public void getProduct() throws Exception {
        Product mockProduct = new Product("S1","SKU1","Shirt 1","Shirt Description","M","White");

	Mockito.when(productService.getProduct(Mockito.anyString())).thenReturn(mockProduct);
        
        String product = "{\"id\":\"S1\",\"sku\":\"SKU1\",\"name\":\"Shirt 1\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"White\"}";

        RequestBuilder request = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(product).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request);

        request = MockMvcRequestBuilders.get("/products/S1").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(product, response.getContentAsString());
    }

    @Test
    public void getProductListGroupByColor() throws Exception {
        String whiteProduct1 = "{\"id\":\"S1\",\"sku\":\"SKU1\",\"name\":\"Shirt 1\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"White\"}";

        RequestBuilder postRequest1 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(whiteProduct1).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest1);

        String blackProduct1 = "{\"id\":\"S2\",\"sku\":\"SKU2\",\"name\":\"Shirt 2\",\"description\":\"Shirt Description\",\"size\":\"L\",\"color\":\"Black\"}";

        RequestBuilder postRequest2 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(blackProduct1).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest2);

        String whiteProduct2 = "{\"id\":\"S4\",\"sku\":\"SKU4\",\"name\":\"Shirt 4\",\"description\":\"Shirt Description\",\"size\":\"L\",\"color\":\"White\"}";

        RequestBuilder postRequest3 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(whiteProduct2).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest3);

        String blackProduct2 = "{\"id\":\"S3\",\"sku\":\"SKU3\",\"name\":\"Shirt 3\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"Black\"}";

        RequestBuilder postRequest4 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(blackProduct2).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest4);

        String expected = "{\"Color Group\":[" + whiteProduct1 + "," + whiteProduct2 + "]}";

        RequestBuilder getRequest = MockMvcRequestBuilders.get("/products?color=White").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(getRequest).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(expected, response.getContentAsString());
    }

    @Test
    public void getProductListGroupBySize() throws Exception {
        String mediumProduct1 = "{\"id\":\"S1\",\"sku\":\"SKU1\",\"name\":\"Shirt 1\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"White\"}";

        RequestBuilder postRequest1 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(mediumProduct1).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest1);

        String largeProduct1 = "{\"id\":\"S2\",\"sku\":\"SKU2\",\"name\":\"Shirt 2\",\"description\":\"Shirt Description\",\"size\":\"L\",\"color\":\"Black\"}";

        RequestBuilder postRequest2 = MockMvcRequestBuilders.post("/products?size=M").accept(MediaType.APPLICATION_JSON)
                .content(largeProduct1).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest2);

        String largeProduct2 = "{\"id\":\"S4\",\"sku\":\"SKU4\",\"name\":\"Shirt 4\",\"description\":\"Shirt Description\",\"size\":\"L\",\"color\":\"White\"}";

        RequestBuilder postRequest3 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(largeProduct2).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest3);

        String mediumProduct2 = "{\"id\":\"S3\",\"sku\":\"SKU3\",\"name\":\"Shirt 3\",\"description\":\"Shirt Description\",\"size\":\"M\",\"color\":\"Black\"}";

        RequestBuilder postRequest4 = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
                .content(mediumProduct2).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRequest4);

        String expected = "{\"Size Group\":[" + mediumProduct1 + "," + mediumProduct2 + "]}";

        RequestBuilder getRequest = MockMvcRequestBuilders.get("/products?size=M").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(getRequest).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(expected, response.getContentAsString());
    }
}