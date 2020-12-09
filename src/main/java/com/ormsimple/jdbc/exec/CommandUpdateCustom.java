package com.ormsimple.jdbc.exec;

import com.ormsimple.jdbc.base.Build;
import com.ormsimple.jdbc.model.Model;
import com.ormsimple.jdbc.model.Update;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandUpdateCustom extends Build {

    private Model model;

    private void buildModel( Update update) throws Exception {
        this.model = buildModelFromUpdate(null, update);
    }

    private void buildCommand(Update update) throws Exception { 
        model.setCommandBuilt( update.build());
    }


    public int doCommand( Update update, JdbcTemplate jdbcTemplate ) throws Exception {
        buildModel(update);
        buildCommand(update);
        return jdbcTemplate.update(model.getCommandBuilt(), model.getListValue().toArray());
    }
    
}
