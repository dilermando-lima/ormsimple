package com.ormsimple.entity;

import com.ormsimple.jdbc.annotation.Col;


public class ContactAndPersonExampleView {
    
    @Col("id_person")
    private Long idPerson;

    @Col("id_contact")
    private Long idContact;

    @Col("name_person")
    private String name;

    @Col("phone")
    private String phone;


    @Override
    public String toString() {
        return String.format("{ id_person: %s, id_contact: %s , name: %s, phone: %s } ", this.idPerson, this.idContact, this.name, this.phone);
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    


}
