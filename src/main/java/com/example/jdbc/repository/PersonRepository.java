package com.example.jdbc.repository;

import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbcaux.operations.DataBase;
import com.example.jdbc.jdbcaux.operations.JdbcExec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Long insert(Person person) throws Exception {
       return  JdbcExec.insert(person, jdbcTemplate, DataBase.MY_SQL);
    }

    public Long update(Person person) throws Exception {
        return JdbcExec.update(person, jdbcTemplate, DataBase.MY_SQL);
     }

     public Person getById(Person person) throws Exception {
        return JdbcExec.selectById(person, jdbcTemplate, DataBase.MY_SQL, Person.class);
     }

    
}
