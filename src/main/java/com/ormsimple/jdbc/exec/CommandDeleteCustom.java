package com.ormsimple.jdbc.exec;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Delete;
import com.ormsimple.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandDeleteCustom extends Build {

    private Model model;

    private void buildModel( Delete delete) throws Exception {
        this.model = buildModelFromDelete(null, delete);
    }

    private void buildCommand(Delete delete) throws Exception { 
        model.setCommandBuilt( delete.build());
    }


    public int doCommand( Delete delete, JdbcTemplate jdbcTemplate) throws Exception { 
        buildModel( delete);
        buildCommand(delete);
        return jdbcTemplate.update(model.getCommandBuilt(), model.getListValue().toArray());
    }
    
}
