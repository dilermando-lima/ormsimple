package com.example.jdbc.repository;

import java.util.List;
import java.util.Map;

import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.Select;
import com.example.jdbc.jdbcaux.operations.JdbcExec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository{


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Long insert(Person person) throws Exception {
       return  JdbcExec.insert(person, jdbcTemplate, DataBase.MY_SQL);
    }

    public Long update(Person person) throws Exception {
        return JdbcExec.update(person, jdbcTemplate, DataBase.MY_SQL);
     }

     public Long updatePatch(Person person, Map<String,Object> mapValues) throws Exception {
      return JdbcExec.updatePatch(person, mapValues, jdbcTemplate, DataBase.MY_SQL);
   }

     public Person getById(Person person) throws Exception {
        return JdbcExec.selectById(person, jdbcTemplate, DataBase.MY_SQL, Person.class);
     }

     public List<Person> select (Select select ) throws Exception {
      return JdbcExec.select(select, jdbcTemplate,  DataBase.MY_SQL, Person.class);
   }

     public Long delete(Person person) throws Exception {
      return JdbcExec.delete(person, jdbcTemplate, DataBase.MY_SQL);
   }

   public List<Integer> insert(List<Person> listPerson) throws Exception {
      return JdbcExec.insertBatch(  listPerson, jdbcTemplate, DataBase.MY_SQL);
   }


}
