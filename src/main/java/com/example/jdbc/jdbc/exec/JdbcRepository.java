package com.example.jdbc.jdbc.exec;

import java.util.List;

import com.example.jdbc.jdbc.base.JdbcUtils;
import com.example.jdbc.jdbc.model.BaseCommandSelect;
import com.example.jdbc.jdbc.model.BaseCommandUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcRepository {
   
   @Autowired
   private JdbcTemplate jdbcTemplate;

   // OK
   public void insert(Object entity ) throws Exception {
         new CommandInsert().doCommand(entity.getClass(), entity, jdbcTemplate, null );
   }

   //OK
   public void exec(String script) throws Exception {
      jdbcTemplate.execute(script);
   }

   //OK
   public <E> E insert(Object entity , Class<E> classEntity, boolean takeNested ) throws Exception {

      return selectById  (
                           JdbcUtils.getInstanceWithId(
                              classEntity, 
                              new CommandInsert().doCommand(entity.getClass(), entity, jdbcTemplate, JdbcUtils.getTypeClassFromId(classEntity))
                           ),
                           classEntity, takeNested );

                           
   }

   //OK
   public int []  insertBatch(Class<?> classEntities, List<?> entities)throws Exception {
      return new CommandInsertBatch().doCommand(classEntities, entities, jdbcTemplate);
   }

   //OK
   public void updateEntity(Object entity ) throws Exception {
      new CommandUpdateEntity().doCommand(entity.getClass(), entity, jdbcTemplate, null );
   }

   // OK
   public <E> E updateEntity(Object entity , Class<E> classEntity, boolean takeNested ) throws Exception {
      return selectById  (
                           JdbcUtils.getInstanceWithId(
                              classEntity, 
                              new CommandUpdateEntity().doCommand(entity.getClass(), entity, jdbcTemplate, JdbcUtils.getTypeClassFromId(classEntity))
                           ),
                           classEntity, takeNested);

   }

   /* criar um update patch
   public <E> E updateEntity(Class<E> classEntity, BaseCommandUpdate update, boolean takeNested  ) throws Exception {
        Object id =  new CommandUpdateCustom().doCommand(update, jdbcTemplate, JdbcUtils.getTypeClassFromId(classEntity));
        return selectById(id, classEntity, takeNested);
   }*/


   //OK
   public int update(BaseCommandUpdate update ) throws Exception {
      return new CommandUpdateCustom().doCommand(update, jdbcTemplate);
   }

   
   public void deleteEntity(Object entity ) throws Exception {
       new CommandDeleteEntity().doCommand(entity.getClass(), entity, jdbcTemplate, JdbcUtils.getTypeClassFromId(entity.getClass()) );
   }

   public <I> I deleteEntity(Object entity, Class<I> classTypeId ) throws Exception {
      return  new CommandDeleteEntity().doCommand(entity.getClass(), entity, jdbcTemplate, classTypeId );
  }


   //OK
   public <E> E selectById(Object value, Class<E> classEntity, boolean takeNested) throws Exception {

      if(  classEntity.isAssignableFrom(value.getClass())  ){
         return new CommandSelectByID().doCommand(value, jdbcTemplate, classEntity, takeNested );
      }else{
         E objWithInstance = JdbcUtils.getInstanceWithId( classEntity,  value  );
         return new CommandSelectByID().doCommand( objWithInstance, jdbcTemplate, classEntity, takeNested );
      }

   }

   public <E> List<E> selectEntity(Class<E> classEntity, boolean takeNested, BaseCommandSelect select ) throws Exception{
         return new CommandSelectList().doCommand(select, jdbcTemplate, classEntity, takeNested);
   }

   public <E> List<E> select(Class<E> classEntity, BaseCommandSelect select ) throws Exception{
      return new CommandSelectList().doCommand(select, jdbcTemplate, classEntity, false);
   }

   public <E> E selectFirstOne(Class<E> classEntity, BaseCommandSelect select ) throws Exception{
      return new CommandSelectFirstOne().doCommand(select, jdbcTemplate, classEntity, false);
   }

   public <E> E selectOneVal(Class<E> classReturn, BaseCommandSelect select ) throws Exception{
      return new CommandSelectOneVal().doCommand(select, jdbcTemplate, classReturn);
   }


}
