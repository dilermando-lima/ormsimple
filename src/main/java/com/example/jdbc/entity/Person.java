package com.example.jdbc.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcColumnSelect;
import com.example.jdbc.jdbcaux.annotations.JdbcFkIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JdbcTable("person")
public class Person {


    @Override
    public String toString() {
        return String.format("\n[ id: %s ,  name: %s  " , this.id , this.name );
    }

    public Person(Long id, String name, String obs, LocalDate insert, Long number, Integer integer, Integer intnum,
            LocalDateTime datetime) {
        this.id = id;
        this.name = name;
        this.obs = obs;
        this.insert = insert;
        this.number = number;
        this.integer = integer;
        this.intnum = intnum;
        this.datetime = datetime;
    }

    @JdbcColumnSelect("id")
    @JdbcIdentity("id")
    private Long id;

    @JdbcColumnSelect("name")
    @JdbcColumn("name")
    private String name;

    @JdbcColumnSelect("obs")
    @JdbcColumn("obs")
    private String obs;

    @JdbcColumnSelect("insert")
    @JdbcColumn("insert")
    private LocalDate insert;

    @JdbcColumn("number")
    private Long number;

    @JdbcColumn("integer")
    private Integer integer;

    @JdbcColumn("intnum")
    private Integer intnum;

    @JdbcColumn("datetime")
    private LocalDateTime datetime;

    @JdbcFkIdentity("id_contact")
    private Contact contact;

    @JdbcFkIdentity("id_contact_2")
    private Contact  contact2;





}
