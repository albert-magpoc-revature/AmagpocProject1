package com.revature.services;

import java.time.LocalDate;

import com.revature.models.Event;

public interface EventService {

	Event getEventByConstants(LocalDate date, String state, String address);

	void addEvent(Event e);

	Event getEventById(int id);

}
