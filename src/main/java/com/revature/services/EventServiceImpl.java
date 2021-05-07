package com.revature.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.Driver;
import com.revature.data.EventDao;
import com.revature.data.EventDaoImpl;
import com.revature.models.Event;

public class EventServiceImpl implements EventService {
	private static final Logger log = LogManager.getLogger(EventServiceImpl.class);
	private static EventDao ed = new EventDaoImpl();

	@Override
	public Event getEventByConstants(LocalDate date, String state, String address) {
		List<Event> eList = ed.getEventsByConstants(date, state);
		if(eList.size() == 0) {
			Event e = new Event();
			return e;
		}
		Event e =  eList.stream()
				.filter(event -> event.getAddress().equals(address))
				.collect(Collectors.toList()).get(0);

		return e;
	}

	@Override
	public void addEvent(Event e) {
		e.setEventID(ed.getEvents().size()+1);
		ed.addEvent(e);
	}

	@Override
	public Event getEventById(int id) {
		log.debug("eid: " + id);
		List<Event> eList = ed.getEvents();
		log.debug("eList: " + eList.toString());
		if(eList.size() == 0) {
			Event e = new Event();
			return e;
		}
		Event e = eList.stream()
				.filter(ev -> ev.getEventID() == id)
				.collect(Collectors.toList()).get(0);
		return e;
	}

}
