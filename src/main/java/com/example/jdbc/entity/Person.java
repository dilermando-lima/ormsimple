package com.example.jdbc.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
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

    public Person(Long id, String name, String obs, LocalDate insert, Long number, Integer integer, int intnum,
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


    @JdbcIdentity("id")
    private Long id;

    @JdbcColumn("name")
    private String name;

    @JdbcColumn("obs")
    private String obs;

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


}
