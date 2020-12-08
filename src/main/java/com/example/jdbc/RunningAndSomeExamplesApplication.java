package com.example.jdbc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.jdbc.entity.Contact;
import com.example.jdbc.entity.Person;
import com.example.jdbc.jdbc.exec.JdbcRepository;
import com.example.jdbc.jdbc.model.Select;
import com.example.jdbc.jdbc.model.SelectCustom;
import com.example.jdbc.jdbc.model.Update;

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
							false);
		// { id: 1, phone: 999990001, person: { id: 2, name: null , obs: null, date_insert: null }  } 
		System.out.println(contact1);


		// return inserted entity with no related entities
		Contact contact2 = repository.insert(
			new Contact("999990002", personInserted), 
			Contact.class, 
			true);

		// { id: 2, phone: 999990002, person: { id: 2, name: name person 2 , obs: obs 2, date_insert: 2020-12-07T23:20:03 }  }
		System.out.println(contact2);


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

		System.out.println(contactUpdated);
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
		System.out.println(listOfContact);


		// selet with conditions
		Select selectWithWhere = new Select("select id, phone, id_person from contact where phone like ? ", "%phoneToSearch%");
		List<Contact> listOfContactWithWhere = repository.selectEntity(
											Contact.class, 
											true, // will bring person nested in id_person attr
											selectWithWhere);
		System.out.println(listOfContactWithWhere);

		
		// selet returning one value. That's good for counts and specific values
		Select selectOneValue = new Select("select name from person where id = ?",1);
		String nameFromPersonId1 = repository.selectOneVal(String.class, selectOneValue);
		System.out.println(nameFromPersonId1);


		// selet only the fist one
		Select selectGetFirstOneEntity = new Select("select * from contact where phone like ? order by id desc", "%phoneToSearch%");
		Contact contact = repository.selectFirstOne(Contact.class, selectGetFirstOneEntity);

		System.out.println(contact);


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

		System.out.println(listPerson);


		
	} 

}