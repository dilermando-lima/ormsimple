package com.example.jdbc;

import java.time.LocalDateTime;
import java.util.Arrays;
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
		PersonRepository repository =  applicationContext.getBean(PersonRepository.class );

	
	/* ========= creating tables ==================================*/

		String deleteTablePerson =  " drop table if exists person " ;
		String deleteTableContact =  " drop table if exists contact; " ;
		String createTablePersonStript = "create table person (  id int not null auto_increment primary key, name varchar(255) , obs varchar(255) , date_insert datetime not null ) ";
		String createTableContactStript = "create table contact (  id int not null auto_increment primary key,  email varchar(255) ,  phone varchar(255) ,  id_person int not null   ) ";

		repository.exec( deleteTablePerson );
		repository.exec( deleteTableContact );
		repository.exec( createTablePersonStript );
		repository.exec( createTableContactStript );

	/* ========= inserting one  ==================================*/

		repository.insert(new Person("first person", "some comments", LocalDateTime.now()));

	/* ========= inserting batch  ==================================*/
		repository.insert(Arrays.asList(
			new Person("second person on batch", "some comments", LocalDateTime.now()),
			new Person("third person on batch", "some comments", LocalDateTime.now()),
			new Person("forth person on batch", "some comments", LocalDateTime.now())
		));

	/* ========= select one  ==================================*/
		Person person1 = repository.getById(new Person(1l));

		person1.setName("name has been changed");

	/* ========= update one  ==================================*/
		repository.update(person1);

	/* ========= select data  ==================================*/
		Select select = new Select(0, 1000)
								.col("name")
								.col("obs")
								.col("date_insert")
								.from("person")
								.orderBy("id", Select.ASC);

		List<Person> listPerson = repository.select(select);

		System.out.println(listPerson.toString());



		




						

		



	
	}

}