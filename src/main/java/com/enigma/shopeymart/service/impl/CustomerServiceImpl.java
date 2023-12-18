package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.repository.CustomerRepository;
import com.enigma.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        if (customerRequest.getName().isEmpty() || customerRequest.getPhone().isEmpty()) {
            return null;
        }
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .phone(customerRequest.getPhone())
                .email(customerRequest.getEmail())
                .build();
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public CustomerResponse createNewCustomer(Customer request) {
        Customer customer = customerRepository.saveAndFlush(request);
        return CustomerResponse.builder()
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer = customerRepository.findById(String.valueOf(id)).orElse(null);
        assert customer != null;
        return CustomerResponse.builder()
                .id(id)
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build()).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        if (customerRequest.getName().isEmpty() || customerRequest.getPhone().isEmpty()) {
            return null;
        }
        Customer customer = Customer.builder()
                .id(customerRequest.getId())
                .name(customerRequest.getName())
                .phone(customerRequest.getPhone())
                .email(customerRequest.getEmail())
                .build();
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
