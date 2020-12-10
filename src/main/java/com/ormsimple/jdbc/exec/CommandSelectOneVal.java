package com.ormsimple.jdbc.exec;

import java.util.List;
import java.util.Map;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Model;
import com.ormsimple.jdbc.model.Select;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandSelectOneVal extends Build {

    private Model model;


    private void buildModel( Select select) throws Exception {
        this.model = buildModelFromSelect(null, select);
    }

    private void buildCommand(Select select) throws Exception { 
        model.setCommandBuilt( select.build()); 
    }

    public <T> T  doCommand( Select select, JdbcTemplate jdbcTemplate, Class<T> classReturn ) throws Exception {

        buildModel( select);
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
        
        return classReturn.cast(  parseValueIntoAllowedTypes( listMapReturn.get(0).entrySet().iterator().next().getValue() , classReturn ));   
    }
    
}
