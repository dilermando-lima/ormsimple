package com.example.jdbc.jdbcaux.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.Select;
import com.example.jdbc.jdbcaux.operations.JdbcExec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class JdbcRepository<E,I> {

   private final Class<E> typeParameterClassEntity;
   private final Class<I> typeParameterClassId;
   private int dataBase;

   protected void setDataBase(int dataBase) {
      this.dataBase = dataBase;
   }

   @SuppressWarnings("unchecked")
   public JdbcRepository() {
      this.typeParameterClassEntity = (Class<E>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      this.typeParameterClassId = (Class<I>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
   
   }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public I insert(E entity) throws Exception {
       return  JdbcExec.insert(entity, jdbcTemplate, dataBase == 0 ? DataBase.MY_SQL : dataBase, typeParameterClassId);
    }

    public void exec(String script) throws Exception {
      jdbcTemplate.execute(script);
   }

    public I update(E entity) throws Exception {
        return JdbcExec.update(entity, jdbcTemplate,dataBase == 0 ? DataBase.MY_SQL : dataBase, typeParameterClassId);
     }

     public E updatePatch(E entity, Map<String,Object> mapValues) throws Exception {
      return JdbcExec.updatePatch(entity, mapValues, jdbcTemplate, dataBase == 0 ? DataBase.MY_SQL : dataBase, typeParameterClassEntity);
   }

     public E getById(E entity) throws Exception {
        return JdbcExec.selectById(entity, jdbcTemplate, dataBase == 0 ? DataBase.MY_SQL : dataBase, typeParameterClassEntity );
     }

     public List<E> select (Select select ) throws Exception {
      return JdbcExec.select(select, jdbcTemplate,  dataBase == 0 ? DataBase.MY_SQL : dataBase, typeParameterClassEntity );
   } 

     public I delete(E entity) throws Exception {
      return JdbcExec.delete(entity, jdbcTemplate, dataBase == 0 ? DataBase.MY_SQL : dataBase, typeParameterClassId);
   }

   public List<Integer> insert(List<E> listPerson) throws Exception {
      return JdbcExec.insertBatch(  listPerson, jdbcTemplate, dataBase == 0 ? DataBase.MY_SQL : dataBase);
   }

   public List<Integer> updateBatch(List<E> listPerson) throws Exception {
      return JdbcExec.updateBatch(  listPerson, jdbcTemplate, dataBase == 0 ? DataBase.MY_SQL : dataBase);
   }




}
