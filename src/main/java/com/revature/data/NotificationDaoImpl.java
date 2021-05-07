package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.models.Notification;
import com.revature.util.CassUtil;

public class NotificationDaoImpl implements NotificationDao {
	private static final Logger log = LogManager.getLogger(NotificationDaoImpl.class);
	private CqlSession session = CassUtil.getInstance().getSession();

	@Override
	public void addNotification(int mngrId, int empId, int eventID, String string) {
		log.trace("Adding Notification");
		StringBuilder sb = new StringBuilder("Insert into project1.notifications")
				.append("(mngrId, userId, eventid, notification)")
				.append("values(?,?,?,?);");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(
				mngrId, empId, eventID, string);
		session.execute(bs);
	}

	@Override
	public List<Notification> getNotifications() {
		log.trace("Getting Notifications");
		
		List<Notification> nList = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder("select * from project1.notifications");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString()).build();
		ResultSet rs = session.execute(ss);
		
		rs.forEach(data -> {
			Notification n = new Notification();
			n.setEmpid(data.getInt("userid"));
			n.setEid(data.getInt("eventid"));
			n.setUid(data.getInt("mngrId"));	
			n.setMessage(data.getString("notification"));
			nList.add(n);
		});
		
		return nList;
	}

	@Override
	public void deleteNotification(int mngrId, int eid, int userId) {
		log.trace("deleting Notification");
		StringBuilder sb = new StringBuilder("delete from project1.notifications ")
				.append("where ")
				.append("mngrid = ? and ")
				.append("userid = ? and ")
				.append("eventid = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(mngrId,userId, eid);
		session.execute(bs);
	}

}
