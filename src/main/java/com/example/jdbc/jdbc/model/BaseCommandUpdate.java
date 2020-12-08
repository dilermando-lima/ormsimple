package com.example.jdbc.jdbc.model;

import java.util.List;

public interface BaseCommandUpdate {
    
    public List<Object> getListValues();

    public String build() throws Exception;

}
