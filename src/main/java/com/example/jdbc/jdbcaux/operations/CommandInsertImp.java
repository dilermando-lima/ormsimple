package com.example.jdbc.jdbcaux.operations;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.stream.Collectors;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcFkIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;
import com.example.jdbc.jdbcaux.model.Command;
import com.example.jdbc.jdbcaux.model.CommandAux;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.JdbcModel;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class CommandInsertImp extends CommandAux implements Command {

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
           if (f.isAnnotationPresent(JdbcColumn.class)) {
                f.setAccessible(true);
                jdbcModel.addParam(f.getAnnotation(JdbcColumn.class).value() , f.get(entity));

            }else if(  f.isAnnotationPresent(JdbcFkIdentity.class) ){
               
                f.setAccessible(true);
                Object fkObj = f.get(entity);
                if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();

                if(  !fkObj.getClass().isAnnotationPresent(JdbcTable.class) )
                throw new Exception( String.format("entity %s has no @JdbcTable.class",fkObj.getClass()));

                Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
                for (Field fFk : fieldsFk) 
                    if (fFk.isAnnotationPresent(JdbcIdentity.class)){
                        fFk.setAccessible(true);
                        jdbcModel.addParam(f.getAnnotation(JdbcFkIdentity.class).value() , fFk.get(fkObj));
                    }
                      
            }
        }

        return jdbcModel;
    }

    @Override
    public String buildCommand(JdbcModel jdbcModel, int dataBase) throws Exception {
    
        
        if( dataBase == DataBase.MY_SQL ){
           
            return String.format( " insert into %s ( %s ) values ( %s ) " , 
                        jdbcModel.getTableName(), 
                        jdbcModel.getValues().entrySet().stream().map( e ->  "`" + e.getKey() + "`" ).collect( Collectors.joining(",")) , 
                        jdbcModel.getValues().entrySet().stream().map( e ->  " ? " ).collect( Collectors.joining(",")) );

        }else if( dataBase == DataBase.SQL_SERVER ){
           
            return String.format( " insert into %s ( %s ) values ( %s ) " , 
            jdbcModel.getTableName(), 
            jdbcModel.getValues().entrySet().stream().map( e ->  "[" + e.getKey() + "]" ).collect( Collectors.joining(",")) , 
            jdbcModel.getValues().entrySet().stream().map( e ->  " ? " ).collect( Collectors.joining(",")) );

        }else{
            throw new Exception(DataBase.class.getName() + " int value was not found. Try chose a valid constant database");
        }
 
    }


    @Override
    public <T> T doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn) throws Exception {
     
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

     int inserted =  jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(jdbcModel.getCommandBuilt(), Statement.RETURN_GENERATED_KEYS );
            for (String key : jdbcModel.getValues().keySet()) {
                statement.setObject(jdbcModel.getPositions().get(key), jdbcModel.getValues().get(key));
            }
            return statement;
    }, holder);

        return inserted == 0 ?  typeReturn.cast(null) :  typeReturn.cast(holder.getKey().longValue());
    }
    
    
}
