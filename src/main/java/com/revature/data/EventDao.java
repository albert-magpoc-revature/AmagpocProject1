package com.revature.data;

import java.time.LocalDate;
import java.util.List;

import com.revature.models.Event;

public interface EventDao {
	void addEvent(Event ev);
	Event getEventByID(int id);
	List<Event> getEventsByConstants(LocalDate date, String state);
	List<Event> getEvents();
}
