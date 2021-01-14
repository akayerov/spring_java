package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.model.City;
import com.grokonez.jwtauthentication.model.Client;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.model.customer.Customer;
import com.grokonez.jwtauthentication.model.customer.CustomerRepository;
import com.grokonez.jwtauthentication.repository.ClientRepository;
import com.grokonez.jwtauthentication.security.services.UserPrinciple;
import com.grokonez.jwtauthentication.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class TestRestAPIs {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ClientService clientService;
	@Autowired
	CustomerRepository  customerRepository;
//  переменная окружения
	@Value("${test_properties}")
	private String testProperties;


	@GetMapping("/api2/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return ">>> User Contents!";
	}

	@GetMapping("/api2/test/pm")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public String projectManagementAccess() {
		return ">>> Board Management Project";
	}
	
	@GetMapping("/api2/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}

	@GetMapping("/api2/public/test1")
	public String test1() {
		return ">>> Test API";
	}

	@GetMapping("/api2/public/test")
	public String test() {
		return "Test API  test_properties=" + testProperties;
	}

	@GetMapping("/api2/public/client")
	public String clients() {
	  Optional<Client> clients1 = clientRepository.findById(2);
      Client c1 = clients1.get();
      Optional<Client> clients2 = clientRepository.findById(100);
      // не вижу преимуществ типа Optional если его использвоать так
   	  Client c2 = null;
      if( clients2.isPresent() )
         c2 = clients2.get();
      return "OK";
	}

	@GetMapping("/api2/public/clients")
	public List<Client> client() {
		Client client = clientRepository.findByName("Березка");
		Client client1 = clientRepository.findByName("andrey");
		if( client != null) {
			client.setName("Сосна");
			clientRepository.save(client);
		}

		List<Client> clientList = clientRepository.findAll();
		List<Client> clientList1 = clientRepository.findWithOther(1l);
        // использование сервиса, напряму к репозиторию обращаться считается невежливым
		List<Client> clientList2 = clientService.findWithOther(1l);

		Client client3 = clientList1.get(0);
// для ленивой загрузки
		City cityClient3 = client3.getCity();


		return clientList2;
	}

	@GetMapping("/api2/public/customer")
	public Customer customer() {
//		Customer customer = customerRepository.findByCustomerId2(2l);
		Customer customer = customerRepository.findByCustomerId(100l);

		return customer;
	}

// обработка Null при выборке и возврат 404
	@PostMapping("/api2/public/customer/{id}")
	public ResponseEntity<Customer> customerPost(@PathVariable long id) {
//		Customer customer = customerRepository.findByCustomerId2(2l);
		Customer customer = customerRepository.findByCustomerId100(id);
		return (customer != null )
				? new ResponseEntity<>(customer, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		return customer;
	}

	@GetMapping("/api2/public/customers")
	public List<Customer> customers() {
		List<Customer> customers = customerRepository.findAll();

		return customers;
	}


	@GetMapping("/api2/private/customersJoin")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Customer> customersJoun() {
        // извлечние из context пользовпателя который авторизовался
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// В объекте UserPrinciple поля пользователя + права достпуа
		UserPrinciple user = (UserPrinciple) auth.getPrincipal();
		System.out.println("User2=" + auth.getName());



		// рабочий, более простой способ преобразование резултата запроса в объест автоматически по рназванию полей
		// List<Customer> customers = customerRepository.findAllJoin();
        List<Customer> customers = customerRepository.findAllJoin2();


		return customers;
	}

	@GetMapping("/api2/private/whoiam")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String whoiam() {
		// извлечние из context пользовпателя который авторизовался
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// В объекте UserPrinciple поля пользователя + права достпуа
		UserPrinciple user = (UserPrinciple) auth.getPrincipal();
		//System.out.println("User2=" + auth.getName());
		return user.getName()+":"+user.getEmail();
	}

	@GetMapping("/api2/private/whoiam2")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String whoiam2(Authentication auth) {
		UserPrinciple user = (UserPrinciple) auth.getPrincipal();
		//System.out.println("User2=" + auth.getName());
		return user.getName()+":"+user.getEmail();
	}

}