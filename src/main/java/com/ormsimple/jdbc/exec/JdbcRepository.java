package com.ormsimple.jdbc.exec;

import java.util.List;

import com.ormsimple.jdbc.base.JdbcUtils;
import com.ormsimple.jdbc.model.Select;
import com.ormsimple.jdbc.model.SelectCustom;
import com.ormsimple.jdbc.model.Update;
import com.ormsimple.jdbc.model.UpdatePatch;

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


   public void exec(String script) throws Exception {
      jdbcTemplate.execute(script);
   }

   
   public <E> E insert(Object entity , Class<E> classEntity, boolean takeNested ) throws Exception {

      return selectById  (
                           JdbcUtils.getInstanceWithId(
                              classEntity, 
                              new CommandInsert().doCommand(entity.getClass(), entity, jdbcTemplate, JdbcUtils.getTypeClassFromId(classEntity))
                           ),
                           classEntity, takeNested );

                           
   }

   public int []  insertBatch(Class<?> classEntities, List<?> entities)throws Exception {
      return new CommandInsertBatch().doCommand(classEntities, entities, jdbcTemplate);
   }


   public void updateEntity(Object entity ) throws Exception {
      new CommandUpdateEntity().doCommand(entity.getClass(), entity, jdbcTemplate, null );
   }


   public <E> E updateEntity(Object entity , Class<E> classEntity, boolean takeNested ) throws Exception {
      return selectById  (
                           JdbcUtils.getInstanceWithId(
                              classEntity, 
                              new CommandUpdateEntity().doCommand(entity.getClass(), entity, jdbcTemplate, JdbcUtils.getTypeClassFromId(classEntity))
                           ),
                           classEntity, takeNested);

   }


   public <E> E updatePatch(Object id, UpdatePatch updatePatch , Class<E> classEntity, boolean takeNested ) throws Exception {
      return selectById  (
                           JdbcUtils.getInstanceWithId(
                              classEntity, 
                              new CommandUpdatePatch().doCommand(id, classEntity, updatePatch, jdbcTemplate, JdbcUtils.getTypeClassFromId(classEntity))
                           ),
                           classEntity, takeNested);
   }


   public void updatePatch(Object id, UpdatePatch updatePatch , Class<?> classEntity ) throws Exception {
       new CommandUpdatePatch().doCommand(id, classEntity, updatePatch, jdbcTemplate, null);
   }


   public int update(Update update ) throws Exception {
      return new CommandUpdateCustom().doCommand(update, jdbcTemplate);
   }

   
   public void deleteEntity(Object entity ) throws Exception {
       new CommandDeleteEntity().doCommand(entity.getClass(), entity, jdbcTemplate, JdbcUtils.getTypeClassFromId(entity.getClass()) );
   }

   public <I> I deleteEntity(Object entity, Class<I> classTypeId ) throws Exception {
      return  new CommandDeleteEntity().doCommand(entity.getClass(), entity, jdbcTemplate, classTypeId );
  }

   public <E> E selectById(Object value, Class<E> classEntity, boolean takeNested) throws Exception {

      if(  classEntity.isAssignableFrom(value.getClass())  ){
         return new CommandSelectByID().doCommand(value, jdbcTemplate, classEntity, takeNested );
      }else{
         E objWithInstance = JdbcUtils.getInstanceWithId( classEntity,  value  );
         return new CommandSelectByID().doCommand( objWithInstance, jdbcTemplate, classEntity, takeNested );
      }

   }

   public <E> List<E> selectEntity(Class<E> classEntity, boolean takeNested, Select select ) throws Exception{
         return new CommandSelectList().doCommand(select, jdbcTemplate, classEntity, takeNested);
   }

   public <E> List<E> select(Class<E> classEntity, SelectCustom select ) throws Exception{
      return new CommandSelectListCustom().doCommand(select, jdbcTemplate, classEntity, false);
   }

   public <E> E selectFirstOne(Class<E> classEntity, Select select ) throws Exception{
      return new CommandSelectFirstOne().doCommand(select, jdbcTemplate, classEntity, false);
   }

   public <E> E selectOneVal(Class<E> classReturn, Select select ) throws Exception{
      return new CommandSelectOneVal().doCommand(select, jdbcTemplate, classReturn);
   }


}
