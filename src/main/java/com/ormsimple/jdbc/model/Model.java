package com.ormsimple.jdbc.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {
    
    private String nameId;
    private Object valueId;
    private Map<String,Object> mapBaseParam;
    private List<Object> listValue;

    private String commandBuilt;
    private String table;

    public Model(){
        mapBaseParam = new LinkedHashMap<String,Object>();
        listValue =  new LinkedList<Object>();
    }

    public Model addBaseParam(String name, Object val){
        mapBaseParam.put(name, val);
        return this;
    }

    public Model setId(String name, Object val){
        this.nameId = name;
        this.valueId = val;
        return this;
    }

    public Model addValue(Object val){
        listValue.add(val);
        return this;
    }

    public String getTable() {
        return table;
    }

    public Model setTable(String table) {
        this.table = table;
        return this;
    }

    public String getCommandBuilt() {
        return commandBuilt;
    }

    public void setCommandBuilt(String commandBuilt) {
        this.commandBuilt = commandBuilt;
    }

    public String getNameId() {
        return nameId;
    }


    public Object getValueId() {
        return valueId;
    }

    public Map<String, Object> getMapBaseParam() {
        return mapBaseParam;
    }

    public List<Object> getListValue() {
        return listValue;
    }

    
    
    
}
