package com.example.jdbc.jdbcaux.model;

import org.springframework.jdbc.core.JdbcTemplate;

public interface Command {

        public void chekingAnnotations(Object entity) throws Exception;

        public JdbcModel prepareValues(Object entity) throws Exception;

        public  String  buildCommand(JdbcModel jdbcModel,  Class<? extends DataBase> dataBase ) throws Exception;

        public <T> T doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn ) throws Exception;

}
