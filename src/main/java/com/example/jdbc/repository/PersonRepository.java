package com.example.jdbc.repository;

import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbcaux.model.DataBaseMySql;
import com.example.jdbc.jdbcaux.repository.JdbcRepository;

import org.springframework.stereotype.Component;

@Component
public class PersonRepository extends  JdbcRepository<Person,String, DataBaseMySql>  {


}
