package com.example.jdbc;

import java.util.List;

import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbcaux.model.Select;
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

	public static void main(String[] args) throws Exception {

		SpringApplication.run(JdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PersonRepository rep = applicationContext.getBean(PersonRepository.class);

		Select select =  new Select(0,500)
								.col("name")
								.col("id")
								.col("intnum")
							.from("person")
							.andWhere(" intnum = ? ", 4)
							.andWhere(" obs like ? ", "%a%")
							.orderBy("id", Select.ASC);
		List<Person> list = rep.select(select);


		System.out.println(list.toString());
		

	
	}

}