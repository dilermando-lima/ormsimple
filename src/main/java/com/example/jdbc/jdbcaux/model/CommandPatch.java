package com.example.jdbc.jdbcaux.model;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public interface CommandPatch  {

        public JdbcModel prepareValues(Object entity, Map<String,Object> mapValues) throws Exception;

        public void chekingAnnotations(Object entity) throws Exception;

        public  String  buildCommand(JdbcModel jdbcModel, int dataBase) throws Exception;

        public <T> T doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn ) throws Exception;

}
