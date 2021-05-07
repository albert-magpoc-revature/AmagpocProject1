package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Notification;
import com.revature.models.User;
import com.revature.services.NotificationService;
import com.revature.services.NotificationServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

public class UserController {
	private static Logger log = LogManager.getLogger(UserController.class);
	private static UserService us = new UserServiceImpl();
	private static NotificationService ns = new NotificationServiceImpl();

	public static void login(Context ctx) {
		log.trace("Attempting log in");
		if(ctx.sessionAttribute("User") != null) {
			ctx.status(204);
			return;
		}
		
		String uName = ctx.formParam("userName");
		User u = us.getUserByName(uName);
		
		if(u == null) {
			log.trace("Log in failed");
			ctx.status(401);
		}
		else {
			log.trace("Logged in");
			ctx.sessionAttribute("user", u);
			log.debug("Current User: " + u.getUserName());
			List<Notification> nList = new ArrayList<>();
			nList = ns.getNotifications(u.getUserId());
			ctx.json(nList);
		}
	}
	
	public static void logout(Context ctx) {
		User u = ctx.sessionAttribute("user");
		log.trace("Logged out of " + u.getUserId());
		ctx.req.getSession().invalidate();
	}
}
