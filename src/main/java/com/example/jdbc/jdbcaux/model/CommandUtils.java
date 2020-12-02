package com.example.jdbc.jdbcaux.model;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.example.jdbc.jdbcaux.annotations.JdbcColumn;
import com.example.jdbc.jdbcaux.annotations.JdbcColumnSelect;
import com.example.jdbc.jdbcaux.annotations.JdbcFkIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcIdentity;
import com.example.jdbc.jdbcaux.annotations.JdbcTable;

public abstract class CommandUtils {
    

    protected void genericCheckingAnnotationsSelect(Object entity) throws Exception {
        if (entity == null)
            throw new NullPointerException("entity is null on inserting");
        if (!entity.getClass().isAnnotationPresent(JdbcTable.class))
            throw new Exception( String.format("entity %s has no @JdbcTable.class",entity.getClass()));

        Field[] fields = entity.getClass().getDeclaredFields();

        boolean hasAtLeastOneJdbcColumnSelect = false;

        for (Field f : fields) {
            if( f.isAnnotationPresent(JdbcColumnSelect.class)  ){
            
                if( 
                    !f.getType().isAssignableFrom(LocalDate.class) &&
                    !f.getType().isAssignableFrom(Double.class) &&
                    !f.getType().isAssignableFrom(Float.class) &&
                    !f.getType().isAssignableFrom(String.class) &&
                    !f.getType().isAssignableFrom(Integer.class) &&
                    !f.getType().isAssignableFrom(Long.class) &&
                    !f.getType().isAssignableFrom(LocalDateTime.class)
                      )
                throw new Exception( String.format("@JdbcColumnSelect.class on entity %s can be used only in LocalDate, LocalDateTime, Float, String, Integer e Long Type attributes",entity.getClass()));

                hasAtLeastOneJdbcColumnSelect = true;

            }
        }


        if( !hasAtLeastOneJdbcColumnSelect ) 
        throw new Exception( String.format("%s must contain at least one attribute with @JdbcColumnSelect.class",entity.getClass()));

        
    }

    protected void genericCheckingAnnotations(Object entity) throws Exception {
       
      
    if (entity == null)
        throw new NullPointerException("entity is null on inserting");
    if (!entity.getClass().isAnnotationPresent(JdbcTable.class))
        throw new Exception( String.format("entity %s has no @JdbcTable.class",entity.getClass()));

    Field[] fields = entity.getClass().getDeclaredFields();

    boolean needsToHaveJdbcFkIdentity = false;
    boolean needsToHaveJdbcIdentity = true;
    boolean needsToHaveJdbcColumn = true;

    for (Field f : fields) {
        if( f.isAnnotationPresent(JdbcIdentity.class)  ){
          
            
            if( !needsToHaveJdbcIdentity ) throw new Exception( String.format("entity %s has duplicated @JdbcIdentity.class",entity.getClass()));
            if(  f.isAnnotationPresent(JdbcColumn.class)  ) 
            throw new Exception( String.format("attributes cannot be @JdbcIdentity.class and @JdbcColumn.class at the same time on entity %s",entity.getClass()));

            if( 
                !f.getType().isAssignableFrom(LocalDate.class) &&
                !f.getType().isAssignableFrom(Double.class) &&
                !f.getType().isAssignableFrom(Float.class) &&
                !f.getType().isAssignableFrom(String.class) &&
                !f.getType().isAssignableFrom(Integer.class) &&
                !f.getType().isAssignableFrom(Long.class) &&
                !f.getType().isAssignableFrom(LocalDateTime.class)
                  )
            throw new Exception( String.format("@JdbcIdentity.class on entity %s can be used only in LocalDate, LocalDateTime, Float, String, Integer e Long Type attributes",entity.getClass()));

            
            
            needsToHaveJdbcIdentity = false;

        }else if (f.isAnnotationPresent(JdbcColumn.class)) {


            if( 
                !f.getType().isAssignableFrom(LocalDate.class) &&
                !f.getType().isAssignableFrom(Double.class) &&
                !f.getType().isAssignableFrom(Float.class) &&
                !f.getType().isAssignableFrom(String.class) &&
                !f.getType().isAssignableFrom(Integer.class) &&
                !f.getType().isAssignableFrom(Long.class) &&
                !f.getType().isAssignableFrom(LocalDateTime.class)
                  )
            throw new Exception( String.format("@JdbcColumn.class on entity %s can be used only in LocalDate, LocalDateTime, Float, String, Integer e Long Type attributes",entity.getClass()));


            needsToHaveJdbcColumn = false;
        }else if(  f.isAnnotationPresent(JdbcFkIdentity.class) ){
           
            f.setAccessible(true);

            if( 
                f.getType().isPrimitive() ||
                f.getType().isArray() ||
                f.getType().isAssignableFrom(LocalDate.class) ||
                f.getType().isAssignableFrom(Double.class) ||
                f.getType().isAssignableFrom(Float.class) ||
                f.getType().isAssignableFrom(String.class) ||
                f.getType().isAssignableFrom(Integer.class) ||
                f.getType().isAssignableFrom(Long.class) ||
                f.getType().isAssignableFrom(LocalDateTime.class)
                  )
            throw new Exception( String.format("@JdbcFkIdentity.class on entity %s cannot be used in %s attribute",entity.getClass(), f.getName() ));


            Object fkObj = f.get(entity);
            if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();

            if(  !fkObj.getClass().isAnnotationPresent(JdbcTable.class) )
            throw new Exception( String.format("entity %s has no @JdbcTable.class",fkObj.getClass()));

            Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
            needsToHaveJdbcFkIdentity = true;
            
            for (Field fFk : fieldsFk){
                if (fFk.isAnnotationPresent(JdbcIdentity.class)){

                    if( !needsToHaveJdbcFkIdentity ) throw new Exception( String.format("entity %s has duplicated @JdbcIdentity.class",fkObj.getClass()));
                    if(  fFk.isAnnotationPresent(JdbcColumn.class)  ) 
                    throw new Exception( String.format("attributes cannot be @JdbcIdentity.class and @JdbcColumn.class at the same time on entity %s",fkObj.getClass()));
        
                   
                    if( 
                        !fFk.getType().isAssignableFrom(Integer.class) &&
                        !fFk.getType().isAssignableFrom(Long.class) 
                          )
                    throw new Exception( String.format("@JdbcIdentity.class on entity %s can be used only in Integer e Long Type attributes",fkObj.getClass()));
        
                    needsToHaveJdbcFkIdentity = false;

                } 
            }
                   
          
            if( needsToHaveJdbcFkIdentity )
            throw new Exception( String.format("entity %s has no @JdbcIdentity.class",fkObj.getClass()));

        }
    }

    if( needsToHaveJdbcIdentity )
    throw new Exception( String.format("entity %s has no @JdbcIdentity.class",entity.getClass()));

    if( needsToHaveJdbcColumn )
    throw new Exception( String.format("entity %s must have at least one attr with @JdbcColumn.class",entity.getClass()));



    }
    

    protected <T> T getObjFromMap(Map<String, Object> mapObject , Class<T> classToReturn) throws Exception {

        Object obj = classToReturn.getDeclaredConstructor().newInstance();

        for (Map.Entry<String,Object> entry : mapObject.entrySet()) {
                if( entry.getValue() != null  ){
                                                
                        Field[] field = obj.getClass().getDeclaredFields();

                        for (Field f : field) {
                
                                    f.setAccessible(true);
                                    if(  f.isAnnotationPresent(JdbcColumn.class) &&  entry.getKey().equals(f.getAnnotation(JdbcColumn.class).value())  ){
                                     
                                        f.set(obj, parseValueIntoAllowedTypes(entry.getValue(), f.getType())   );

                                    }else if( f.isAnnotationPresent(JdbcIdentity.class) &&  entry.getKey().equals(f.getAnnotation(JdbcIdentity.class).value()) ) {
                                 
                                        f.set(obj, parseValueIntoAllowedTypes(entry.getValue(), f.getType())   );
                                    }else if( f.isAnnotationPresent(JdbcFkIdentity.class)   &&  entry.getKey().equals(f.getAnnotation(JdbcFkIdentity.class).value()) ) {
                                        
                                        f.setAccessible(true);

                                        Object fkObj = f.get(obj);
                                        if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();


                                        Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
                                        for (Field fFk : fieldsFk){

                                            fFk.setAccessible(true);

                                            if (fFk.isAnnotationPresent(JdbcIdentity.class)){
                                                fFk.set(fkObj, parseValueIntoAllowedTypes(entry.getValue(), fFk.getType())   );
                                                f.set(obj, fkObj);
                                            }
                                        }

                                    }
                
                            
                        }
                    }
        }

        return classToReturn.cast(obj);
    }


    @SuppressWarnings("unchecked")
    private <T> T parseValueIntoAllowedTypes(  Object value , Class<T> typeCondition ){

        if( typeCondition.isAssignableFrom(LocalDate.class)   ){
            return (T)((Date) value).toLocalDate();
        }else if ( typeCondition.isAssignableFrom(LocalDateTime.class) ){
            return (T)((Timestamp) value).toLocalDateTime();
        }else if (typeCondition.isAssignableFrom(Double.class) ){
            return (T) Double.valueOf( String.valueOf( value));
        }else if ( typeCondition.isAssignableFrom(Float.class) ){
            return (T) Float.valueOf( String.valueOf( value));
        }else if ( typeCondition.isAssignableFrom(String.class) ){
            return (T) String.valueOf(value);
        }else if ( typeCondition.isAssignableFrom(Integer.class) ){
            return (T) Integer.valueOf( String.valueOf( value));
        }else if ( typeCondition.isAssignableFrom(Long.class) ){
            return (T) Long.valueOf( String.valueOf( value ) );
        }else{
            return typeCondition.cast(value);
        }
    }


    protected <T> T getSelectObjFromMap(Map<String, Object> mapObject , Class<T> classToReturn) throws Exception {

        Object obj = classToReturn.getDeclaredConstructor().newInstance();

        for (Map.Entry<String,Object> entry : mapObject.entrySet()) {
                if( entry.getValue() != null  ){
                                                
                        Field[] field = obj.getClass().getDeclaredFields();

                        for (Field f : field) {
                
                                    f.setAccessible(true);
                                    if(  f.isAnnotationPresent(JdbcColumnSelect.class) &&  entry.getKey().equals(f.getAnnotation(JdbcColumnSelect.class).value())  ){
                                        f.set(obj, parseValueIntoAllowedTypes(entry.getValue(), f.getType())   );
                                    }
                            
                        }
                    }
        }

        return classToReturn.cast(obj);
    }


}