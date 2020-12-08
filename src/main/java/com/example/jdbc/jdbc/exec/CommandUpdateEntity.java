package com.example.jdbc.jdbc.exec;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandUpdateEntity extends Build {

    private Model model;


    private void buildModel(Object entity) throws Exception {
        this.model = buildModelFromObj(entity);
    }

    private void buildCommand() throws Exception { 
        model.setCommandBuilt( 
            String.format( "  update %s set %s where %s " , 
                        model.getTable(), 
                        model.getMapBaseParam().entrySet().stream().map( e ->  " " + e.getKey() + " = ? " ).collect( Collectors.joining(",")) , 
                        model.getNameId() + " = ? " )
                    ); 
    }

    public <T> T doCommand( Class<?> classEntity,Object entity, JdbcTemplate jdbcTemplate, Class<T> classReturn ) throws Exception {
        
        validateAnnotationsEntity(classEntity);
        buildModel(entity);
        buildCommand();

        List<String> indexes = new ArrayList<String>(model.getMapBaseParam().keySet()); 

        int updated =  jdbcTemplate.update(con -> {
               PreparedStatement statement = con.prepareStatement(model.getCommandBuilt() );
               for (String key : model.getMapBaseParam().keySet()) {
                   statement.setObject( indexes.indexOf(key) + 1, model.getMapBaseParam().get(key)  );
               }

               statement.setObject(model.getMapBaseParam().size() + 1, model.getValueId());

               return statement;
       });
       
       if( classReturn == null ){
             return null;
       }else{
            return updated == 0 ? classReturn.cast(null) : classReturn.cast( model.getValueId());
       } 
      
         
    }
    
}
