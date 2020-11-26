package com.example.jdbc.jdbcaux.operations;

import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbcaux.model.Command;
import com.example.jdbc.jdbcaux.model.CommandBatch;
import com.example.jdbc.jdbcaux.model.CommandPatch;
import com.example.jdbc.jdbcaux.model.JdbcModel;
import com.example.jdbc.jdbcaux.model.JdbcModelBatch;

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



    private static List<Integer> execBatch(CommandBatch command, List<?>  entityList , JdbcTemplate jdbcTemplate, int database )  throws Exception {
       
        command.chekingAnnotations(entityList);

        JdbcModelBatch jdbcModelBatch = command.prepareValues(entityList);

        jdbcModelBatch.setCommandBuilt( command.buildCommand(jdbcModelBatch, database) );    

        return  command.doCommand(jdbcModelBatch, jdbcTemplate);
    }


    public static Long insert( Object entity , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  exec(new CommandInsertImp() , entity, jdbcTemplate, database, Long.class);
    }

    public static Long update( Object entity , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  exec(new CommandUpdateImp() , entity, jdbcTemplate, database, Long.class);
    }

    public static Long updatePatch( Object entityWithId , Map<String,Object> mapValues,  JdbcTemplate jdbcTemplate,  int database) throws Exception {
        return  execPatch(new CommandUpdatePatchImp() , entityWithId ,mapValues ,  jdbcTemplate, database, Long.class);
    }

    public static <T> T selectById( Object entityWithId , JdbcTemplate jdbcTemplate, int database, Class<T> classReturn ) throws Exception {
        return  exec(new CommandSelectByIDImp() , entityWithId, jdbcTemplate, database, classReturn);
    }

    public static Long delete( Object entityWithId , JdbcTemplate jdbcTemplate, int database ) throws Exception {
        return  exec(new CommandDeleteImp() , entityWithId, jdbcTemplate, database, Long.class);
    }

    public static List<Integer> insertBatch(List<?> entityList , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  execBatch(new CommandInsertBatchImp(), entityList,  jdbcTemplate, database);
    }



}
