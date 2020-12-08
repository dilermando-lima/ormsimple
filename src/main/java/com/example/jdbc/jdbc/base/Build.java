package com.example.jdbc.jdbc.base;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.example.jdbc.jdbc.annotation.Col;
import com.example.jdbc.jdbc.annotation.ColSelect;
import com.example.jdbc.jdbc.annotation.Fk;
import com.example.jdbc.jdbc.annotation.Id;
import com.example.jdbc.jdbc.annotation.Table;
import com.example.jdbc.jdbc.exec.CommandSelectByID;
import com.example.jdbc.jdbc.model.BaseCommandDelete;
import com.example.jdbc.jdbc.model.BaseCommandSelect;
import com.example.jdbc.jdbc.model.BaseCommandUpdate;
import com.example.jdbc.jdbc.model.Model;

import org.springframework.jdbc.core.JdbcTemplate;



public abstract class Build extends Validate {
    

    protected Model buildModelFromObj(Object entity) throws Exception {
    Model model = new Model();
    model.setTable(entity.getClass().getAnnotation(Table.class).value());
    Field[] fields = entity.getClass().getDeclaredFields();
  
    for (Field f : fields) {
        f.setAccessible(true);

        if(  f.isAnnotationPresent(Id.class) ){
            model.setId(f.getAnnotation(Id.class).value(),  f.get(entity));
        }
        
        if (f.isAnnotationPresent(Col.class)) {
            model.addBaseParam(f.getAnnotation(Col.class).value(),  f.get(entity));
        }
        
        if(  f.isAnnotationPresent(Fk.class) ){
           
            f.setAccessible(true);
            Object fkObj = f.get(entity);
            if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();

            Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
            for (Field fFk : fieldsFk){
                fFk.setAccessible(true);
                if (fFk.isAnnotationPresent(Id.class)){
                    model.addBaseParam(f.getAnnotation(Fk.class).value(),   fFk.get(fkObj));
                }
            }
                  
        }
    }

        return model;

    }

    protected Model buildModelFromSelect(Class<?> classEntity, BaseCommandSelect select) throws Exception{
        Model model = new Model();
        if(  classEntity != null  ){
            model.setTable(classEntity.getAnnotation(Table.class).value());
        }
        for (Object obj : select.getListValues()) {
            model.addValue(obj);
        }
        return model;
    }

    protected Model buildModelFromUpdate(Class<?> classEntity, BaseCommandUpdate update) throws Exception{
        Model model = new Model();
        if(  classEntity != null  ){
            model.setTable(classEntity.getAnnotation(Table.class).value());
        }
        for (Object obj : update.getListValues()) {
            model.addValue(obj);
        }
        return model;
    }

    protected Model buildModelFromDelete(Class<?> classEntity, BaseCommandDelete delete) throws Exception{
        Model model = new Model();
        if(  classEntity != null  ){
            model.setTable(classEntity.getAnnotation(Table.class).value());
        }
        for (Object obj : delete.getListValues()) {
            model.addValue(obj);
        }
        return model;
    }

    protected Model buildSelectModelFromObj(Object entity) throws Exception {
        Model model = new Model();
        model.setTable(entity.getClass().getAnnotation(Table.class).value());
        Field[] fields = entity.getClass().getDeclaredFields();
      
        for (Field f : fields) {
            f.setAccessible(true);
    
            if(  f.isAnnotationPresent(Id.class) ){
                model.setId(f.getAnnotation(Id.class).value(),  f.get(entity));
            }
            
            if (f.isAnnotationPresent(ColSelect.class) &&  !f.isAnnotationPresent(Fk.class)) {
                model.addBaseParam(f.getAnnotation(ColSelect.class).value(),  f.get(entity));
            }
            
            if(  f.isAnnotationPresent(ColSelect.class) && f.isAnnotationPresent(Fk.class) ){
               
                f.setAccessible(true);
                Object fkObj = f.get(entity);
                if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();
    
                Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
                for (Field fFk : fieldsFk){
                    fFk.setAccessible(true);
                    if (fFk.isAnnotationPresent(Id.class)){
                        model.addBaseParam(f.getAnnotation(Fk.class).value(),   fFk.get(fkObj));
                    }
                }
                      
            }
        }
    
            return model;
    
        }




    protected <T> T getObjFromMap(Map<String, Object> mapObject , Class<T> classToReturn, boolean takeNested, JdbcTemplate jdbcTemplate) throws Exception {

        Object obj = classToReturn.getDeclaredConstructor().newInstance();

        for (Map.Entry<String,Object> entry : mapObject.entrySet()) {
                if( entry.getValue() != null  ){
                                                
                        Field[] field = obj.getClass().getDeclaredFields();

                        for (Field f : field) {
                
                                    f.setAccessible(true);
                                    if(  f.isAnnotationPresent(Col.class) &&  entry.getKey().equals(f.getAnnotation(Col.class).value())  ){
                                        f.set(obj, parseValueIntoAllowedTypes(entry.getValue(), f.getType())   );

                                    }else if( f.isAnnotationPresent(Id.class) &&  entry.getKey().equals(f.getAnnotation(Id.class).value()) ) {
                                        f.set(obj, parseValueIntoAllowedTypes(entry.getValue(), f.getType())   );
                                    
                                    }else if( f.isAnnotationPresent(Fk.class)   &&  entry.getKey().equals(f.getAnnotation(Fk.class).value()) ) {
                                        
                                        Object fkObj = f.get(obj);
                                        if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();


                                        Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
                                        for (Field fFk : fieldsFk){

                                            fFk.setAccessible(true);

                                            if (fFk.isAnnotationPresent(Id.class)){
                                                fFk.set(fkObj, parseValueIntoAllowedTypes(entry.getValue(), fFk.getType())   );

                                                if(  takeNested   ){
                                                    if( fkObj.getClass().isAssignableFrom( obj.getClass() )   ) 
                                                    throw new Exception( String.format("attr %s cannot be the same type than %s in order to won't loop reading entities", f.getName(), obj.getClass()  ));
                  

                                                    fkObj = new CommandSelectByID().doCommand( fkObj, jdbcTemplate, fkObj.getClass(), takeNested);
                                                }
                                              
                                                f.set(obj, fkObj);
                                            }
                                        }
                                    }
                
                            
                        }
                    }
        }

        return classToReturn.cast(obj);
    }


    protected <T> T getObjFromMapSelect(Map<String, Object> mapObject , Class<T> classToReturn,  boolean takeNested, JdbcTemplate jdbcTemplate) throws Exception {

        Object obj = classToReturn.getDeclaredConstructor().newInstance();

        for (Map.Entry<String,Object> entry : mapObject.entrySet()) {
                if( entry.getValue() != null  ){
                                                
                        Field[] field = obj.getClass().getDeclaredFields();

                        for (Field f : field) {
                
                                    f.setAccessible(true);
                                    if( f.isAnnotationPresent(ColSelect.class) && !f.isAnnotationPresent(Fk.class) &&  entry.getKey().equals(f.getAnnotation(ColSelect.class).value()) ) {
                                        
                                        f.set(obj, parseValueIntoAllowedTypes(entry.getValue(), f.getType())   );
                                    
                                    }else if( f.isAnnotationPresent(ColSelect.class) && f.isAnnotationPresent(Fk.class)   &&  entry.getKey().equals(f.getAnnotation(ColSelect.class).value()) ) {
                                        
            
                                        Object fkObj = f.get(obj);
                                        if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();

                                        Field[] fieldsFk = fkObj.getClass().getDeclaredFields();
                                        for (Field fFk : fieldsFk){

                                            fFk.setAccessible(true);

                                            if (fFk.isAnnotationPresent(Id.class)){
                                                fFk.set(fkObj, parseValueIntoAllowedTypes(entry.getValue(), fFk.getType())   );

                                                if(  takeNested   ){
                                                    if( fkObj.getClass().isAssignableFrom( obj.getClass() )   ) 
                                                    throw new Exception( String.format("attr %s cannot be the same type than %s in order to won't loop reading entities", f.getName(), obj.getClass()  ));
                  

                                                    fkObj = new CommandSelectByID().doCommand( fkObj, jdbcTemplate, fkObj.getClass(), takeNested);
                                                }
                                                
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
    protected  <T> T parseValueIntoAllowedTypes(  Object value , Class<T> typeCondition ){

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



}
