### About this project
A simple way to persist data into a database with  [jdbc-template from spring](https://spring.io/guides/gs/relational-data-access/) in a spring boot project. <br>
All core of this project are in [src/main/java/com/example/jdbc/jdbcaux](https://github.com/dilermando-lima/jdbc-template-easy/tree/master/src/main/java/com/example/jdbc/jdbcaux)


### Import dependency spring-boot-starter-jdbc

Import `spring-boot-starter-jdbc` in your *pom.xml*

```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
  </dependency>
 ```


### Create your entity to persist
All entity needs to have the **3 annotations** bellow and have at least a **default constructor**:

* ###### *@JdbcTable("name_table_on_database")*
* ###### *@JdbcIdentity("name_column_id_on_database")*
* ###### *@JdbcColumn("name_any_column_to_insert_update")*
* ###### *@JdbcColumnSelect("name_any_column_to_read")*

*All atributes without these annotations will be ignored on persisting*


```java
@JdbcTable("my_entity")
public class MyEntity {

    public MyEntity(){}
    
    public MyEntity(String name, String obs) {
        this.name = name;
        this.obs = obs;
    }
    
    @JdbcColumnSelect("id")
    @JdbcIdentity("id")
    private String id;
    
    @JdbcColumnSelect("name")
    @JdbcColumn("name")
    private String name;
    
    @JdbcColumnSelect("obs")
    @JdbcColumn("obs")
    private String obs;
    
    // setters and getters...
   
}

```

### Create your repository class to persist
We need to extend repostitories from *`com.example.jdbc.jdbcaux.repository.JdbcRepository`*:<br> 
* *extends JdbcRepository< `YOUR_CLASS_ENTITY` ,`TYPE_OF_ATTR_ID` , `CLASS_DATA_BASE` >*


```java

  import com.example.jdbc.jdbcaux.model.DataBaseMySql;
  import com.example.jdbc.jdbcaux.repository.JdbcRepository;

  import org.springframework.stereotype.Component;

  @Component
  public class MyEntitynRepository extends  JdbcRepository<MyEntity,Long, DataBaseMySql>  {
  }

```

## That is it! :)
Let's see how this work...

#### Inserting a entity

```java

  repository.insert(new MyEntity("some name","some comments"));

```

#### Updating a entity

```java

  myEntity.setId(2);
  repository.update(myEntity);

```

#### Updating patch a entity

```java

  HashMap<String,Object> mapAttrToUpdate =  new HashMap<>();
  mapAttrToUpdate.put("name", "name to update");
  mapAttrToUpdate.put("other_attr_to_update", "value to update");

  repository.updatePatch(new MyEntity(), mapAttrToUpdate);

```



#### Getting by id

```java

  myEntity.setId(2);
  MyEntity = repository.getById(myEntity);

```

#### Simple select
In order to bring data on select your attributes need to have *@JdbcColumnSelect("name_in_database")*

```java

  int numPage = 0; /* will bring the first page */
  int numPageSize = 1000;  /* will bring 1000 rows at the first page */
  
	Select select = new Select(numPage, numPageSize) 
                      .col("name")
                      .col("obs")
                      .from("my_entity")
                      .orderBy("id", Select.ASC);

  List<MyEntity> listMyEntity = repository.select(select);

```



#### Select with inners and where

```java

  Select select = new Select(0, 1000)
                    .col("id","name","obs")
                    .from("my_entity")
                    .innerJoin("other_entity", "id", "id")
                    .andWhere(" name = ? ","some name filter")
                    .orderBy("id", Select.ASC);
                        
  List<MyEntity> listMyEntity = repository.select(select);

```

#### More about select

```java

  Select select = new Select(0, 1000)
                    .col("id","name","obs")
                    .from("my_entity")
                    .orderBy("id", Select.ASC);


  if(  addInfoFromOtherEntity  ){
        select.col("other_entity.name")
              .innerJoin("other_entity", "id", "id");
  }


  if(  addFilterName  ){
        select.andWhere(" name = ? ", valueToFilterName);
  }

  List<MyEntity> listMyEntity = repository.select(select);

```






