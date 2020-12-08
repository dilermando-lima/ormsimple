package com.example.jdbc.entity;

import com.example.jdbc.jdbc.annotation.Col;
import com.example.jdbc.jdbc.annotation.ColSelect;
import com.example.jdbc.jdbc.annotation.Fk;
import com.example.jdbc.jdbc.annotation.Id;
import com.example.jdbc.jdbc.annotation.Table;

@Table("contact")
public class Contact {

    @ColSelect("id")
    @Id("id")
    private Long id;

    @ColSelect("phone")
    @Col("phone")
    private String phone;

    @ColSelect("id_person")
    @Fk("id_person")
    private Person person;

    @Override
    public String toString() {
        return String.format("{ id: %s, phone: %s, person: %s } ", this.id, this.phone, this.person);
    }

    public Contact(){}

    public Contact(Long id) {
        this.id = id;
    }

    public Contact(Long id, String phone, Person person) {
        this.id = id;
        this.phone = phone;
        this.person = person;
    }

    public Contact( String phone, Person person) {
        this.phone = phone;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    

    
    



}
