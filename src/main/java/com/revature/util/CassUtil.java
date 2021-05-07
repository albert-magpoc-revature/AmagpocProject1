package com.revature.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

public class CassUtil {
	private static final Logger log = LogManager.getLogger(CassUtil.class);
	private static CassUtil instance = null;
	private CqlSession session = null;
	
	public static synchronized CassUtil getInstance() {
		if(instance == null) {
			instance = new CassUtil();
		}
		
		return instance;
	}
	
	private CassUtil() {
		log.trace("Connecting to Cassandra");
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		try {
			this.session = CqlSession.builder()
					.withConfigLoader(loader)
					.withKeyspace("project1")
					.build();
		}
		catch(Exception e) {
			for(StackTraceElement s: e.getStackTrace()) {
				log.warn(s);
			}
		}
	}
	
	public CqlSession getSession() {
		return session;
	}
}
