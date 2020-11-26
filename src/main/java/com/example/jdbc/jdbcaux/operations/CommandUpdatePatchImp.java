package com.example.jdbc.jdbcaux.operations;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcFkIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;
import com.example.jdbc.jdbcaux.model.CommandAux;
import com.example.jdbc.jdbcaux.model.CommandPatch;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.JdbcModel;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommandUpdatePatchImp  extends CommandAux implements CommandPatch {

    @Override
    public void chekingAnnotations(Object entity) throws Exception {
      genericCheckingAnnotations(entity);
    }

    @Override
    public JdbcModel prepareValues(Object entity,  Map<String,Object> mapValues) throws Exception {
        JdbcModel jdbcModel = new JdbcModel();

        jdbcModel.setTableName(entity.getClass().getAnnotation(JdbcTable.class).value());

        
        Field[] fieldsToCheckId = entity.getClass().getDeclaredFields();
        for (Field f : fieldsToCheckId) {
            if(  f.isAnnotationPresent(JdbcIdentity.class) ){
                f.setAccessible(true);
                jdbcModel.addParamIdentity(f.getAnnotation(JdbcIdentity.class).value() , f.get(entity));
            }
        }
        
        for (Map.Entry<String,Object> entry : mapValues.entrySet()) {
        
                    
                    Field[] fields = entity.getClass().getDeclaredFields();

                    for (Field f : fields) {

                        if (f.isAnnotationPresent(JdbcColumn.class)  && entry.getKey().equals(f.getAnnotation(JdbcColumn.class).value())  ) {
                            f.setAccessible(true);
                            jdbcModel.addParam(f.getAnnotation(JdbcColumn.class).value() ,entry.getValue());

                        }else if(  f.isAnnotationPresent(JdbcFkIdentity.class) && entry.getKey().equals(f.getAnnotation(JdbcFkIdentity.class).value()) ){
                        
                            f.setAccessible(true);
                            Object fkObj = f.get(entity);
                            if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();

                            if(  !fkObj.getClass().isAnnotationPresent(JdbcTable.class) )
                            throw new Exception( String.format("entity %s has no @JdbcTable.class",fkObj.getClass()));

                            Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
                            for (Field fFk : fieldsFk) 
                                if (fFk.isAnnotationPresent(JdbcIdentity.class)){
                                    fFk.setAccessible(true);
                                    jdbcModel.addParam(f.getAnnotation(JdbcFkIdentity.class).value() , entry.getValue());
                                }
                                
                        }
                    }
            
        }            

        return jdbcModel;
    }

    @Override
    public String buildCommand(JdbcModel jdbcModel, int dataBase) throws Exception {
        
        if( dataBase == DataBase.MY_SQL ){
           
            return String.format( " update %s  set %s where %s = ?" , 
            jdbcModel.getTableName(), 
            jdbcModel.getValues().entrySet().stream().map( e -> "`" + e.getKey() + "` = ? " ).collect( Collectors.joining(",")) , 
            jdbcModel.getNameIdentity() );

        }else if( dataBase == DataBase.SQL_SERVER ){
           
            return String.format( " update %s  set %s where %s = ?" , 
            jdbcModel.getTableName(), 
            jdbcModel.getValues().entrySet().stream().map( e ->  "[" + e.getKey() + "] = ? " ).collect( Collectors.joining(",")) , 
            jdbcModel.getNameIdentity() );

        }else{
            throw new Exception(DataBase.class.getName() + " int value was not found. Try chose a valid constant database");
        }
    }

    @Override
    public <T> T doCommand(JdbcModel jdbcModel, JdbcTemplate jdbcTemplate, Class<T> typeReturn) throws Exception {
 
  

       int updated =  jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(jdbcModel.getCommandBuilt());

            for (String key : jdbcModel.getValues().keySet()) {
                statement.setObject(jdbcModel.getPositions().get(key), jdbcModel.getValues().get(key));
            }

            statement.setObject(jdbcModel.getPositions().size() + 1, jdbcModel.getValueIdentity() );

            return statement;
        });

        return updated == 0 ? typeReturn.cast(null) : typeReturn.cast(jdbcModel.getValueIdentity());
    }


    



}
