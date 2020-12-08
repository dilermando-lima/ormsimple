### About this project
A simple way to persist data into a database with  [jdbc-template from spring](https://spring.io/guides/gs/relational-data-access/) in a spring boot project. <br>
All core of this project are in [src/main/java/com/example/jdbc/jdbc](https://github.com/dilermando-lima/jdbc-template-easy/tree/master/src/main/java/com/example/jdbc/jdbc)


### Import dependency spring-boot-starter-jdbc

Import `spring-boot-starter-jdbc` in your *pom.xml*

```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
  </dependency>
 ```

### Mapping entities

```java
import com.example.jdbc.jdbc.annotation.Col;
import com.example.jdbc.jdbc.annotation.ColSelect;
import com.example.jdbc.jdbc.annotation.Id;
import com.example.jdbc.jdbc.annotation.Table;

@Table("person")
public class Person {

    @ColSelect("id")
    @Id("id")
    private Long id;

    @ColSelect("name")
    @Col("name")
    private String name;

    @ColSelect("obs")
    @Col("obs")
    private String obs;

    @ColSelect("date_insert")
    @Col("date_insert")
    private LocalDateTime dateInsert;
    
    // setters and getters...
   
}


import com.example.jdbc.jdbc.annotation.Col;
import com.example.jdbc.jdbc.annotation.ColSelect;
import com.example.jdbc.jdbc.annotation.Fk;
import com.example.jdbc.jdbc.annotation.Id;
import com.example.jdbc.jdbc.annotation.Table;

@Table("contact")
public class Contact {

    @ColSelect("id")
    @Id("id")
    private Long id;

    @ColSelect("phone")
    @Col("phone")
    private String phone;

    @ColSelect("id_person")
    @Fk("id_person")
    private Person person;


```

### Testing Commands


```java



	/* ======== INSERTING DATA ============================*/

		// return void
		repository.insert(new Person("name person 1", "obs 1", LocalDateTime.now()));
		
		// return inserted entity
		Person personInserted = repository.insert(
						new Person("name person 2", "obs 2", LocalDateTime.now()), 
						Person.class, 
						false);

		// return inserted entity with no related entities
		Contact contact1 = repository.insert(
							new Contact("999990001", personInserted), 
							Contact.class, 
							false); // false: with no related entities.
		// { id: 1, phone: 999990001, person: { id: 2, name: null , obs: null, date_insert: null }  } 
		

		// return inserted entity with no related entities
		Contact contact2 = repository.insert(
			new Contact("999990002", personInserted), 
			Contact.class, 
			true);  // true: will bring related entities.

		// { id: 2, phone: 999990002, person: { id: 2, name: name person 2 , obs: obs 2, date_insert: 2020-12-07T23:20:03 }  }
		

		// inserting a list of entities
		repository.insertBatch(Contact.class, 
				Arrays.asList(
					new Contact("999990003", personInserted),
					new Contact("999990004", personInserted),
					new Contact("999990005", personInserted)
				)
		);


	/* ======== UPDATE DATA ============================*/


		// updating return void
		Person personToUpdate = new Person(1l, "name updated 1", "obs updated 1", LocalDateTime.now());
		repository.updateEntity(personToUpdate);



		// updating return entity updated with related entities
		Contact contactUpdated = repository.updateEntity(
									 new Contact(1l, "phone_updated", personInserted),
									 Contact.class,
									 true);

		//{ id: 1, phone: phone_updated, person: { id: 2, name: name person 2 , obs: obs 2, date_insert: 2020-12-07T23:35:22 }  


	/* ======== CUSTOMIZED UPDATE DATA ============================*/
		// update on script udpate
		Update update = new Update(" update person set name = 'name updated 3' where id = 1 ");
		repository.update(update);

		// update on script udpate with parameters
		update = new Update(" update person set name = ? where name = ? and obs like ? ", 
								"nameToUpdate", 		// param ? 1
								"nameToSearch", 		// param ? 2
								"%containsThisObs%" ); 	// param ? 3
		repository.update(update);


		// TODO: CREATE UPDATE PATCH

		/* ============ SELECT =======================*/
		
		// select by id
		Person p = repository.selectById(
								1l, // new Person(1l), // will work too
								Person.class, 
								true);


		// select a list of entity
		Select select =  new Select("select * from person");	
		List<Contact> listOfContact = repository.selectEntity(
											Contact.class, 
											false, 
											select);
		


		// selet with conditions
		Select selectWithWhere = new Select("select id, phone, id_person from contact where phone like ? ", "%phoneToSearch%");
		List<Contact> listOfContactWithWhere = repository.selectEntity(
											Contact.class, 
											true, // will bring person nested in id_person attr
											selectWithWhere);
		

		// selet returning one value. That's good for counts and specific values
		Select selectOneValue = new Select("select name from person where id = ?",1);
		String nameFromPersonId1 = repository.selectOneVal(String.class, selectOneValue);
		


		// selet only the fist one
		Select selectGetFirstOneEntity = new Select("select * from contact where phone like ? order by id desc", "%phoneToSearch%");
		Contact contact = repository.selectFirstOne(Contact.class, selectGetFirstOneEntity);

		


		/* ============ CUSTOMIZED SELECT  =======================*/

		boolean containsSomeCondition = true;
		boolean addSomePagination = true;
		boolean addSomeOrderBy = true;

		SelectCustom selectCustom = new SelectCustom()
										.col("id","name")
										.from("person");

		if( containsSomeCondition   ){ // if will need add conditions
			selectCustom.col("obs")
						.andWhere(" obs = ? ","valueObsToSearch")
						.andWhere(" obs like ? and obs like ?   ","%param1%"  ,"%param2%"  );
		}

		if(  addSomeOrderBy  ){ // if will need add orderby
			selectCustom.orderBy("id", SelectCustom.ASC)
						.orderBy("name", SelectCustom.DESC);
		}

		if(  addSomePagination  ){ // if will need add limits and off set

			int pageSize = 10; // take 10 rows
			int numPage = 0; // take the fist pagination
			selectCustom.setPagination(String.format(" limit %d  offset %d ", pageSize, numPage * pageSize )); // MYSQL
		//	selectCustom.setPagination(String.format(" offset %d  rows fetch next %d rows only ", numPage, pageSize )); // SQLSERVER

		}


		List<Person> listPerson =  repository.select(Person.class, selectCustom);



```






