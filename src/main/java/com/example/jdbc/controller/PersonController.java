package com.example.jdbc.controller;

import java.util.List;

import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbcaux.model.Select;
import com.example.jdbc.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/person")
public class PersonController {
    

    @Autowired
    private PersonRepository repository;

    @GetMapping
    public  List<Person> list() throws Exception {
        return repository.select(new Select(0, 100).col("id","name","obs").from("person").orderBy("id", Select.DESC ));
    }

    @GetMapping("/{id}")
    public  Person getById(@PathVariable("id") Long id  ) throws Exception {
        return  repository.getById(new Person(id));
    }

    @PostMapping
    public  Person insert(@RequestBody Person person  ) throws Exception {
        return  repository.getById( new Person(repository.insert(person)) );
    }

    @PutMapping("/{id}")
    public  Person update(@PathVariable("id") Long id, @RequestBody Person person  ) throws Exception {
        person.setId(id);
        return  repository.getById( new Person(repository.update(person)) );
    }

    @DeleteMapping("/")
    public  Long delete(@RequestBody Person person  ) throws Exception {
        return  repository.delete(person);
    }


}
