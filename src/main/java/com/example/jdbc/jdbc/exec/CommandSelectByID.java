package com.example.jdbc.jdbc.exec;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandSelectByID extends Build {

    private Model model;


    private void buildModel(Object entity) throws Exception {
        this.model = buildSelectModelFromObj(entity);
    }

    private void buildCommand() throws Exception { 
        model.setCommandBuilt( 
            String.format( " select %s from %s where %s " , 
                        model.getMapBaseParam().entrySet().stream().map( e -> " " + e.getKey() + " " ).collect( Collectors.joining(",")) , 
                        model.getTable(), 
                        model.getNameId() + " = ? " )
                    ); 
    }

    public <T> T doCommand( Object entity, JdbcTemplate jdbcTemplate, Class<T> classReturn, boolean takeNested ) throws Exception {
        
        validateAnnotationsEntity(classReturn);
        buildModel(entity);
        buildCommand();

        List<Map<String, Object>> listMapReturn = jdbcTemplate.queryForList(model.getCommandBuilt(),  new Object[] {model.getValueId()}  );

        if( listMapReturn == null || listMapReturn.isEmpty() ) return classReturn.cast( null );
       
        return getObjFromMap(listMapReturn.get(0), classReturn, takeNested , !takeNested ? null : jdbcTemplate );
  
    }
    
}
