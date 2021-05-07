package com.revature.services;

import java.util.List;

import com.revature.models.Notification;

public interface NotificationService {

	void addNotification(int mngrId, int emplId, int eventID, String string);

	List<Notification> getNotifications(int userId);

	void deleteNotification(int userId, int eid, int userId2);


}
