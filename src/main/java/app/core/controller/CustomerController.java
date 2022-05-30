package app.core.controller;

import app.core.exception.CustomerNotfoundException;
import app.core.exception.EmailAlreadyExistException;
import app.core.model.Customer;
import app.core.model.Order;
import app.core.repositoty.CustomerRepo;
import app.core.repositoty.OrderRepo;
import app.core.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private CustomerRepo customerRepo;

    private OrderRepo orderRepo;

    private CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

//    @PostMapping
//    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws EmailAlreadyExistException {
//
//            Boolean c = customerService.findByEmail(customer.getEmail());
//
//        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {

        try{
            Boolean c = customerService.findByEmail(customer.getEmail());
            customerService.saveCustomer(customer);
        }catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

//        return ResponseEntity.ok("added customer: " + customer.getName());
        return new ResponseEntity<>("added customer: " + customer.getName(), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int id) {
        try{
            Customer c =  customerService.findCustomerById(id);
        }catch (CustomerNotfoundException e) {
            // all the returns are the same!
//              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//              return ResponseEntity.notFound().build();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        Customer customer = customerRepo.findById(id).get();
                // all the returns are the same!
//              return ResponseEntity.ok(customer);
//              return ResponseEntity.status(HttpStatus.OK).body(customer);
        return new ResponseEntity<>(customer ,HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public List<Customer> findCustomerByCity(@PathVariable String city) {
        return customerService.findByCity(city);
    }

//    @PostMapping("/order/{id}")
//    public int addOrderToCustomer(@RequestBody Order order, @PathVariable int id) throws CustomerNotfoundException {
//        Order newOrder = orderRepo.save(order);
//        Customer customer = customerService.findCustomerById(id);
//        customer.addOrder(newOrder);
//        customerService.saveCustomer(customer);
//        return newOrder.getId();
//
//    }

    @PostMapping(path = "/order/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addOrderToCustomer(@RequestBody Order order, @PathVariable int id) {
        Order newOrder = orderRepo.save(order);
        newOrder.setDate(LocalDate.now());
        try {
            Customer customer = customerService.findCustomerById(id);
        }catch (CustomerNotfoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        Customer customer = customerRepo.findById(id).get();
        customer.addOrder(newOrder);
        customerService.saveCustomer(customer);
//        return ResponseEntity.ok("added order to customer: " + customer.getName());
        return new ResponseEntity<>("added order to customer: " + customer.getName() , HttpStatus.OK);

    }

    @GetMapping("/all-orders")
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

//    @GetMapping("/getCustomerOrders/{id}")
//    public List<Order> getAllOrdersByCustomer(@PathVariable int id) {
//        Customer c = customerRepo.findById(id).get();
//        return c.getOrders();
//    }

    @GetMapping("/getCustomerOrders/{id}")
    public ResponseEntity<?> getAllOrdersByCustomer(@PathVariable int id) {
       try{
           Customer c = customerService.findCustomerById(id);
       }catch (CustomerNotfoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
      Customer customer = customerRepo.findById(id).get();
       List<Order> orders = customer.getOrders();
       return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) throws CustomerNotfoundException {
            try {
                Customer customer = customerService.findCustomerByEmail(email);
            }catch (CustomerNotfoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            Customer c = customerRepo.findCustomerByEmail(email);
            return new ResponseEntity<>(c, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
//        Customer customer = customerRepo.findById(id).get();
//        if(customer == null) {
////            return ResponseEntity.notFound().build();
//            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
//        }else {
//            customerRepo.deleteById(id);
//        }
//
//        return new ResponseEntity<>("deleted employee with id: " + id, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {

        try{
            Customer customer = customerRepo.findById(id).get();
        }catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
          return new ResponseEntity<>("not found customer with id: " + id, HttpStatus.NOT_FOUND);
        }
        customerRepo.deleteById(id);
        return new ResponseEntity<>("deleted employee with id: " + id, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public Customer findCustomerByName(@PathVariable String name) {
        Customer customer = customerRepo.findCustomerByName(name);
        if(customer != null) {
            return customer;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cannot find username: " + name);

    }


}
