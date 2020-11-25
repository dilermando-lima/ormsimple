package com.example.jdbc;

import com.example.jdbc.entity.Person;
import com.example.jdbc.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


 @SpringBootApplication 
 public class JdbcApplication implements CommandLineRunner{
 

  @Autowired private ApplicationContext applicationContext;
 

  public static void main(String[] args) {
	SpringApplication.run(JdbcApplication.class, args);
  }

  @Override public void run(String... args) throws Exception {
	PersonRepository rep = applicationContext.getBean(PersonRepository.class);

	// var p =  new Person(53l, "nome allterado 2", "Nome", LocalDate.now(), 7l, 10, 4, LocalDateTime.now()) ;
	// p.setContact(new Contact(2l, "email@email", "199292929"));

	var p = new Person();
	p.setId(54l);
	 
	p = rep.getById(p);

	// TODO: solve getByid is not bringing id and id fk. check prepare stringBuilt

	System.out.println(p.getName());

  }


 }