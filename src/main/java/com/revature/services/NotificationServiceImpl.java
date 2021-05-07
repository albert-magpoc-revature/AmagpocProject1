package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.Driver;
import com.revature.data.NotificationDao;
import com.revature.data.NotificationDaoImpl;
import com.revature.models.Notification;

public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LogManager.getLogger(Driver.class);
	private static final NotificationDao nd = new NotificationDaoImpl();

	@Override
	public void addNotification(int mngrId, int emplId, int eventID, String string) {
		nd.addNotification(mngrId, emplId, eventID, string);
	}

	@Override
	public List<Notification> getNotifications(int userId) {
		List<Notification> nList = nd.getNotifications().stream()
				.filter(n -> n.getUid() == userId)
				.collect(Collectors.toList());
		log.trace("Returning List: " + nList);
		return nList;
	}

	@Override
	public void deleteNotification(int userId, int eid, int userId2) {
		nd.deleteNotification(userId, eid, userId2);
	}


}
