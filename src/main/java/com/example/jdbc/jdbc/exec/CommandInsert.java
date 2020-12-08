package com.example.jdbc.jdbc.exec;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class CommandInsert extends Build {

    private Model model;


    private void buildModel(Object entity) throws Exception {
        this.model = buildModelFromObj(entity);
    }

    private void buildCommand() throws Exception { 
        model.setCommandBuilt( 
            String.format( " insert into %s ( %s ) values ( %s ) " , 
                        model.getTable(), 
                        model.getMapBaseParam().entrySet().stream().map( e ->  " " + e.getKey() + " "  ).collect( Collectors.joining(",")) , 
                        model.getMapBaseParam().entrySet().stream().map( e ->  " ? " ).collect( Collectors.joining(",")) )
                    ); 
    }

    public <T> T doCommand( Class<?> classEntity,Object entity, JdbcTemplate jdbcTemplate, Class<T> classReturn ) throws Exception {
        
        validateAnnotationsEntity(classEntity);
        buildModel(entity);
        buildCommand();

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        List<String> indexes = new ArrayList<String>(model.getMapBaseParam().keySet()); 

        jdbcTemplate.update(con -> {
               PreparedStatement statement = con.prepareStatement(model.getCommandBuilt(), Statement.RETURN_GENERATED_KEYS );
               for (String key : model.getMapBaseParam().keySet()) {
                   statement.setObject( indexes.indexOf(key) + 1, model.getMapBaseParam().get(key)  );
               }
               return statement;
       }, holder);
   
       if( classReturn == null ){
             return null;
       }else{
            return  parseValueIntoAllowedTypes(holder.getKey().toString(), classReturn);
        }
         
        
    }
    
}
