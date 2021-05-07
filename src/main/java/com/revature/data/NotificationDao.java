package com.revature.data;

import java.util.List;

import com.revature.models.Notification;

public interface NotificationDao {


	void addNotification(int mngrId, int empId, int eventID, String string);

	List<Notification> getNotifications();

	void deleteNotification(int userId, int eid, int userId2);

}
