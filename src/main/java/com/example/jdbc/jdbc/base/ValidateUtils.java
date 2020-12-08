package com.example.jdbc.jdbc.base;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.jdbc.jdbc.annotation.Col;
import com.example.jdbc.jdbc.annotation.ColSelect;
import com.example.jdbc.jdbc.annotation.Fk;
import com.example.jdbc.jdbc.annotation.Id;
import com.example.jdbc.jdbc.annotation.Table;
import com.example.jdbc.jdbc.model.Model;

public class ValidateUtils {

   

    public static  void validateTypeAllowed(Class<?> type)  throws Exception{
        if( 
            !type.isAssignableFrom(LocalDate.class) &&
            !type.isAssignableFrom(Double.class) &&
            !type.isAssignableFrom(Float.class) &&
            !type.isAssignableFrom(String.class) &&
            !type.isAssignableFrom(Integer.class) &&
            !type.isAssignableFrom(Long.class) &&
            !type.isAssignableFrom(LocalDateTime.class)
        )
        throw new Exception( String.format("Attr %s can be used only with LocalDate, LocalDateTime, Float, String, Integer e Long Type at JDBC annotations",  type.getName()));
  
    }

    public static void validateTypeAllowedOnFields(Class<?> classEntity) throws Exception{
       

        Field[] fields = classEntity.getDeclaredFields();
        for (Field f : fields) {
            
            if( f.isAnnotationPresent(Fk.class)  ){
                 if(  !f.getType().isAnnotationPresent(Table.class)  )
                 throw new Exception( String.format("attr %s on entity %s cannot be used the annotation @Fk() becouse class %s dosen't have @Table annotation ",f.getName(), classEntity, f.getType() ));
            }else{
                if( f.isAnnotationPresent(Id.class) || f.isAnnotationPresent(Col.class) ||  f.isAnnotationPresent(ColSelect.class)  ){
                    validateTypeAllowed(f.getType());
                }
            }
        }

     }

     public static void validateTableNotation(Class<?> classEntity) throws Exception{
      
        if (classEntity == null)
            throw new NullPointerException( Model.class.getClass() + ".getClassEntity() cannot be null");
        
        if (!classEntity.isAnnotationPresent(Table.class))
             throw new NullPointerException( classEntity + " must have a annotation " + Table.class.getClass());

    }



    public static  void validateFieldNotationsEntity(Class<?> entityClass) throws Exception{

        Object entity = entityClass.getConstructor().newInstance();
        Field[] fields = entity.getClass().getDeclaredFields();


        String nameId = null;
        String mameFk = null;
        String nameCol = null;
        String nameColSelect = null;

        for (Field f : fields) {
            f.setAccessible(true);

            if( f.isAnnotationPresent(Id.class)  ){
                if( nameId != null ) throw new Exception( String.format("entity %s must have only one attr with @Id.class annotation", entityClass));
                nameId = f.getAnnotation(Id.class).value();
            }

            if( f.isAnnotationPresent(Fk.class)  ){
               
                if(  mameFk != null && mameFk.equals(f.getAnnotation(Fk.class).value()) )
                throw new Exception( String.format("entity %s has duplicated column name at @Fk annotation", entityClass));
               
                mameFk = f.getAnnotation(Fk.class).value();

                    Object fkObj = f.get(entity);
                    if(  fkObj == null ) fkObj = f.getType().getDeclaredConstructor().newInstance();

                    if(  f.getType().isAssignableFrom( entityClass)  )
                    throw new Exception( String.format("attr %s cannot be the same type than %s in order to won't loop reading entities", f.getName(), entityClass  ));
                    
                    validateFieldNotationsEntity(fkObj.getClass());

                }

            if( f.isAnnotationPresent(Col.class)  ){

                if(  nameCol != null && nameCol.equals(f.getAnnotation(Col.class).value()) )
                throw new Exception( String.format("entity %s has duplicated column name at @Col annotation", entityClass));
            
                nameCol = f.getAnnotation(Col.class).value();
            }

            if( f.isAnnotationPresent(ColSelect.class)  ){

                if(  nameColSelect != null && nameColSelect.equals(f.getAnnotation(ColSelect.class).value()) )
                throw new Exception( String.format("entity %s has duplicated column name at @ColSelect annotation", entityClass));

                nameColSelect = f.getAnnotation(ColSelect.class).value();
            }
        }

        if( nameId == null ) 
        throw new Exception( String.format("entity %s has no annotation @Id.class",entityClass));

        if( nameCol == null && nameColSelect == null ){
            throw new Exception( String.format("entity %s has no annotation @Col.class or has no @ColSelect.class",entityClass));
        }


    }

    

}
