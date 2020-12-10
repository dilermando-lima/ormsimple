package com.ormsimple.jdbc.exec;

import java.util.List;
import java.util.Map;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Model;
import com.ormsimple.jdbc.model.Select;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandSelectFirstOne extends Build {

    private Model model;


    private void buildModel(Class<?> classEntity, Select select) throws Exception {
        this.model = buildModelFromSelect(classEntity, select);
    }

    private void buildCommand(Select select) throws Exception { 
        model.setCommandBuilt( select.build() ); 
    }

    public <T> T  doCommand( Select select, JdbcTemplate jdbcTemplate, Class<T> classReturn, boolean takeNested ) throws Exception {
        
       
        buildModel(classReturn, select);
        buildCommand(select);

        List<Map<String, Object>> listMapReturn;
        
        if(   model.getListValue().isEmpty() ){
            listMapReturn = jdbcTemplate.queryForList( model.getCommandBuilt() );
        }else{
            listMapReturn =  jdbcTemplate.queryForList(
                model.getCommandBuilt() , model.getListValue().toArray()
              );
        }


        if( listMapReturn == null || listMapReturn.isEmpty() ) return null;
        
        return  getObjFromMapSelect(listMapReturn.get(0), classReturn , takeNested , !takeNested ? null : jdbcTemplate);
         
    }
    
}
