package com.revature;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.put;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.controllers.FormController;
import com.revature.controllers.UserController;
import com.revature.util.CassUtil;
import com.revature.util.S3Util;

import io.javalin.Javalin;

public class Driver {
	
	private static final Logger log = LogManager.getLogger(Driver.class);
	
	public static void main(String[] args) {
		log.trace("Project 1  started");
		//createKS();
		//log.trace("resarting User table");
		//restartUserTable();
//		putUserData();
//		log.trace("resarting Form table");
//		restartFormTable();
//		log.trace("restarting Notification table");
//		restartNotificationTable();
//		log.trace("restarting Event table");
//		restartEventTable();
		log.trace("connecting to Javalin");
		Javalin app = Javalin.create().start(8080);
//		
//		S3Util.getinstance();
		
		app.routes(() -> {
			path("/login", () ->{
				post(UserController::login);
			});
			path("/forms", ()->{
				post(FormController::addForm);
				path("/:uName", () ->{
					get(FormController::getForms);
					path("/:eid", () ->{
						get(FormController::getForm);
						put(FormController::updateForm);
						path("/attachments", ()->{
							post(FormController::addAttachment);
							path("/:aName", () ->{
								get(FormController::getAttachment);	
							});
						});
					});
				});
			});
			path("/logout", () ->{
				delete(UserController::logout);
			});
		});
	}
	
	private static void createKS() {
		StringBuilder sb = new StringBuilder("Create keyspace if not exists ")
				.append("Project1 with replication = {")
                .append("'class':'SimpleStrategy','replication_factor':1};");
        DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
        CqlSession.builder().withConfigLoader(loader).build().execute(sb.toString());
	}
	
	private static void restartUserTable() {
		log.trace("checking User table");
		StringBuilder sb = new StringBuilder("Drop table if exists ").append("Users;");
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.trace("Creating User table");
		CassUtil.getInstance().getSession().execute(sb.toString());
		sb = new StringBuilder("Create table if not exists ").append("Users (")
				.append("userId int,")
				.append("userName text,")
				.append("department text,")
				.append("position text,")
				.append("firstName text,")
				.append("lastName text,")
				.append("mngrId int,")
				.append("availableAmount float,")
				.append("pendingAmount float,")
				.append("amountAwarded float,")
				.append("PRIMARY KEY(")
				.append("userId, userName")
				.append(")")
				.append(");");
		CassUtil.getInstance().getSession().execute(sb.toString());
	}

	private static void putUserData() {
		log.trace("populating User table");
		StringBuilder sb = new StringBuilder("Insert into users")
				.append("(userId, department, position, ")
				.append("userName, firstName, lastName, mngrId,")
				.append("availableAmount, pendingAmount, AmountAwarded)")
				.append("values( ")
				.append("1, 'IT', 'employee',")
				.append("'fJones', 'Freddy', 'Jones', 2, 1000, 0, 0")
				.append(");");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		CassUtil.getInstance().getSession().execute(ss);
		log.trace("populating User table");
		sb = new StringBuilder("Insert into users")
				.append("(userId, department, position, ")
				.append("userName, firstName, lastName, ")
				.append("mngrId, availableAmount, pendingAmount, AmountAwarded)")
				.append("values( 2, 'IT', 'DepartmentHead', 'dBlake','Daphne', 'Blake', 3, 1000, 0, 0")
				.append(");");
		ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		CassUtil.getInstance().getSession().execute(ss);
		log.trace("populating User table");
		sb = new StringBuilder("Insert into users")
				.append("(userId, department, position, ")
				.append("userName, firstName, lastName, mngrId,")
				.append("availableAmount, pendingAmount, AmountAwarded)")
				.append("values( ")
				.append("3, 'HUMAN_RESOURCES', 'Benco', ")
				.append("'nRogers','Norville', 'Rogers', 4,")
				.append(" 1000, 0, 0")
				.append(");");
		ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		CassUtil.getInstance().getSession().execute(ss);
		log.trace("populating User table");
		sb = new StringBuilder("Insert into users")
				.append("(userId, department, position, ")
				.append("username, firstName, lastName, mngrId,")
				.append("availableAmount, pendingAmount, AmountAwarded)")
				.append("values( ")
				.append("4, 'HUMAN_RESOURCES', 'DepartmentHead', ")
				.append("'vDinkley','Velma', 'Dinkley', 4, ")
				.append("1000, 0, 0")
				.append(");");
		ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		CassUtil.getInstance().getSession().execute(ss);
		log.trace("populating User table");
		sb = new StringBuilder("Insert into users")
				.append("(userId, department, position, ")
				.append("username, firstName, lastName, mngrId,")
				.append("availableAmount, pendingAmount, AmountAwarded)")
				.append("values( ")
				.append("5, 'IT', 'employee',")
				.append("'aMagpoc', 'Albert', 'Magpoc',")
				.append("1, 1000, 0, 0")
				.append(");");
		ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		CassUtil.getInstance().getSession().execute(ss);
		log.trace("populating User table");
		sb = new StringBuilder("Insert into users")
				.append("(userId, department, position, ")
				.append("userName, firstName, lastName, mngrId,")
				.append("availableAmount, pendingAmount, AmountAwarded)")
				.append("values( ")
				.append("6, 'HUMAN_RESOURCES', 'Benco', ")
				.append("'sDoo	','Scooby', 'Doo', 4,")
				.append(" 1000, 0, 0")
				.append(");");
		ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		CassUtil.getInstance().getSession().execute(ss);
	}

	private static void restartFormTable() {
		log.trace("checking Form table");
		StringBuilder sb = new StringBuilder("Drop table if exists ")
				.append("forms;");
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.trace("Creating Form table");
		CassUtil.getInstance().getSession().execute(sb.toString());
		sb = new StringBuilder("Create table if not exists ").append("project1.forms (")
				.append("coverageType text,")
				.append(",userId int")
				.append(",dateOfSubmission date")
				.append(",eventID int")
				.append(",gradingformat text")
				.append(",requestedAmount float,")
				.append(",dsApproval boolean,")
				.append(",dhApproval boolean,")
				.append(",benCoApproval boolean,")
				.append(",denialComment text,")
				.append(",status text,")
				.append("PRIMARY KEY(")
				.append("coveragetype, userId, eventID")
				.append(")")
				.append(");");
		CassUtil.getInstance().getSession().execute(sb.toString());
	}
	
	private static void restartNotificationTable() {
		log.trace("checking Notification table");
		StringBuilder sb = new StringBuilder("Drop table if exists ")
				.append("notifications;");
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CassUtil.getInstance().getSession().execute(sb.toString());
		sb = new StringBuilder("Create table if not exists ")
				.append("project1.notifications (")
				.append("userId int")
				.append(",mngrId int")
				.append(",eventId int")
				.append(",notification text,")
				.append("PRIMARY KEY(")
				.append("mngrId, userId, eventId")
				.append(")")
				.append(");");
		CassUtil.getInstance().getSession().execute(sb.toString());
	}
	
	private static void restartEventTable() {
		log.trace("checking events table");
		StringBuilder sb = new StringBuilder("Drop table if exists ")
				.append("events;");
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CassUtil.getInstance().getSession().execute(sb.toString());
		sb = new StringBuilder("Create table if not exists ")
				.append("project1.events (")
				.append("eventId int")
				.append(",name text")
				.append(",status text")
				.append(",dateOfEvent date")
				.append(",address text")
				.append(",city text")
				.append(",state text")
				.append(",zip int")
				.append(",description text")
				.append(",PRIMARY KEY(")
				.append("state, dateOfEvent, eventId")
				.append(")")
				.append(");");
		CassUtil.getInstance().getSession().execute(sb.toString());
	}
}
