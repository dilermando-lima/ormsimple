package com.example.jdbc.jdbc.model;

import java.util.List;

public interface BaseCommandSelect {
    
    public List<Object> getListValues();

    public String build() throws Exception;

}
