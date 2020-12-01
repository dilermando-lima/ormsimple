package com.example.jdbc.jdbcaux.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbcaux.model.CommandSelect;
import com.example.jdbc.jdbcaux.model.CommandUtils;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.JdbcModel;
import com.example.jdbc.jdbcaux.model.Select;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandSelectImp extends CommandUtils implements CommandSelect {

    @Override
    public void chekingAnnotationsSelect(Class<?> classReturn) throws Exception {   
        genericCheckingAnnotationsSelect(classReturn.getDeclaredConstructor().newInstance());
    }

    @Override
    public JdbcModel prepareValues(Select select) throws Exception {
        JdbcModel jdbcModel = new JdbcModel();
        int i = 0;
        for (Object obj : select.getListValues()) {
            jdbcModel.addParam("param" + i, obj);
            i++;
        }
        return jdbcModel;
    }

    @Override
    public String buildCommand(Select select,  Class<? extends DataBase> dataBase) throws Exception {

        return select.build(dataBase);
    }

    @Override
    public <T> List<T> doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn) throws Exception {
 

        List<Map<String, Object>> listMapReturn;
        
        if(   jdbcModel.getValues().isEmpty() ){
            listMapReturn = jdbcTemplate.queryForList( jdbcModel.getCommandBuilt() );
        }else{
            listMapReturn =  jdbcTemplate.queryForList(
                jdbcModel.getCommandBuilt() , jdbcModel.getValues().values().toArray()
              );
        }
        
        
        if( listMapReturn == null || listMapReturn.isEmpty() ) return new ArrayList<T>();

        List<T> listReturn = new ArrayList<T>();
        for (Map<String,Object> map : listMapReturn) {
            listReturn.add( getSelectObjFromMap(map, typeReturn ));
        }

        return listReturn;
    }
    
}
