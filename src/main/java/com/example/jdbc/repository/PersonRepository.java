package com.example.jdbc.repository;

import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.repository.JdbcRepository;

import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository extends JdbcRepository<Person,Long>  {

     public PersonRepository(){ setDataBase(DataBase.MY_SQL); } 
     
}
