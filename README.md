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


// select by id
	Person p = repository.selectById(
		3l, // new Person(2l), /* will work too */
		Person.class, 
		true // take related entities
					);

// select a list of entity
	Select select =  
	new Select("select * from contact");	
	
	List<Contact> listOfContact = repository.selectEntity(
			Contact.class, 
			select,
			true
		);


				

// selet with conditions
	Select selectWithWhere = 
	new Select("select id, phone, id_person from contact where phone like ? ", "%phone%");

	List<Contact> listOfContactWithWhere = 
		repository.selectEntity(
			Contact.class, 
			selectWithWhere,
			true // will bring person nested in id_person attr
		);


// select the fisrt entity 
	Contact contact = repository.selectFirstOne(
		Contact.class, 
		new Select("select * from contact where id = 2"),
		true);

// select one value
	LocalDateTime localDateTime = repository.selectOneVal(
			LocalDateTime.class, 
			new Select("select date_insert from person where id = 1")			
		);



```
### Select custom
```java


// select custom
	SelectCustom selectCustom = new SelectCustom().col(" id, name ").from("person");
	List<Person> listPersonCust = repository.select(Person.class, selectCustom);


// select custom with where
	SelectCustom selectCustomWithWhere = 
		new SelectCustom()
			.col(" id, name ")
			.from("person")
			.andWhere("id = 1")
			.andWhere(" name = ? ","nameToSearch");
	List<Person> listPersonCustWithWhere = repository.select(Person.class, selectCustomWithWhere);

// select custom with inner join
	SelectCustom selectCustomWithInner = 
		new SelectCustom()
			.col(" person.id as id_person")
			.col(" contact.id as id_contact")
			.col(" person.name as name_person")
			.col(" contact.phone as phone")
			.from("person")
			.innerJoin("contact", "contact.id", "person.id")
			.orderBy("person.name", SelectCustom.ASC);

	List<ContactAndPersonExampleView> listPersonCustWithInnerJoin = 
		repository.select(
			ContactAndPersonExampleView.class, 
			selectCustomWithInner);
								

// dinamic select
	boolean containsSomeCondition = true;
	boolean addSomePagination = true;
	boolean addSomeOrderBy = true;

	SelectCustom selectDinam = 
		new SelectCustom()
			.col("id","name")
			.from("person");
					
	if( containsSomeCondition   ){ // if will need add conditions
		selectDinam
			.col("obs")
			.andWhere(" obs = ? ","valueObsToSearch")
			.andWhere(" obs like ? and obs like ?   ","%param1%"  ,"%param2%"  );
	}

	if(  addSomeOrderBy  ){ // if will need add orderby
		selectDinam.orderBy("id", SelectCustom.ASC);
	}

	if(  addSomePagination  ){ // if will need add limits and off set

		int pageSize = 10; // take 10 rows
		int numPage = 0; // take the fist pagination
		selectDinam.setPagination(String.format(" limit %d  offset %d ", pageSize, numPage * pageSize )); // MYSQL
		//	selectCustom.setPagination(String.format(" offset %d  rows fetch next %d rows only ", numPage, pageSize )); // SQLSERVER
	}

	List<Person> listDinamicSelect = 
			repository.select(
				Person.class, 
				selectDinam);


```
