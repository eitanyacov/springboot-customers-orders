package app.core.repositoty;

import app.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
    List<Customer> findCustomerByCity(String city);
    Customer findCustomerByEmail(String email);
    Customer findCustomerByName(String name);
}
