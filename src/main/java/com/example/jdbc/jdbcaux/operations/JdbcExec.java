package com.example.jdbc.jdbcaux.operations;

import com.example.jdbc.jdbcaux.model.JdbcModel;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcExec {



    private static <T> T exec(Command command, Object entity , JdbcTemplate jdbcTemplate, int database , Class<T> classReturn )  throws Exception {
       
        command.chekingAnnotations(entity);

        JdbcModel jdbcModel = command.prepareValues(entity);

        jdbcModel.setCommandBuilt( command.buildCommand(jdbcModel, database) );    

        return  command.doCommand(jdbcModel, jdbcTemplate , classReturn);
    }

    public static Long insert( Object entity , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  exec(new CommandInsertImp() , entity, jdbcTemplate, database, Long.class);
    }

    public static Long update( Object entity , JdbcTemplate jdbcTemplate, int database) throws Exception {
        return  exec(new CommandUpdateImp() , entity, jdbcTemplate, database, Long.class);
    }

    public static <T> T selectById( Object entity , JdbcTemplate jdbcTemplate, int database, Class<T> classReturn ) throws Exception {
        return  exec(new CommandSelectByIDImp() , entity, jdbcTemplate, database, classReturn);
    }



}
