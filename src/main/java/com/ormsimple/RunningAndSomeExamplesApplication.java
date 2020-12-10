package com.ormsimple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ormsimple.entity.Contact;
import com.ormsimple.entity.ContactAndPersonExampleView;
import com.ormsimple.entity.Person;
import com.ormsimple.jdbc.exec.JdbcRepository;
import com.ormsimple.jdbc.model.Select;
import com.ormsimple.jdbc.model.SelectCustom;
import com.ormsimple.jdbc.model.Update;
import com.ormsimple.jdbc.model.UpdatePatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;




@EnableAutoConfiguration(exclude={
	
		ActiveMQAutoConfiguration.class,
		AopAutoConfiguration.class,
		ArtemisAutoConfiguration.class,
		BatchAutoConfiguration.class,
		CacheAutoConfiguration.class,
		CassandraAutoConfiguration.class,
		CassandraDataAutoConfiguration.class,
		CassandraReactiveDataAutoConfiguration.class,
		CassandraReactiveRepositoriesAutoConfiguration.class,
		CassandraRepositoriesAutoConfiguration.class,
		ClientHttpConnectorAutoConfiguration.class,
		CodecsAutoConfiguration.class,
		CouchbaseAutoConfiguration.class,
		CouchbaseDataAutoConfiguration.class,
		CouchbaseReactiveDataAutoConfiguration.class,
		CouchbaseReactiveRepositoriesAutoConfiguration.class,
		CouchbaseRepositoriesAutoConfiguration.class,
		ElasticsearchDataAutoConfiguration.class,
		ElasticsearchRepositoriesAutoConfiguration.class,
		ElasticsearchRestClientAutoConfiguration.class,
		EmbeddedLdapAutoConfiguration.class,
		EmbeddedMongoAutoConfiguration.class,
		EmbeddedWebServerFactoryCustomizerAutoConfiguration.class,
		ErrorWebFluxAutoConfiguration.class,
		FlywayAutoConfiguration.class,
		FreeMarkerAutoConfiguration.class,
		GroovyTemplateAutoConfiguration.class,
		GsonAutoConfiguration.class,
		H2ConsoleAutoConfiguration.class,
		HazelcastAutoConfiguration.class,
		HazelcastJpaDependencyAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		HttpHandlerAutoConfiguration.class,
		HypermediaAutoConfiguration.class,
		InfluxDbAutoConfiguration.class,
		IntegrationAutoConfiguration.class,
		JerseyAutoConfiguration.class,
		JmsAutoConfiguration.class,
		JmxAutoConfiguration.class,
		JndiConnectionFactoryAutoConfiguration.class,
		JndiDataSourceAutoConfiguration.class,
		JooqAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class,
		JsonbAutoConfiguration.class,
		JtaAutoConfiguration.class,
		KafkaAutoConfiguration.class,
		LdapAutoConfiguration.class,
		LdapRepositoriesAutoConfiguration.class,
		LifecycleAutoConfiguration.class,
		LiquibaseAutoConfiguration.class,
		MailSenderAutoConfiguration.class,
		MailSenderValidatorAutoConfiguration.class,
		MessageSourceAutoConfiguration.class,
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class,
		MongoReactiveAutoConfiguration.class,
		MongoReactiveDataAutoConfiguration.class,
		MongoReactiveRepositoriesAutoConfiguration.class,
		MongoRepositoriesAutoConfiguration.class,
		MultipartAutoConfiguration.class,
		MustacheAutoConfiguration.class,
		Neo4jAutoConfiguration.class,
		Neo4jDataAutoConfiguration.class,
		Neo4jReactiveDataAutoConfiguration.class,
		Neo4jReactiveRepositoriesAutoConfiguration.class,
		Neo4jRepositoriesAutoConfiguration.class,
		OAuth2ClientAutoConfiguration.class,
		OAuth2ResourceServerAutoConfiguration.class,
		PersistenceExceptionTranslationAutoConfiguration.class,
		PropertyPlaceholderAutoConfiguration.class,
		QuartzAutoConfiguration.class,
		R2dbcAutoConfiguration.class,
		R2dbcDataAutoConfiguration.class,
		R2dbcRepositoriesAutoConfiguration.class,
		R2dbcTransactionManagerAutoConfiguration.class,
		RabbitAutoConfiguration.class,
		ReactiveElasticsearchRepositoriesAutoConfiguration.class,
		ReactiveElasticsearchRestClientAutoConfiguration.class,
		ReactiveOAuth2ClientAutoConfiguration.class,
		ReactiveOAuth2ResourceServerAutoConfiguration.class,
		ReactiveSecurityAutoConfiguration.class,
		ReactiveUserDetailsServiceAutoConfiguration.class,
		ReactiveWebServerFactoryAutoConfiguration.class,
		RedisAutoConfiguration.class,
		RedisReactiveAutoConfiguration.class,
		RedisRepositoriesAutoConfiguration.class,
		RepositoryRestMvcAutoConfiguration.class,
		RestTemplateAutoConfiguration.class,
		RSocketMessagingAutoConfiguration.class,
		RSocketRequesterAutoConfiguration.class,
		RSocketSecurityAutoConfiguration.class,
		RSocketServerAutoConfiguration.class,
		RSocketStrategiesAutoConfiguration.class,
		Saml2RelyingPartyAutoConfiguration.class,
		SecurityAutoConfiguration.class,
		SecurityFilterAutoConfiguration.class,
		SendGridAutoConfiguration.class,
		SessionAutoConfiguration.class,
		SolrAutoConfiguration.class,
		SolrRepositoriesAutoConfiguration.class,
		SpringApplicationAdminJmxAutoConfiguration.class,
		SpringDataWebAutoConfiguration.class,
		TaskSchedulingAutoConfiguration.class,
		TaskExecutionAutoConfiguration.class,
		ThymeleafAutoConfiguration.class,
		TransactionAutoConfiguration.class,
		UserDetailsServiceAutoConfiguration.class,
		ValidationAutoConfiguration.class,
		WebClientAutoConfiguration.class,
		WebFluxAutoConfiguration.class,
		WebMvcAutoConfiguration.class,
		WebServicesAutoConfiguration.class,
		WebServiceTemplateAutoConfiguration.class,
		WebSocketMessagingAutoConfiguration.class,
		WebSocketReactiveAutoConfiguration.class,
		WebSocketServletAutoConfiguration.class,
		XADataSourceAutoConfiguration.class
	}) 
@SpringBootApplication
public class RunningAndSomeExamplesApplication implements CommandLineRunner {

	@Autowired
	private JdbcRepository repository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RunningAndSomeExamplesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
	/* ========= CREATING TABLES ================================== */
		String deleteTablePerson =  " drop table if exists person " ;
		String deleteTableContact =  " drop table if exists contact; " ;
		String createTablePersonStript = "create table person (  id int not null auto_increment primary key, name varchar(255) , obs varchar(255) , date_insert datetime  ) ";
		String createTableContactStript = "create table contact (  id int not null auto_increment primary key,  phone varchar(255) ,  id_person int not null   ) ";
		repository.exec( deleteTablePerson );
		repository.exec( deleteTableContact );
		repository.exec( createTablePersonStript );
		repository.exec( createTableContactStript );

			/* insert a list entity in batch */
			List<Person> listPersonsTestRowback =  new ArrayList<Person>();
			listPersonsTestRowback.add(new Person("name sdfasd asdf asdfa sdfasdf asdfasdfa sdfasdf asdfasdfasdfasdfasdfasdfasdfa sdfasdfasddfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfa sdfasdfasdfasdfasdfasdfasdfafasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf", LocalDateTime.now()));
			listPersonsTestRowback.add(new Person("name", LocalDateTime.now()));
			listPersonsTestRowback.add(new Person("name", LocalDateTime.now()));

			
			repository.insertBatch(Person.class, listPersonsTestRowback);

			return;

	} public void teste() throws Exception {

	/* ======== INSERT  ============================*/

		/* just insert without read inserted entity */
		repository.insert(
					new Person("name", LocalDateTime.now()) 
						);

		Person personObjInserted = repository.insert(
			new Person( "name", LocalDateTime.now()),
			Person.class,
			false // dont take related entities
		);


		/* insert entity read a inserted entity */
		Contact contact1 = repository.insert(
							new Contact( "phone", personObjInserted),
							Contact.class,
							false // dont take related entities
						);


		/* insert entity read a inserted entity */
		Contact contact2 = repository.insert(
							new Contact( "phone", personObjInserted),
							Contact.class,
							true // take related entities with @Fk
						);

		/* insert a list entity in batch */
		List<Person> listPersons =  new ArrayList<Person>();
		listPersons.add(new Person("name", LocalDateTime.now()));
		listPersons.add(new Person("name", LocalDateTime.now()));
		listPersons.add(new Person("name", LocalDateTime.now()));

		repository.insertBatch(Person.class, listPersons);


		/* just updating without reading updated entity */
		personObjInserted.setName("name updated 1");
		repository.updateEntity(personObjInserted);

		/* update entity and read a updated entity */
		personObjInserted.setName("name updated 2");
		Person personUpdated = repository.updateEntity(
						personObjInserted,
						Person.class,
						false // use true to take related entities
						);

		/* update patch entity */
		UpdatePatch updatePatch = new UpdatePatch()
					.alter("name", "valueToUpdate");
		
		repository.updatePatch(
						1, // id entity
						updatePatch, 
						Person.class
						);
		
		/* create your customized update */
		repository.update(new Update("update person set name = ? where id = ? ", "name 5", 1 ));


		/* delete entity */
		repository.deleteEntity(new Person(5l));

		/* delete entity */
		Long idPersonDeleted = repository.deleteEntity(new Person(5l), Long.class);
		


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

			// select one value in count example
			Long count = repository.selectOneVal(
							Long.class, 
									new Select("select count(1) from person")			
								);


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
		SelectCustom selectDinam = new SelectCustom()
										.col("id","name")
										.from("person");
						
		if( containsSomeCondition   ){ // if will need add conditions
			selectDinam.col("obs")
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



		// testing performance - 166/s
		/*System.out.print("start 1 - ");
		System.out.println( LocalDateTime.now());
		for (int i = 0; i < 10000; i++) {
			repository.insert(new Person("name" + i, LocalDateTime.now()));
		}
		System.out.print("  end 1 - ");	
		System.out.println( LocalDateTime.now());	

		List<Person> listToTestPerfm = new ArrayList<>();
		System.out.print("start 2 - ");
		System.out.println( LocalDateTime.now());	
		for (int i = 0; i < 10000; i++) {
			listToTestPerfm.add(new Person("name" + i, LocalDateTime.now()));
		}
		repository.insertBatch(Person.class , listToTestPerfm);
		System.out.print("  end 2 - ");
		System.out.println( LocalDateTime.now());	 */
		
	} 

}