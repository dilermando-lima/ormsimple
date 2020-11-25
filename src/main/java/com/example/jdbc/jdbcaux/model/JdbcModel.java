package com.example.jdbc.jdbcaux.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class JdbcModel {
    
    private Map<String,Object> values;
    private Map<String,Integer> positions;
    private String commandBuilt;
    private String tableName;
    private String nameIdentity;
    private Object valueIdentity;

    public JdbcModel addParam(String nameColumn, Object obj){
        if( values == null ){ 
            values = new LinkedHashMap<String,Object>();
            positions = new LinkedHashMap<String,Integer>();
        } 
        values.put(nameColumn  ,  obj);
        positions.put(nameColumn, values.size());
        return this;
    }

    public JdbcModel addParamIdentity(String nameIdentity, Object valueIdentity){
        this.nameIdentity = nameIdentity;
        this.valueIdentity = valueIdentity;
        return this;
    }

    public Map<String, Object> getValues() {
        return values;
    }


    public String getCommandBuilt() {
        return commandBuilt;
    }

    public void setCommandBuilt(String commandBuilt) {
        this.commandBuilt = commandBuilt;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Integer> getPositions() {
        return positions;
    }

    public String getNameIdentity() {
        return nameIdentity;
    }

    public void setNameIdentity(String nameIdentity) {
        this.nameIdentity = nameIdentity;
    }

    public Object getValueIdentity() {
        return valueIdentity;
    }

    public void setValueIdentity(Object valueIdentity) {
        this.valueIdentity = valueIdentity;
    }


}
