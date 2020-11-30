package com.example.jdbc.jdbcaux.operations;

import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbcaux.model.Command;
import com.example.jdbc.jdbcaux.model.CommandBatch;
import com.example.jdbc.jdbcaux.model.CommandPatch;
import com.example.jdbc.jdbcaux.model.CommandSelect;
import com.example.jdbc.jdbcaux.model.JdbcModel;
import com.example.jdbc.jdbcaux.model.JdbcModelBatch;
import com.example.jdbc.jdbcaux.model.Select;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcExec {

    
    private static <T> T exec(Command command, Object entity , JdbcTemplate jdbcTemplate, int database , Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotations(entity);

        JdbcModel jdbcModel = command.prepareValues(entity);

        jdbcModel.setCommandBuilt( command.buildCommand(jdbcModel, database) );    

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }

    private static <T> T execPatch(CommandPatch command, Object entity , Map<String,Object> mapValues, JdbcTemplate jdbcTemplate, int database , Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotations(entity);

        JdbcModel jdbcModel = command.prepareValues(entity, mapValues);

        jdbcModel.setCommandBuilt( command.buildCommand(jdbcModel, database) );    

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }

    private static <T> List<T> execSelect(CommandSelect command, Select select , JdbcTemplate jdbcTemplate, int database , Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotationsSelect(classReturn);

        JdbcModel jdbcModel = command.prepareValues(select);

        jdbcModel.setCommandBuilt( command.buildCommand(select, database) );    

        System.out.println( jdbcModel.getCommandBuilt() );

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }



    private static List<Integer> execBatch(CommandBatch command, List<?>  entityList , JdbcTemplate jdbcTemplate, int database )  throws Exception {
       
        command.chekingAnnotations(entityList);

        JdbcModelBatch jdbcModelBatch = command.prepareValues(entityList);

        jdbcModelBatch.setCommandBuilt( command.buildCommand(jdbcModelBatch, database) );    

        return  command.doCommand(jdbcModelBatch, jdbcTemplate);
    }


    public static <T> T  insert( Object entity , JdbcTemplate jdbcTemplate, int database,  Class<T> classReturn) throws Exception {
        return  exec(new CommandInsertImp() , entity, jdbcTemplate, database, classReturn);
    }

    public static <T> T update( Object entity , JdbcTemplate jdbcTemplate, int database, Class<T> classReturn) throws Exception {
        return  exec(new CommandUpdateImp() , entity, jdbcTemplate, database, classReturn);
    }

    public static <T> T updatePatch( Object entityWithId , Map<String,Object> mapValues,  JdbcTemplate jdbcTemplate,  int database, Class<T> classReturn ) throws Exception {
        return  execPatch(new CommandUpdatePatchImp() , entityWithId ,mapValues ,  jdbcTemplate, database, classReturn);
    }

    public static <T> T selectById( Object entityWithId , JdbcTemplate jdbcTemplate, int database, Class<T> classReturn ) throws Exception {
        return  exec(new CommandSelectByIDImp() , entityWithId, jdbcTemplate, database, classReturn);
    }

    public static <T> List<T> select( Select select , JdbcTemplate jdbcTemplate, int database, Class<T> classReturn ) throws Exception {
        return  execSelect(new CommandSelectImp(), select, jdbcTemplate, database, classReturn);
    }

    public static <T> T delete( Object entityWithId , JdbcTemplate jdbcTemplate, int database, Class<T> classReturn ) throws Exception {
        return  exec(new CommandDeleteImp() , entityWithId, jdbcTemplate, database, classReturn);
    }

    public static List<Integer> insertBatch(List<?> entityList , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  execBatch(new CommandInsertBatchImp(), entityList,  jdbcTemplate, database);
    }

    public static List<Integer> updateBatch(List<?> entityList , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  execBatch(new CommandUpdateBatchImp(), entityList,  jdbcTemplate, database);
    }



}
