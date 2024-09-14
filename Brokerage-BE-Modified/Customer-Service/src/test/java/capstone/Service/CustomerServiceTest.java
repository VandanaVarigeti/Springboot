package capstone.Service;


import capstone.Entity.NewCustomerDto;
import capstone.Repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceTest {


    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer_ShouldAddCustomer_WhenCustomerDoesNotExist() {
        NewCustomerDto customer = new NewCustomerDto();
        customer.setSsnNumber(123456789L);

        when(customerRepository.customerExists(customer.getSsnNumber())).thenReturn(false);
        when(customerRepository.save(customer)).thenReturn(customer);

        customerService.addCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void addCustomer_ShouldThrowException_WhenCustomerAlreadyExists() {
        NewCustomerDto customer = new NewCustomerDto();
        customer.setSsnNumber(123456789L);

        when(customerRepository.customerExists(customer.getSsnNumber())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer(customer));
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer_WhenCustomerExists() {
        NewCustomerDto customer = new NewCustomerDto();
        customer.setSsnNumber(123456789L);

        when(customerRepository.customerExists(customer.getSsnNumber())).thenReturn(true);
        when(customerRepository.save(customer)).thenReturn(customer);

        NewCustomerDto updatedCustomer = customerService.updateCustomer(customer.getSsnNumber(), customer);

        assertEquals(customer, updatedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void updateCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        NewCustomerDto customer = new NewCustomerDto();
        customer.setSsnNumber(123456789L);

        when(customerRepository.customerExists(customer.getSsnNumber())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(customer.getSsnNumber(), customer));
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer_WhenCustomerExists() {
        NewCustomerDto customer = new NewCustomerDto();
        customer.setSsnNumber(123456789L);

        when(customerRepository.findById(customer.getSsnNumber())).thenReturn(Optional.of(customer));

        String result = customerService.deleteCustomer(customer.getSsnNumber());

        assertEquals("Customer deleted Successfully", result);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        Long ssnNumber = 123456789L;

        when(customerRepository.findById(ssnNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> customerService.deleteCustomer(ssnNumber));
    }

    @Test
    void getAllCustomer_ShouldReturnAllCustomers() {
        NewCustomerDto customer1 = new NewCustomerDto();
        NewCustomerDto customer2 = new NewCustomerDto();

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<NewCustomerDto> customers = customerService.getAllCustomer();

        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_ShouldReturnCustomer_WhenCustomerExists() {
        NewCustomerDto customer = new NewCustomerDto();
        customer.setSsnNumber(123456789L);

        when(customerRepository.findById(customer.getSsnNumber())).thenReturn(Optional.of(customer));

        NewCustomerDto foundCustomer = customerService.getCustomerById(customer.getSsnNumber());

        assertEquals(customer, foundCustomer);
    }

    @Test
    void getCustomerById_ShouldThrowException_WhenCustomerDoesNotExist() {
        Long ssnNumber = 123456789L;

        when(customerRepository.findById(ssnNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> customerService.getCustomerById(ssnNumber));
    }
}



