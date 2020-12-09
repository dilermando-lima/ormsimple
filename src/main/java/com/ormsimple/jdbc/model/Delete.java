package com.ormsimple.jdbc.model;

import java.util.LinkedList;
import java.util.List;

import com.ormsimple.jdbc.base.JdbcUtils;

public class Delete {
    
    private String built;
    private List<Object> listValues;

    public Delete(String delete) {
        listValues = new LinkedList<Object>();
        built = delete;
    }


    public Delete addValue(Object value){
        listValues = new LinkedList<Object>();
        listValues.add(value);
        return this;
    }

    public Delete(String delete, Object... values) {
        listValues = new LinkedList<Object>();
        built = delete;

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
        JdbcUtils.validateDeleteSql(built);
        return built;
    }

}
