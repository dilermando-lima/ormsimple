package com.example.jdbc.entity;

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
@JdbcTable("contact")
public class Contact {

   @JdbcIdentity("id")
    private Long id;

    @JdbcColumn("email")
    private String email;

    @JdbcColumn("phone")
    private String phone;

    @JdbcFkIdentity("id_person")
    private Person person;

    @Override
    public String toString() {
        return String.format("{ id: %s, email: %s , phone: %s, id_person: %s } ", this.id, this.email, this.phone, this.person);
    }


    public Contact(Long id) {
        this.id = id;
    }

    public Contact(Long id, String email, String phone, Person person) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.person = person;
    }

    
    



}
