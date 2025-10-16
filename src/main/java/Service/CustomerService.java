package Service;

import DTO.CustomerDTO.CustomerRequestDTO;
import DTO.CustomerDTO.CustomerResponseDTO;
import Repository.CustomerRepository;

public class CustomerService {

    private final CustomerRepository customerRepository=new CustomerRepository();

    public CustomerRequestDTO updateCustomer(CustomerRequestDTO body, String customerId) {

        CustomerRequestDTO customer=customerRepository.findById(customerId);

        return null;
    }
    public CustomerResponseDTO getCustomerProfileById(int customerId) {
        return customerRepository.findCustomerProfileById(customerId);
    }
}
