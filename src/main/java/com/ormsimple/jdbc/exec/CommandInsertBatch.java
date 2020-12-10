package com.ormsimple.jdbc.exec;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Model;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class CommandInsertBatch extends Build {

    private List<Model> modelList;
    private String built;


    private void buildModelBatch(List<?> entities) throws Exception {
        modelList =  new ArrayList<Model>();
        for (Object entity : entities) {
            modelList.add( buildModelFromObj(entity));
        }
    }

    private void buildCommand() throws Exception { 
       
        built =  String.format( " insert into %s ( %s ) values ( %s ) " , 
                        modelList.get(0).getTable(), 
                        modelList.get(0).getMapBaseParam().entrySet().stream().map( e ->  " " + e.getKey() + " "  ).collect( Collectors.joining(",")) , 
                        modelList.get(0).getMapBaseParam().entrySet().stream().map( e ->  " ? " ).collect( Collectors.joining(",")) 
                    ); 
    }


    public int []  doCommand( Class<?> classEntity,List<?> entities, JdbcTemplate jdbcTemplate ) throws Exception {
        
        if( entities == null || entities.isEmpty() )  throw new Exception("List entities cannot be empty on insertBatch");

        validateAnnotationsEntity(classEntity);
        buildModelBatch(entities);
        buildCommand();

       return jdbcTemplate.batchUpdate(built , new BatchPreparedStatementSetter(){
            
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                List<String> indexes = new ArrayList<String>(modelList.get(i).getMapBaseParam().keySet()); 

                for (String key :  modelList.get(i).getMapBaseParam().keySet()) {
                    ps.setObject(indexes.indexOf(key) + 1, modelList.get(i).getMapBaseParam().get(key) );
                }
            }

            public int getBatchSize() {
                return modelList.size();
            }

        });

    }
    
}
