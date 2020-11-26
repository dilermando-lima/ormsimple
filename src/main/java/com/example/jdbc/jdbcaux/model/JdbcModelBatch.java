package com.example.jdbc.jdbcaux.model;

import java.util.ArrayList;
import java.util.List;

public class JdbcModelBatch {

    private List<JdbcModel> listJdbcModel;
    private String commandBuilt;


    public JdbcModelBatch addJdbcModel(JdbcModel jdbcModel){
        if( listJdbcModel == null ) listJdbcModel = new ArrayList<JdbcModel>();
        listJdbcModel.add(jdbcModel);
        return this;
    }


    public List<JdbcModel> getListJdbcModel() {
        return listJdbcModel;
    }

    public void setListJdbcModel(List<JdbcModel> listJdbcModel) {
        this.listJdbcModel = listJdbcModel;
    }

    public String getCommandBuilt() {
        return commandBuilt;
    }

    public void setCommandBuilt(String commandBuilt) {
        this.commandBuilt = commandBuilt;
    }
  

    


}
