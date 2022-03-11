package app.core;

import app.core.model.Customer;
import app.core.repositoty.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerOrdersApplication {

	public static void main(String[] args) {
				SpringApplication.run(CustomerOrdersApplication.class, args);



	}


}
