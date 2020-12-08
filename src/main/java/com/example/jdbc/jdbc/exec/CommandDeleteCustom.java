package com.example.jdbc.jdbc.exec;

import com.example.jdbc.jdbc.base.Build;
import com.example.jdbc.jdbc.model.BaseCommandDelete;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandDeleteCustom extends Build {

    private Model model;


    private void buildModel( BaseCommandDelete delete) throws Exception {
        this.model = buildModelFromDelete(null, delete);
    }

    private void buildCommand(BaseCommandDelete delete) throws Exception { 
        model.setCommandBuilt( delete.build() ); 
    }

    public int doCommand( BaseCommandDelete delete, JdbcTemplate jdbcTemplate) throws Exception {
        
        buildModel( delete);
        buildCommand(delete);

        return jdbcTemplate.update(model.getCommandBuilt(), model.getListValue().toArray());
       
         
    }
    
}
