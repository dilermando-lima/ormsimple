package com.example.jdbc.jdbcaux.model;

import java.util.LinkedList;
import java.util.List;

public class Select {

    final public static String ASC = "asc";
    final public static String DESC = "desc";

    private boolean containsWhere;

    private String textSequence;
    private String selectBuilt;
    private List<Object> listValues;
    int numPage;
    int pageSize;

    private void init(){
        this.containsWhere = false;
        listValues = new LinkedList<Object>();
        textSequence = "s";
        selectBuilt = " select ";
    }

    public Select(int numPage , int pageSize) {
        init();
        this.numPage = numPage;
        this.pageSize = pageSize;
    }

    public Select() {
        init();
        this.numPage = 0;
        this.pageSize = 15;
    }


    public String build(int dataBase) throws Exception {

            if( !textSequence.equals("scfiwo") && 
            !textSequence.equals("scfo") && 
            !textSequence.equals("scfwo") && 
            !textSequence.equals("scfio") ){
                throw new Exception(" the order of calling methods to build select has been incorrect ");
            }

            return ( selectBuilt + pagination(this.numPage, this.pageSize, dataBase) ).replace("  ", " ");
           
    }


    public Select col(String col){

         if( !textSequence.contains("c") ) textSequence+= "c";

         selectBuilt+= String.format(" %s,",col);
         return this;
    }

    public Select from(String table){
        
        textSequence+="f";

        if( selectBuilt.endsWith(",") ) selectBuilt = selectBuilt.substring(0, selectBuilt.length() - 1);
        selectBuilt+= String.format(" from %s ",table);
        return this;
    }

    public Select innerJoin(String tableJoin, String colLeft, String colRight){
       
        if( !textSequence.contains("i") ) textSequence+= "i";

        selectBuilt+=  String.format(" inner join %s on %s = %s ",tableJoin, colLeft,colRight);
        return this;
    }

    public Select leftJoin(String tableJoin, String colLeft, String colRight){
        if( !textSequence.contains("i") ) textSequence+= "i";

        selectBuilt+=  String.format(" left join %s on %s = %s ",tableJoin, colLeft,colRight);
        return this;
    }


    public Select andWhere(String whereand ,   Object... ObjParam){

        if( !textSequence.contains("w") ) textSequence+= "w";


        if( !containsWhere ) {
              selectBuilt+= " where";
              containsWhere = true;
        }else if( !selectBuilt.endsWith(" and") ){
            selectBuilt+= " and";
        }

        selectBuilt+= whereand;

        for (Object object : ObjParam) {
            listValues.add(object);
        }
        return this;
    }

    public Select orWhere(String whereor ,   Object... ObjParam){
        if( !textSequence.contains("w") ) textSequence+= "w";

        if( !containsWhere ) {
            selectBuilt+= " where";
            containsWhere = true;
        }else if( !selectBuilt.endsWith(" or") ){
            selectBuilt+= " or";
        }

        selectBuilt+= whereor;


        for (Object object : ObjParam) {
            listValues.add(object);
        }
        return this;
    }

    public Select orderBy(String col, String order ){

       

        if( !textSequence.contains("o") ) textSequence+= "o";
        
        
        if( !selectBuilt.contains(" order by")){
            selectBuilt+= " order by";
        }else{
            selectBuilt+= ", ";
        }

        selectBuilt+= String.format(" %s %s ", col, order);
        return this;
    }


    public String pagination(int numPage, int pageSize, int dataBase){
        if( dataBase == DataBase.MY_SQL ){
            return String.format(" limit %d  offset %d ", pageSize, numPage * pageSize );
        }else if (  dataBase == DataBase.SQL_SERVER ){
            return String.format(" offset %d  rows fetch next %d rows only ", numPage, pageSize );
        }else{
            return "";
        }
    }

    public List<Object> getListValues() {
        return listValues;
    }





   






    
   




}
