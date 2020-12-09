package com.ormsimple.jdbc.model;

import java.util.LinkedList;
import java.util.List;

import com.ormsimple.jdbc.base.JdbcUtils;

public class Select {
    
    private String built;
    private List<Object> listValues;


    public Select(String select) {
        listValues = new LinkedList<Object>();
        built = select;
    }

    public Select addValue(Object value){
        listValues = new LinkedList<Object>();
        listValues.add(value);
        return this;
    }

    public Select(String select, Object... values) {
        listValues = new LinkedList<Object>();
        built = select;

        if(  values != null  ){
            for (Object object : values) {
                listValues.add(object);
            }
        }
    }

  
 
    public List<Object> getListValues() {
        return listValues;
    }

    public String build() throws Exception {
         JdbcUtils.validateSelectSql(built);
         return built;
    }

}
