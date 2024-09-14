package com.org.capstone.controller;

import com.org.capstone.entity.Account;
import com.org.capstone.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId(1L);
        // Set other properties of account as needed
    }

    @Test
    public void testAddProduct() throws Exception {
        ResponseEntity<Account> responseEntity = new ResponseEntity<>(account,HttpStatus.CREATED);
        CompletableFuture<Account> accountFuture = CompletableFuture.completedFuture(account);
        when(productService.addProduct(account)).thenReturn(accountFuture);

        CompletableFuture<ResponseEntity<Account>> responseFuture = productController.addProduct(account);
        ResponseEntity<Account> response = responseFuture.get();

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(productService, times(1)).addProduct(account);
    }

    @Test
    public void testGetAllProduct() throws Exception {
        List<Account> accounts = Arrays.asList(account);
        CompletableFuture<List<Account>> futureAccounts = CompletableFuture.completedFuture(accounts);
        when(productService.getAllProduct()).thenReturn(futureAccounts);

        CompletableFuture<List<Account>> resultFuture = productController.getAllProduct();

        List<Account> result = resultFuture.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productService, times(1)).getAllProduct();
    }

    @Test
    public void testGetProduct() {
        when(productService.getProduct(account.getAccountId())).thenReturn(account);

        Account result = productController.getProduct(account.getAccountId());

        assertNotNull(result);
        assertEquals(account, result);
        verify(productService, times(1)).getProduct(account.getAccountId());
    }

    @Test
    public void testUpdateProduct() {
        when(productService.updateProduct(account.getAccountId(), account)).thenReturn(account);

        ResponseEntity<Account> response = productController.updateProduct(account.getAccountId(), account);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(productService, times(1)).updateProduct(account.getAccountId(), account);
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(account.getAccountId());

        productController.deleteProduct(account.getAccountId());

        verify(productService, times(1)).deleteProduct(account.getAccountId());
    }
}