package com.example.jdbc.jdbcaux.model;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public interface CommandSelect {

        public void chekingAnnotationsSelect(Class<?> classReturn ) throws Exception;

        public JdbcModel prepareValues(Select select) throws Exception;

        public  String  buildCommand(Select select, int dataBase) throws Exception;

        public <T> List<T>  doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn ) throws Exception;

}
