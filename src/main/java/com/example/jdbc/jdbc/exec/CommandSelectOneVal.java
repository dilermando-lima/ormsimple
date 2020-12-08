package com.example.jdbc.jdbc.exec;

import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.BaseCommandSelect;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandSelectOneVal extends Build {

    private Model model;


    private void buildModel( BaseCommandSelect select) throws Exception {
        this.model = buildModelFromSelect(null, select);
    }

    private void buildCommand(BaseCommandSelect select) throws Exception { 
        model.setCommandBuilt( select.build()); 
    }

    public <T> T  doCommand( BaseCommandSelect select, JdbcTemplate jdbcTemplate, Class<T> classReturn ) throws Exception {
        
        // validateAnnotationsEntity(classReturn); it is not gonna checked this.
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
