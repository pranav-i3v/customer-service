package com.kanerika.customer.service;

import com.kanerika.customer.dto.CustomerRequest;
import com.kanerika.customer.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request);

    List<CustomerResponse> findAll();

    CustomerResponse findById(Long id);

    CustomerResponse update(Long id, CustomerRequest request);

    void delete(Long id);
}
