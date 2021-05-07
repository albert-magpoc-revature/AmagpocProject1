package com.revature.data;

import java.time.LocalDate;
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
import com.revature.models.Event;
import com.revature.util.CassUtil;

public class EventDaoImpl implements EventDao {
	private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
	private CqlSession session = CassUtil.getInstance().getSession();

	@Override
	public void addEvent(Event ev) {
		log.trace("Adding Event: " + ev);
		StringBuilder sb = new StringBuilder("Insert into project1.events")
				.append("(eventId, name, dateOfEvent, address, city, state, zip, description)")
				.append("values( ?, ?, ?, ?, ?, ?, ? ,?);");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(
				ev.getEventID(), ev.getName(), ev.getDate(), ev.getAddress(),
				ev.getCity(), ev.getState(), ev.getZip(), ev.getDesc());
		session.execute(bs);
	}

	@Override
	public Event getEventByID(int id) {
		Event e = new Event();

		StringBuilder sb = new StringBuilder("select * from project1.events where eventid = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		ResultSet rs = session.execute(ss);
		Row data = rs.one();

		e.setAddress(data.getString("address"));
		e.setEventID(data.getInt("eventId"));
		e.setCity(data.getString("city"));
		e.setDate(data.getLocalDate("dateOfEvent"));
		e.setDesc(data.getString("description"));
		e.setName(data.getString("name"));
		e.setState(data.getString("state"));
		e.setZip(data.getInt("zip"));
		return e;
	}

	@Override
	public  List<Event> getEventsByConstants(LocalDate date, String state) {
		List<Event> eList = new ArrayList<>();

		StringBuilder sb = new StringBuilder("select * from project1.events where state = ? and dateOfEvent = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		log.debug("State: " + state + "date" + date);
		BoundStatement bs= session.prepare(ss).bind(state, date);
		ResultSet rs = session.execute(bs);

		rs.forEach(data -> {
			Event e = new Event();
			e.setAddress(data.getString("address"));
			e.setEventID(data.getInt("eventId"));
			e.setCity(data.getString("city"));
			e.setDate(data.getLocalDate("dateOfEvent"));
			e.setDesc(data.getString("description"));
			e.setName(data.getString("name"));
			e.setState(data.getString("state"));
			e.setZip(data.getInt("zip"));
			eList.add(e);
		});
		
		return eList;
	}

	@Override
	public List<Event> getEvents() {
		List<Event> eList = new ArrayList<>();

		StringBuilder sb = new StringBuilder("select * from project1.events");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		ResultSet rs = session.execute(ss);

		rs.forEach(data -> {
			Event e = new Event();
			e.setAddress(data.getString("address"));
			e.setEventID(data.getInt("eventId"));
			e.setCity(data.getString("city"));
			e.setDate(data.getLocalDate("dateOfEvent"));
			e.setDesc(data.getString("description"));
			e.setName(data.getString("name"));
			e.setState(data.getString("state"));
			e.setZip(data.getInt("zip"));
			eList.add(e);
		});
		
		return eList;
	}

}