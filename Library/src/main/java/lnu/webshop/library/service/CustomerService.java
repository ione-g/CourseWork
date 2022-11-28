package lnu.webshop.library.service;

import lnu.webshop.library.dto.CustomerDto;
import lnu.webshop.library.model.Customer;

public interface CustomerService {

    CustomerDto save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer saveInfor(Customer customer);
}
