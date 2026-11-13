package com.kanerika.customer.service;

import com.kanerika.customer.dto.CustomerRequest;
import com.kanerika.customer.dto.CustomerResponse;
import com.kanerika.customer.entity.Customer;
import com.kanerika.customer.exception.CustomerNotFoundException;
import com.kanerika.customer.repository.CustomerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        Customer customer = customerRepository.save(new Customer(request.name().trim()));
        return toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(CustomerServiceImpl::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        return toResponse(getCustomer(id));
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = getCustomer(id);
        customer.rename(request.name().trim());
        return toResponse(customerRepository.save(customer));
    }

    @Override
    public void delete(Long id) {
        customerRepository.delete(getCustomer(id));
    }

    private Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName());
    }
}
