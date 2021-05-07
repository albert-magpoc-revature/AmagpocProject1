package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.models.Department;
import com.revature.models.User;
import com.revature.util.CassUtil;


public class UserDaoImpl implements UserDao{
	private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
	private CqlSession session = CassUtil.getInstance().getSession();
	
	@Override
	public void addUser(User u) {
		StringBuilder sb = new StringBuilder("Insert into users")
				.append("(userId, department,position, username, firstName, lastName, mngrId"
						+ "availableAmount, pendingAmount, AmountAwarded)")
				.append("values( ?, '?', '?', '?', '?', '?', ?, '?', '?', ?, ?, ?, ?);");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss)
				.bind(u.getUserId(),u.getDepartment(),u.getPosition(), u.getUserName(), u.getfName(),u.getlName(),
						u.getMngrId(), u.getAvailableAmount(), u.getPendingAmount(), 
						u.getAmountAwarded());
		session.execute(bs);
	}
	
	@Override
	public List<User> getUsers() {
		log.trace("Getting Users from Cassandra");
		
		List<User> uList = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder("select * from project1.users");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		ResultSet  rs = session.execute(ss);
		
		rs.forEach(data ->{
			User u = new User();
			u.setUserId(data.getInt("userId"));
			u.setDepartment(Department.valueOf(data.getString("department")));
			u.setPosition(data.getString("position"));
			u.setUserName(data.getString("userName"));
			u.setfName(data.getString("firstName"));
			u.setlName(data.getString("lastName"));
			u.setMngrId(data.getInt("mngrId"));
			u.setAvailableAmount(data.getFloat("availableAmount"));
			u.setPendingAmount(data.getFloat("pendingAmount"));
			u.setAmountAwarded(data.getFloat("amountAwarded"));
			uList.add(u);
		});
		log.debug("User List: " + uList);
		
		return uList;
	}

	@Override
	public void updateUserMoney(int id, float avail, float pend, float aw) {
		StringBuilder sb = new StringBuilder("update project1.forms ")
				.append("set ")
				.append("availableAmount = ?,")
				.append("pendingAmount = ?,")
				.append("availableAmount = ?,")
				.append(" where ")
				.append("userid = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss)
				.bind(id,avail,pend,aw);
		session.execute(bs);
	}
}
