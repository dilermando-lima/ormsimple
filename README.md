## ormsimple

### About this project

A simple way to persist and read data from database with [jdbc-template](https://spring.io/guides/gs/relational-data-access/) from a spring [spring boot](https://start.spring.io/)  project.
All core of this project are in [src/main/java/com/ormsimple](https://github.com/dilermando-lima/jdbc-template-easy/tree/master/src/main/java/com/ormsimple)

### simple example
This project turns interations into database easily like this:

```java
@Table("table_my_entity")
public class MyEntity {
    @Id("col_id") private Long id;
    @Id("col_name") private String name;
}
public class Exec{

    @Autowired private JdbcRepository repository;
    
    public void insertAndUpdate() {
        ...
        myEntity.setName("name");
        repository.insert(myEntity);

        myEntity.setName("name updated");
        repository.updateEntity(myEntity);
        ...
    }
}
```

### dependency

It's required java version 1.8x and import `spring-boot-starter-jdbc` in your *pom.xml*

```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
  </dependency>
 ```
It's not necessary to import spring-data-jpa, hibernate, jpa or other some libs and framework to cover example bellows.

### Mapping entities
In order to persist and read data we need to note our entities
Let's see our available annotarions:
  - @Table("name_table")
  - @Id("name_id")
  - @Col("name_col_to_insert")
  - @ColSelect("name_col_to_select")
  - @Fk("name_fk_from_orther_entiy")
  
This project accept only type attrs bellow:
 - LocalDate.class
 - Double.class
 - Float.class
 - String.class
 - Integer.class
 - Long.class
 - LocalDateTime.class

Let's see a real mapping nottations in entities:

```java
import com.ormsimple.jdbc.annotation.Col;
import com.ormsimple.jdbc.annotation.ColSelect;
import com.ormsimple.jdbc.annotation.Fk;
import com.ormsimple.jdbc.annotation.Id;
import com.ormsimple.jdbc.annotation.Table;

@Table("contact") // used to relate entity into database table
public class Contact {

    @ColSelect("id") // used on selects
    @Id("id") // used to identify IDs
    private Long id;

    @ColSelect("phone")
    @Col("phone") // used on cols into inserts and updates
    private String phone;

    @ColSelect("id_person")
    @Fk("id_person")
    private Person person; // person entity must have @id("")
    
    // setters and getters
}
```

### Inject 'repository' or 'dao' to use on interations
We need to import JdbcRepository.class to use commands to alter and read data.
```java
    @Autowired
	private JdbcRepository repository;

	public void somMethod() {
		repository.insert(...);
	}
```

### Insert entities
```java

// just insert without reading inserted entity
    repository.insert(new Person("name", LocalDateTime.now()));

// insert entity and read a inserted entity
	Contact contact1 = repository.insert(
		new Contact( "phone", personObjInserted),
		Contact.class,
		false // dont take related entities
						);

// insert entity read a inserted entity with related entites
	Contact contact2 = repository.insert(
		new Contact( "phone", personObjInserted),
		Contact.class,
		true // take related entities with @Fk  
		    );
						
// insert a list entity in batch
    List<Person> listPersons =  new ArrayList<Person>();
	listPersons.add(new Person("name", LocalDateTime.now()));
	listPersons.add(new Person("name", LocalDateTime.now()));
	listPersons.add(new Person("name", LocalDateTime.now()));

	repository.insertBatch(Person.class, listPersons);
```
### Alter entities
```java
// just updating without reading updated entity
	personObjInserted.setName("name updated 1");
	repository.updateEntity(personObjInserted);
	
// update entity and read a updated entity
	personObjInserted.setName("name updated 2");
	Person personUpdated = repository.updateEntity(
			personObjInserted,
			Person.class,
			false // use true to take related entities
			    );

// update patch entity
	UpdatePatch updatePatch = new UpdatePatch()
		.alter("name", "valueToUpdate");
		
	repository.updatePatch(
			1, // id entity
			updatePatch, 
			Person.class
				);

// create your customized update
	repository.update(
	    new Update("update person set name = ? where id = ? ", "name 5", 1 ));

		

```
### Delete entities
```java
// delete entity
		repository.deleteEntity(new Person(1l));
		
// delete entity */
		Long idPersonDeleted = repository.deleteEntity(new Person(2l), Long.class);
```
### Select entities
```java


```