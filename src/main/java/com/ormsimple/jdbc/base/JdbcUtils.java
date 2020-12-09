package com.ormsimple.jdbc.base;

import java.lang.reflect.Field;

import com.ormsimple.jdbc.annotation.Id;

public class JdbcUtils {
    

    public static <E> E getInstanceWithId(Class<E> classEntity, Object valueId) throws Exception{

        E obj = classEntity.getDeclaredConstructor().newInstance();
   
        Field[] field = obj.getClass().getDeclaredFields();

        for (Field f : field) {
                f.setAccessible(true);
                if(  f.isAnnotationPresent(Id.class) ){
                    f.set(obj, valueId);
                }
            }
        return obj;
    }


    public static void validateSelectSql(String regexSelect) throws Exception{
        if( regexSelect == null )   throw new Exception("select cannot be null");
        regexSelect =  regexSelect.toLowerCase();
		if( regexSelect.contains("inner")){
			if(  !regexSelect.matches(".*select .*? from .*? join .*") ){
                            throw new Exception("select dosen't match to '.*select .*? from .*? join .*' ");
                    }	 
		}else if( regexSelect.contains(" where ") ){
			if(  !regexSelect.matches(".*select .*? from .*? where .*")){
                throw new Exception("select dosen't match to '.*select .*? from .*? where .*' ");
            }
		}else{ 
			if( !regexSelect.matches(".*select .*? from .*") ){
                throw new Exception("select dosen't match to '.*select .*? from .*' ");
            }
		}

    }
    
    public static void validateUpdateSql(String regexUpdate) throws Exception{
		if( regexUpdate == null )   throw new Exception("Update cannot be null");
        regexUpdate = regexUpdate.toLowerCase();
		if(  !regexUpdate.matches(".*update .*? set .*") ){
                            throw new Exception("update dosen't match to '.*update .*? set .*' "); 
		}

    }

    public static void validateDeleteSql(String regexDelete) throws Exception{
		if( regexDelete == null )   throw new Exception("Delete cannot be null");
        regexDelete = regexDelete.toLowerCase();
		if(  !regexDelete.matches(".*delete .*? from .*") ){
                            throw new Exception("Delete dosen't match to '.*delete .*? from .*' "); 
		}

    }

    

    public static Class<?> getTypeClassFromId(Class<?> classEntity) throws Exception{

        Object obj = classEntity.getDeclaredConstructor().newInstance();
   
        Field[] field = obj.getClass().getDeclaredFields();

        for (Field f : field) {
                f.setAccessible(true);
                if(  f.isAnnotationPresent(Id.class) ){
                    return f.getType();
                }
            }
        return null;
    }

}
