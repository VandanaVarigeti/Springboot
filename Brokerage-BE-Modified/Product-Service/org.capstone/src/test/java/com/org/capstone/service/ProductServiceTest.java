package com.org.capstone.service;

import com.org.capstone.entity.Account;
import com.org.capstone.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

//    @Mock
//    private CustAccRoleService custAccRoleService;

    @InjectMocks
    private ProductService productService;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId(1L);
        // Set other properties of account as needed
    }

    @Test
    public void testAddProduct() throws Exception {
        when(productRepository.save(account)).thenReturn(account);

        CompletableFuture<Account> savedAccountFuture = productService.addProduct(account);

        Account savedAccount = savedAccountFuture.get();

        assertNotNull(savedAccount);
        assertEquals(account.getAccountId(), savedAccount.getAccountId());
        verify(productRepository, times(1)).save(account);
      //  verify(custAccRoleService, times(1)).createCustAccRole(savedAccount);
    }

    @Test
    public void testGetAllProduct() throws Exception {
        List<Account> accounts = Arrays.asList(account);
        when(productRepository.findAll()).thenReturn(accounts);

        CompletableFuture<List<Account>> resultFuture = productService.getAllProduct();
        List<Account> result = resultFuture.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.productExists(account.getAccountId())).thenReturn(true);
        when(productRepository.save(account)).thenReturn(account);

        Account updatedAccount = productService.updateProduct(account.getAccountId(), account);

        assertNotNull(updatedAccount);
        assertEquals(account.getAccountId(), updatedAccount.getAccountId());
        verify(productRepository, times(1)).save(account);
    }

    @Test
    public void testUpdateProduct_NotFound() {
        when(productRepository.productExists(account.getAccountId())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(account.getAccountId(), account);
        });
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        productService.deleteProduct(account.getAccountId());

        verify(productRepository, times(1)).delete(account);
    }

    @Test
    public void testDeleteProduct_NotFound() {
        when(productRepository.findById(account.getAccountId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productService.deleteProduct(account.getAccountId());
        });
    }

    @Test
    public void testGetProduct() {
        when(productRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        Account foundAccount = productService.getProduct(account.getAccountId());

        assertNotNull(foundAccount);
        assertEquals(account.getAccountId(), foundAccount.getAccountId());
        verify(productRepository, times(1)).findById(account.getAccountId());
    }

    @Test
    public void testGetProduct_NotFound() {
        when(productRepository.findById(account.getAccountId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productService.getProduct(account.getAccountId());
        });
    }
}