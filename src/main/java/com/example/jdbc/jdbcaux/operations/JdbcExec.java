package com.example.jdbc.jdbcaux.operations;

import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbcaux.model.Command;
import com.example.jdbc.jdbcaux.model.CommandBatch;
import com.example.jdbc.jdbcaux.model.CommandPatch;
import com.example.jdbc.jdbcaux.model.CommandSelect;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.JdbcModel;
import com.example.jdbc.jdbcaux.model.JdbcModelBatch;
import com.example.jdbc.jdbcaux.model.Select;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class JdbcExec {

    
    protected <T> T exec(Command command, Object entity , JdbcTemplate jdbcTemplate,  Class<? extends DataBase> dataBase  , Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotations(entity);

        JdbcModel jdbcModel = command.prepareValues(entity);

        jdbcModel.setCommandBuilt( command.buildCommand(jdbcModel, dataBase) );    

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }

    protected <T> T execPatch(CommandPatch command, Object entity , Map<String,Object> mapValues, JdbcTemplate jdbcTemplate,   Class<? extends DataBase> dataBase , Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotations(entity);

        JdbcModel jdbcModel = command.prepareValues(entity, mapValues);

        jdbcModel.setCommandBuilt( command.buildCommand(jdbcModel, dataBase) );    

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }

    protected <T> List<T> execSelect(CommandSelect command, Select select , JdbcTemplate jdbcTemplate, Class<? extends DataBase> dataBase, Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotationsSelect(classReturn);

        JdbcModel jdbcModel = command.prepareValues(select);

        jdbcModel.setCommandBuilt( command.buildCommand(select, dataBase) );    

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }

    protected List<Integer> execBatch(CommandBatch command, List<?>  entityList , JdbcTemplate jdbcTemplate,  Class<? extends DataBase> dataBase )  throws Exception {
       
        command.chekingAnnotations(entityList);

        JdbcModelBatch jdbcModelBatch = command.prepareValues(entityList);

        jdbcModelBatch.setCommandBuilt( command.buildCommand(jdbcModelBatch, dataBase) );    

        return  command.doCommand(jdbcModelBatch, jdbcTemplate);
    }

}
