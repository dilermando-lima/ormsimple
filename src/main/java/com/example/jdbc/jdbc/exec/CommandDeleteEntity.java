package com.example.jdbc.jdbc.exec;

import java.sql.PreparedStatement;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandDeleteEntity extends Build {

    private Model model;


    private void buildModel(Object entity) throws Exception {
        this.model = buildModelFromObj(entity);
    }

    private void buildCommand() throws Exception { 
        model.setCommandBuilt( 
            String.format( " delete from  %s where %s " , 
                        model.getTable(), 
                        model.getNameId() + " = ? " )
                    ); 
    }

    public <T> T doCommand( Class<?> classEntity,Object entity, JdbcTemplate jdbcTemplate, Class<T> classReturn ) throws Exception {
        
        validateAnnotationsEntity(classEntity);
        buildModel(entity);
        buildCommand();

        int deleted =  jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(model.getCommandBuilt());
            statement.setObject(1, model.getValueId());
            return statement;
        });
        return deleted == 0 ? classReturn.cast(null) : classReturn.cast(model.getValueId());
    }
    
}
