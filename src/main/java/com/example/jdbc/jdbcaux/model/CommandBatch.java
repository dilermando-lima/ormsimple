package com.example.jdbc.jdbcaux.model;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public interface CommandBatch {

        public JdbcModelBatch prepareValues(  List<?>   entityList) throws Exception;

        public void chekingAnnotations( List<?>  entityList) throws Exception;

        public  String  buildCommand(JdbcModelBatch jdbcModelBatch, int dataBase) throws Exception;

        public List<Integer> doCommand(JdbcModelBatch jdbcModelBatch, JdbcTemplate jdbcTemplate ) throws Exception;

}
