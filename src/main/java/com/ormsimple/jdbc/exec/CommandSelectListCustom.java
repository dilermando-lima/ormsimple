package com.ormsimple.jdbc.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Model;
import com.ormsimple.jdbc.model.SelectCustom;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandSelectListCustom extends Build {

    private Model model;


    private void buildModel(Class<?> classEntity, SelectCustom select) throws Exception {
        this.model = buildModelFromSelectCustom(classEntity, select);
    }

    private void buildCommand(SelectCustom select) throws Exception { 
        model.setCommandBuilt( select.build() ); 
    }

    public <T> List<T>  doCommand( SelectCustom select, JdbcTemplate jdbcTemplate, Class<T> classReturn, boolean takeNested ) throws Exception {
        
        validateAnnotationsEntity(classReturn);
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


        if( listMapReturn == null || listMapReturn.isEmpty() ) return new ArrayList<T>();
        
        List<T> listReturn = new ArrayList<T>();
        for (Map<String,Object> map : listMapReturn) {
            listReturn.add( getObjFromMapSelect(map, classReturn , takeNested , !takeNested ? null : jdbcTemplate));
        }

        return listReturn;
         
    }
    
}
