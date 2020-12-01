package com.example.jdbc.jdbcaux.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.example.jdbc.jdbcaux.model.DataBase;
import com.example.jdbc.jdbcaux.model.Select;
import com.example.jdbc.jdbcaux.operations.CommandDeleteImp;
import com.example.jdbc.jdbcaux.operations.CommandInsertBatchImp;
import com.example.jdbc.jdbcaux.operations.CommandInsertImp;
import com.example.jdbc.jdbcaux.operations.CommandSelectByIDImp;
import com.example.jdbc.jdbcaux.operations.CommandSelectImp;
import com.example.jdbc.jdbcaux.operations.CommandUpdateBatchImp;
import com.example.jdbc.jdbcaux.operations.CommandUpdateImp;
import com.example.jdbc.jdbcaux.operations.CommandUpdatePatchImp;
import com.example.jdbc.jdbcaux.operations.JdbcExec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class JdbcRepository<E, I, D extends DataBase> extends JdbcExec {

   private final Class<E> typeParameterClassEntity;
   private final Class<I> typeParameterClassId;
   private final Class<D> typeParameterClassDataBase;

 
   @Autowired
   protected JdbcTemplate jdbcTemplate;

   @SuppressWarnings("unchecked")
   public JdbcRepository() {

      this.typeParameterClassEntity = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
      this.typeParameterClassId = (Class<I>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[1];
      this.typeParameterClassDataBase = (Class<D>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[2];

    
   }

 

    public I insert(E entity) throws Exception {
       return exec(new CommandInsertImp() , entity, jdbcTemplate, typeParameterClassDataBase , typeParameterClassId);
    }

    public void exec(String script) throws Exception {
      jdbcTemplate.execute(script);
   }

    public I update(E entity) throws Exception {
          return exec(new CommandUpdateImp() , entity, jdbcTemplate, typeParameterClassDataBase, typeParameterClassId);
     }

     
   public E updatePatch(E entity, Map<String,Object> mapValues) throws Exception {
      return execPatch(new CommandUpdatePatchImp() , entity ,mapValues ,  jdbcTemplate, typeParameterClassDataBase, typeParameterClassEntity);
   }

     public E getById(E entity) throws Exception {
        return  exec(new CommandSelectByIDImp() , entity, jdbcTemplate, typeParameterClassDataBase, typeParameterClassEntity);
     }

     public List<E> select (Select select ) throws Exception {
      return execSelect(new CommandSelectImp(), select, jdbcTemplate, typeParameterClassDataBase, typeParameterClassEntity);
   } 

   public I delete(E entity) throws Exception {
      return  exec(new CommandDeleteImp() , entity, jdbcTemplate, typeParameterClassDataBase, typeParameterClassId);
   }

   public List<Integer> insert(List<E> entityList) throws Exception {
      return  execBatch(new CommandInsertBatchImp(), entityList,  jdbcTemplate, typeParameterClassDataBase);
   }

   public List<Integer> updateBatch(List<E> entityList) throws Exception {
      return execBatch(new CommandUpdateBatchImp(), entityList,  jdbcTemplate,typeParameterClassDataBase);
   }




}
