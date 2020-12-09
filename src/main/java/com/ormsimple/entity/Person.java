package com.ormsimple.entity;

import java.time.LocalDateTime;

import com.ormsimple.jdbc.annotation.Col;
import com.ormsimple.jdbc.annotation.ColSelect;
import com.ormsimple.jdbc.annotation.Id;
import com.ormsimple.jdbc.annotation.Table;


@Table("person")
public class Person {

    @ColSelect("id")
    @Id("id")
    private Long id;

    @ColSelect("name")
    @Col("name")
    private String name;

    @ColSelect("date_insert")
    @Col("date_insert")
    private LocalDateTime dateInsert;

    @Override
    public String toString() {
        return String.format("{ id: %s, name: %s ,  date_insert: %s } ", this.id, this.name, this.dateInsert);
    }

    public Person(){}

    public Person(Long id) {
        this.id = id;
    }

    public Person(Long id, String name, LocalDateTime dateInsert) {
        this.id = id;
        this.name = name;
        this.dateInsert = dateInsert;
    }

    public Person( String name, LocalDateTime dateInsert) {
        this.name = name;
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

    public LocalDateTime getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(LocalDateTime dateInsert) {
        this.dateInsert = dateInsert;
    }



    
    


}
