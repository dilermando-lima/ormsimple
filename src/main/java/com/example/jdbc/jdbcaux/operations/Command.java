package com.example.jdbc.jdbcaux.operations;

import com.example.jdbc.jdbcaux.model.JdbcModel;

import org.springframework.jdbc.core.JdbcTemplate;

public interface Command {

        public void chekingAnnotations(Object entity) throws Exception;

        public JdbcModel prepareValues(Object entity)throws Exception;

        public  String  buildCommand(JdbcModel jdbcModel, int dataBase) throws Exception;

        public <T> T doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn ) throws Exception;

}
