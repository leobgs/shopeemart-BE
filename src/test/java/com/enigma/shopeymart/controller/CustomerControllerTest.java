package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    //tidak pakai @SpringBootTest karena tidak berkaitan dengan client(postman)
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse customerResponse = new CustomerResponse();

        when(customerService.create(customerRequest)).thenReturn(customerResponse);

        CustomerResponse customerResponseActual = customerController.createCustomer(customerRequest);

        assertEquals(customerResponse, customerResponseActual);
    }

    @Test
    void getById() {
        String customerId = "1";
        CustomerResponse customerResponseExpect = new CustomerResponse();

        when(customerService.getById(customerId)).thenReturn(customerResponseExpect);

        CustomerResponse customerResponseActual = customerController.getById(customerId);

        assertEquals(customerResponseExpect, customerResponseActual);
    }

    @Test
    void getAllCustomer() {
        List<CustomerResponse> customerResponsesExpect = new ArrayList<>();

        when(customerService.getAll()).thenReturn(customerResponsesExpect);

        List<CustomerResponse> customerResponsesActual = customerController.getAllCustomer();

        assertEquals(customerResponsesExpect, customerResponsesActual);
    }

    @Test
    void updateCustomer() {
        //todo -> make expect and actual what you want
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse customerResponseExpect = new CustomerResponse();

        //todo -> check your behavior/method on your service and return the response expect
        when(customerService.update(customerRequest)).thenReturn(customerResponseExpect);

        //todo -> check your behavior/method on your controller with using request
        CustomerResponse customerResponseActual = customerController.updateCustomer(customerRequest);

        //todo -> comparing rsponse expext and respnse actual
        assertEquals(customerResponseExpect, customerResponseActual);

    }

    @Test
    void deleteCustomer() {
        String customerId = "1";

        customerController.deleteCustomer(customerId);
        verify(customerService, times(1)).delete(customerId);
    }
}