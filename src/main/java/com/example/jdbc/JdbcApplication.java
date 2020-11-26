package com.example.jdbc;

import java.util.ArrayList;
import java.util.List;

import com.example.jdbc.entity.Person;
import com.example.jdbc.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JdbcApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PersonRepository rep = applicationContext.getBean(PersonRepository.class);

		// var p = new Person(53l, "nome allterado 2", "Nome", LocalDate.now(), 7l, 10,
		// 4, LocalDateTime.now()) ;
		// p.setContact(new Contact(2l, "email@email", "199292929"));

		

		List<Person> list = new ArrayList<>();
		


		list.add( new Person( null, "nome 1", "obs 1", null, null, null, null, null) );
		list.add( new Person( null, "nome 2", "obs 1", null, null, null, 2, null) );
		list.add( new Person( null, "nome 3", "obs 3", null, null, null, null, null) );
		list.add( new Person( null, "nome 4", "obs 1", null, null, null, 4, null) );
		list.add( new Person( null, "nome 5", "obs 5", null, null, null, null, null) );
		list.add( new Person( null, "nome 6", "obs 1", null, null, null, null, null) );

	
		System.out.println(rep.insert(list));

	}

}