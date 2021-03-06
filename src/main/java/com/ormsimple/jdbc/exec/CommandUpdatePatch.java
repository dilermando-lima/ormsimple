package com.ormsimple.jdbc.exec;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Model;
import com.ormsimple.jdbc.model.UpdatePatch;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandUpdatePatch extends Build {

    private Model model;


    private void buildModel(Class<?> classToReturn, Map<String,Object> mapValues,  Object id) throws Exception {
        this.model = buildModelFromMap(classToReturn, mapValues, id);
    }

    private void buildCommand() throws Exception { 
        model.setCommandBuilt( 
            String.format( "  update %s set %s where %s " , 
                        model.getTable(), 
                        model.getMapBaseParam().entrySet().stream().map( e ->  " " + e.getKey() + " = ? " ).collect( Collectors.joining(",")) , 
                        model.getNameId() + " = ? " )
                    ); 
    }

    public <T> T doCommand( Object id, Class<?> classEntity , UpdatePatch updatePath, JdbcTemplate jdbcTemplate, Class<T> classReturn ) throws Exception {
     
        
        validateAnnotationsEntity(classEntity);
        buildModel(classEntity, updatePath.getMapAlter() , id );
        buildCommand();

        List<String> indexes = new ArrayList<String>(model.getMapBaseParam().keySet()); 

        int updated =  jdbcTemplate.update(con -> {
               PreparedStatement statement = con.prepareStatement(model.getCommandBuilt() );
               for (String key : model.getMapBaseParam().keySet()) {
                   statement.setObject( indexes.indexOf(key) + 1, model.getMapBaseParam().get(key)  );
               }

               statement.setObject(model.getMapBaseParam().size() + 1, id);

               return statement;
       });
       
       if( classReturn == null ){
             return null;
       }else{
            return updated == 0 ? classReturn.cast(null) : classReturn.cast( model.getValueId());
       } 
      
         
    }
    
}
