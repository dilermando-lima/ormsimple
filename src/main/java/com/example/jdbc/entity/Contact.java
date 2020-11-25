package com.example.jdbc.entity;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JdbcTable("contact")
public class Contact {


    
    public Contact(Long id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }


   @JdbcIdentity("id")
    private Long id;

    @JdbcColumn("email")
    private String email;

    @JdbcColumn("phone")
    private String phone;


}
