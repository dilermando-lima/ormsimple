package com.example.jdbc.jdbcaux.operations;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;

import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;
import com.example.jdbc.jdbcaux.model.Command;
import com.example.jdbc.jdbcaux.model.CommandAux;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.JdbcModel;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandDeleteImp  extends CommandAux implements Command {

    @Override
    public void chekingAnnotations(Object entity) throws Exception {
      genericCheckingAnnotations(entity);
    }

    @Override
    public JdbcModel prepareValues(Object entity) throws Exception {
        JdbcModel jdbcModel = new JdbcModel();

        jdbcModel.setTableName(entity.getClass().getAnnotation(JdbcTable.class).value());
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field f : fields) {
            if(  f.isAnnotationPresent(JdbcIdentity.class) ){
                f.setAccessible(true);
                jdbcModel.addParamIdentity(f.getAnnotation(JdbcIdentity.class).value() , f.get(entity));
            }
        }

        return jdbcModel;
    }

    @Override
    public String buildCommand(JdbcModel jdbcModel, int dataBase) throws Exception {
        
        if( dataBase == DataBase.MY_SQL ){
           
            return String.format( " delete from  %s where %s = ? " , 
            jdbcModel.getTableName(), 
            "`" + jdbcModel.getNameIdentity() + "`" );

        }else if( dataBase == DataBase.SQL_SERVER ){
           
            return String.format( " delete from  %s where %s = ? " , 
            jdbcModel.getTableName(), 
            "[" + jdbcModel.getNameIdentity() + "]" );

        }else{
            throw new Exception(DataBase.class.getName() + " int value was not found. Try chose a valid constant database");
        }
    }

    @Override
    public <T> T doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn) throws Exception {
 
       int deleted =  jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(jdbcModel.getCommandBuilt());
            statement.setObject(1, jdbcModel.getValueIdentity() );
            return statement;
        });

        return deleted == 0 ? typeReturn.cast(null) : typeReturn.cast(jdbcModel.getValueIdentity());
    }
    



}
