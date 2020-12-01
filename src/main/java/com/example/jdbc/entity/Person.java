package com.example.jdbc.entity;

import java.time.LocalDateTime;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcColumnSelect;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;



@JdbcTable("person")
public class Person {



    @JdbcColumnSelect("id")
    @JdbcIdentity("id")
    private Long id;

    @JdbcColumnSelect("name")
    @JdbcColumn("name")
    private String name;

    @JdbcColumnSelect("obs")
    @JdbcColumn("obs")
    private String obs;

    @JdbcColumnSelect("date_insert")
    @JdbcColumn("date_insert")
    private LocalDateTime dateInsert;


    @Override
    public String toString() {
        return String.format("{ id: %s, name: %s , obs: %s, date_insert: %s } ", this.id, this.name, this.obs, this.dateInsert);
    }

    public Person(){}

    public Person(Long id) {
        this.id = id;
    }

    public Person(String name, String obs, LocalDateTime dateInsert) {
        this.name = name;
        this.obs = obs;
        this.dateInsert = dateInsert;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public LocalDateTime getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(LocalDateTime dateInsert) {
        this.dateInsert = dateInsert;
    }



    
    


}
