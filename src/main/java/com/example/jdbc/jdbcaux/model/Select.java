package com.example.jdbc.jdbcaux.model;

import java.util.LinkedList;
import java.util.List;

public class Select {

    final public static String ASC = "asc";
    final public static String DESC = "desc";

    private boolean containsWhere;

    private String textSequence;
    private String built;

    private String errorMsg;

    private String cols;
    private String table;
    private String inners;
    private String where;
    private String orderBy;
    private String pagination;




    private List<Object> listValues;
    int numPage;
    int pageSize;

    private void init(){
        this.containsWhere = false;
        listValues = new LinkedList<Object>();
        textSequence = "s";

        errorMsg = null;

        built = "";

        cols = "";
        table = "";
        inners = "";
        where = "";
        orderBy = "";
        pagination = "";

    }

    public Select(int numPage , int pageSize) {
        init();
        this.numPage = numPage;
        this.pageSize = pageSize;
    }


    public String build(int dataBase) throws Exception {

            if( errorMsg != null )  throw new Exception(errorMsg);

            if( !textSequence.equals("scfiwo") && 
            !textSequence.equals("scfo") && 
            !textSequence.equals("scfwo") && 
            !textSequence.equals("scfio") ){
                throw new Exception(" the order of calling methods to build select has been incorrect ");
            }

            setPagination(numPage, pageSize, dataBase);

            if( cols.trim().endsWith(",") ) cols = cols.trim().substring(0, cols.trim().length() - 1);

            if( containsWhere  ) where = " where " + where;

            built+= String.format(" select %s from %s %s %s %s %s",cols,table, inners, where, orderBy, pagination );

            return built.replace("  ", " ");
           
    }


    public Select col(String col){
        
        if( col == null){  errorMsg = "Cols cannot be null on Select.col()"; return this;}

        if( !textSequence.contains("c") ) textSequence+= "c";

        if( col.trim().endsWith(",")  ) col = col.trim().substring(0, col.trim().length() - 1);

        cols+= String.format(" %s, ",col);
        return this;
    }

    public Select col(String... col){
        if( col == null ) return this;
        for (String string : col) {
            col(string);
        }
        return this;
   }



    public Select from(String table){
        if( table == null )  {  errorMsg = "Table cannot be null on Select.from()"; return this;}

        textSequence+="f";
        this.table = table;
        return this;
    }

    public Select innerJoin(String tableJoin, String colLeft, String colRight){
        
        if( tableJoin == null || colLeft == null || colRight == null ) 
        {  errorMsg = "TableJoin, ColLeft or ColRight cannot be null on Select.innerJoin()"; return this;}


        if( !textSequence.contains("i") ) textSequence+= "i";

        inners+=  String.format(" inner join %s on %s = %s ",tableJoin, colLeft,colRight);
        return this;
    }

    public Select leftJoin(String tableJoin, String colLeft, String colRight){
        
        if( tableJoin == null || colLeft == null || colRight == null ) 
        {  errorMsg = "TableJoin, ColLeft or ColRight cannot be null on Select.leftJoin()"; return this;}


        if( !textSequence.contains("i") ) textSequence+= "i";

        inners+=  String.format(" left join %s on %s = %s ",tableJoin, colLeft,colRight);
        return this;
    }


    public Select andWhere(String whereand){
        return andWhere(whereand);
    }

    public Select andWhere(String whereand ,   Object... ObjParam){

        if( whereand == null  ) 
        {  errorMsg = "whereand cannot be null on Select.andWhere()"; return this;}

        if( !textSequence.contains("w") ) textSequence+= "w";

       

        if(   !containsWhere  ){
            containsWhere = true;
            where+= " " + whereand + " ";
        }else if( !where.trim().endsWith("and") ){
            where+= " and " + whereand + " ";
        }else{
            where+= " " + whereand + " ";
        }

        if(  ObjParam != null  ){
            for (Object object : ObjParam) {
                listValues.add(object);
            }
        }
       
        return this;
    }

    public Select orWhere(String whereand){
        return orWhere(whereand);
    }
    
    public Select orWhere(String whereor ,   Object... ObjParam){

        if( whereor == null  ) 
        {  errorMsg = "whereor cannot be null on Select.orWhere()"; return this;}

        if( !textSequence.contains("w") ) textSequence+= "w";

  
    
        
        if(   !containsWhere  ){
            containsWhere = true;
            where+= " " + whereor + " ";
        }if( !where.trim().endsWith("or") ){
            where+= " or " + whereor + " ";
        }else{
            where+= " " + whereor + " ";
        }

        if(  ObjParam != null  ){
            for (Object object : ObjParam) {
                listValues.add(object);
            }
        }
        return this;
    }


    public Select orderBy(String col, String order ){

        if( col == null ||  order == null  ) 
        {  errorMsg = "col or order cannot be null on Select.orderBy()"; return this;}

    
        if( !textSequence.contains("o") ) textSequence+= "o";
        
        
        if( !orderBy.contains("order by")){
            orderBy+= " order by ";
        }else{
            orderBy+= " , ";
        }

        orderBy+= String.format(" %s %s ", col, order);
        return this;
    }


    private void setPagination(int numPage, int pageSize, int dataBase){

        if( pageSize == 0  )
        {  errorMsg = "pageSize cannot be ZERO on Select.setPagination()"; return;}

        if( dataBase == DataBase.MY_SQL ){
            pagination = String.format(" limit %d  offset %d ", pageSize, numPage * pageSize );
        }else if (  dataBase == DataBase.SQL_SERVER ){
            pagination = String.format(" offset %d  rows fetch next %d rows only ", numPage, pageSize );
        }else{
             errorMsg = "int dataBase not found " + this.getClass() ; return;
        }
     
    }

    public List<Object> getListValues() {
        return listValues;
    }





   






    
   




}
