package app.core.service;

import app.core.exception.CustomerNotfoundException;
import app.core.exception.EmailAlreadyExistException;
import app.core.model.Customer;
import app.core.repositoty.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerService {


    private CustomerRepo customerRepo;

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer findCustomerById(int id) throws CustomerNotfoundException {
        Optional<Customer> opt = customerRepo.findById(id);
            if(opt.isPresent()) {
                return opt.get();
            }else {
                throw new CustomerNotfoundException("Customer with id: " + id + " was not found");
            }
    }

    public List<Customer> findByCity(String city) {
        return customerRepo.findCustomerByCity(city);
    }

    // this method is checking if email is already exist, if the email is not existing we return true
    // if we can find customer with this email, we throw exception.
    public Boolean findByEmail(String email) throws EmailAlreadyExistException {
       Customer c = customerRepo.findCustomerByEmail(email);
       if(c != null) {
           throw new EmailAlreadyExistException("email already exist");
       }
       return true;

    }

    public Customer findCustomerByEmail(String email) throws CustomerNotfoundException {
        Customer customer = customerRepo.findCustomerByEmail(email);
        if(customer == null) {
            throw new CustomerNotfoundException("cannot find customer with email: " + email);
        }
        return customer;
    }
}
