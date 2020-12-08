package com.example.jdbc.jdbc.model;

import java.util.LinkedList;
import java.util.List;

import com.example.jdbc.jdbc.base.JdbcUtils;

public class Update implements BaseCommandUpdate {
    
    private String built;
    private List<Object> listValues;

    public Update(String update) {
        listValues = new LinkedList<Object>();
        built = update;
    }


    public Update addValue(Object value){
        listValues = new LinkedList<Object>();
        listValues.add(value);
        return this;
    }

    public Update(String update, Object... values) {
        listValues = new LinkedList<Object>();
        built = update;

        if(  values != null  ){
            for (Object object : values) {
                listValues.add(object);
            }
        }
    }

  
    @Override
    public List<Object> getListValues() {
        return listValues;
    }

    @Override
    public String build() throws Exception {
        JdbcUtils.validateUpdateSql(built);
        return built.replace("  ", " ");
    }

}
