package com.example.jdbc.jdbcaux.operations;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcFkIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;
import com.example.jdbc.jdbcaux.model.CommandAux;
import com.example.jdbc.jdbcaux.model.CommandBatch;
import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.JdbcModel;
import com.example.jdbc.jdbcaux.model.JdbcModelBatch;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class CommandUpdateBatchImp extends CommandAux implements CommandBatch {

    @Override
    public void chekingAnnotations( List<?>   entityList) throws Exception {
        if( entityList == null || entityList.isEmpty() )  throw new Exception("List cannot be empty on updateBatch");
        genericCheckingAnnotations(entityList.get(0));
    }

    @Override
    public JdbcModelBatch prepareValues( List<?>   entityList) throws Exception {
        
        JdbcModelBatch jdbcModelBatch =  new JdbcModelBatch();
        
        for (Object entity : entityList) {

            JdbcModel jdbcModel = new JdbcModel();

            jdbcModel.setTableName(entity.getClass().getAnnotation(JdbcTable.class).value());
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field f : fields) {

            if(  f.isAnnotationPresent(JdbcIdentity.class) ){
                f.setAccessible(true);
                jdbcModel.addParamIdentity(f.getAnnotation(JdbcIdentity.class).value() , f.get(entity));
            }else if (f.isAnnotationPresent(JdbcColumn.class)) {
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


            jdbcModelBatch.addJdbcModel(jdbcModel);
        }
        

        return jdbcModelBatch;
    }

    @Override
    public String buildCommand(JdbcModelBatch jdbcModelBatch, int dataBase) throws Exception {
    
        
        if( dataBase == DataBase.MY_SQL ){
           
            return String.format( " update %s  set %s where %s = ?" , 
            jdbcModelBatch.getListJdbcModel().get(0).getTableName(), 
            jdbcModelBatch.getListJdbcModel().get(0).getValues().entrySet().stream().map( e ->  "`" + e.getKey() + "` = ? " ).collect( Collectors.joining(",")) , 
            jdbcModelBatch.getListJdbcModel().get(0).getNameIdentity() );

        }else if( dataBase == DataBase.SQL_SERVER ){
           
            return String.format( " update %s  set %s where %s = ?" , 
            jdbcModelBatch.getListJdbcModel().get(0).getTableName(), 
            jdbcModelBatch.getListJdbcModel().get(0).getValues().entrySet().stream().map( e ->  "[" + e.getKey() + "] = ? " ).collect( Collectors.joining(",")) , 
            jdbcModelBatch.getListJdbcModel().get(0).getNameIdentity() );

        }else{
            throw new Exception(DataBase.class.getName() + " int value was not found. Try chose a valid constant database");
        }
 
    }


    @Override
    public List<Integer> doCommand(JdbcModelBatch jdbcModelBatch, JdbcTemplate jdbcTemplate) throws Exception {
     
        int [] ret = jdbcTemplate.batchUpdate(jdbcModelBatch.getCommandBuilt() , new BatchPreparedStatementSetter(){
            
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                for (String key :  jdbcModelBatch.getListJdbcModel().get(i).getValues().keySet()) {
                  
                    ps.setObject(jdbcModelBatch.getListJdbcModel().get(i).getPositions().get(key), 
                    jdbcModelBatch.getListJdbcModel().get(i).getValues().get(key));

                }

                ps.setObject(jdbcModelBatch.getListJdbcModel().get(i).getPositions().size() + 1, jdbcModelBatch.getListJdbcModel().get(i).getValueIdentity() );
            }

            public int getBatchSize() {
                return jdbcModelBatch.getListJdbcModel().size();
            }

        });

        return ret.length == 0 ? new ArrayList<Integer>() : IntStream.of(ret).boxed().collect(Collectors.toList());

    }
    
}
