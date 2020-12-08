package com.example.jdbc.jdbc.exec;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.BaseCommandUpdate;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandUpdateCustom extends Build {

    private Model model;


    private void buildModel( BaseCommandUpdate update) throws Exception {
        this.model = buildModelFromUpdate(null, update);
    }

    private void buildCommand(BaseCommandUpdate update) throws Exception { 
        model.setCommandBuilt( update.build() ); 
    }

    public int doCommand( BaseCommandUpdate update, JdbcTemplate jdbcTemplate ) throws Exception {
        
        buildModel( update);
        buildCommand(update);

        return jdbcTemplate.update(model.getCommandBuilt(), model.getListValue().toArray());
 
    }
    
}
