package com.example.jdbc.entity;

import java.time.LocalDateTime;

import com.example.jdbc.jdbc.annotation.Col;
import com.example.jdbc.jdbc.annotation.ColSelect;
import com.example.jdbc.jdbc.annotation.Id;
import com.example.jdbc.jdbc.annotation.Table;


@Table("person")
public class Person {

    @ColSelect("id")
    @Id("id")
    private Long id;

    @ColSelect("name")
    @Col("name")
    private String name;

    @ColSelect("obs")
    @Col("obs")
    private String obs;

    @ColSelect("date_insert")
    @Col("date_insert")
    private LocalDateTime dateInsert;

    @Override
    public String toString() {
        return String.format("{ id: %s, name: %s , obs: %s, date_insert: %s } ", this.id, this.name, this.obs, this.dateInsert);
    }

    public Person(){}

    public Person(Long id) {
        this.id = id;
    }

    public Person(Long id, String name, String obs, LocalDateTime dateInsert) {
        this.id = id;
        this.name = name;
        this.obs = obs;
        this.dateInsert = dateInsert;
    }

    public Person( String name, String obs, LocalDateTime dateInsert) {
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
