package com.revature.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.CoverageType;
import com.revature.models.Event;
import com.revature.models.Form;
import com.revature.models.User;
import com.revature.services.EventService;
import com.revature.services.EventServiceImpl;
import com.revature.services.FormService;
import com.revature.services.FormServiceImpl;
import com.revature.services.NotificationService;
import com.revature.services.NotificationServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.util.S3Util;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import software.amazon.awssdk.core.sync.RequestBody;

public class FormController {

	private static Logger log = LogManager.getLogger(FormController.class);

	private static FormService fs = new FormServiceImpl();
	private static UserService us = new UserServiceImpl();
	private static EventService es = new EventServiceImpl();
	private static NotificationService ns = new NotificationServiceImpl();

	public static void getForm(Context ctx) {
		log.trace("Getting Form");

		User user = ctx.sessionAttribute("user");

		if (user == null) {
			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		log.debug("User: " + user);
		String uName = ctx.pathParam(":uName");
		User empl = us.getUserByName(uName);
		User mngr = us.getUserById(empl.getUserId());
		User dh = us.getDh(empl.getDepartment());
		
		int uid = empl.getUserId();
		int eid = Integer.parseInt(ctx.pathParam("eid"));
		Form f = fs.getForm(uid, eid);

		if(mngr.getUserId() != user.getUserId() && !user.getUserName().equals(uName)) {
			if(dh.getUserId() == user.getUserId()) {
				if(dh.getUserId() != empl.getMngrId() && f.isDsApproval()) {
					
					log.trace("Unauthorized access");
					ctx.status(403);
					return;
				}
				else if(f.isDsApproval()) {
					log.trace("Unauthorized access");
					ctx.status(403);
					return;
				}
			}
		}
		
		if (user.getUserId() == dh.getUserId() && f.isDsApproval()) {
			log.trace("Unauthorized access");
			ctx.status(403);
			return;
		}

		ctx.json(f);

	}

	public static void addForm(Context ctx) {
		User user = ctx.sessionAttribute("user");
		log.trace("adding form");

		if (user == null) {

			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		Form f = new Form();
		float reqAmount = 0;
		f.setUserID(user.getUserId());
		f.setRequestedAmount(Float.parseFloat(ctx.formParam("amount")));
		f.setDateOfSubmission(LocalDate.now());
		log.debug("Coverage Type: " + ctx.formParam("CoverageType"));
		f.setCoverage(CoverageType.valueOf(ctx.formParam("CoverageType")));
		f.setGradingFormat(Form.GradingFormat.valueOf(ctx.formParam("gradingFormat")));
		log.debug("Coverage: " + f.getCoverage().getValue());
		log.debug("Available amount" + user.getAvailableAmount());
		log.debug("available amount: " + user.getAvailableAmount());

		if (Float.parseFloat(ctx.formParam("amount")) * f.getCoverage().getValue() > user.getAvailableAmount()) {
			reqAmount = user.getAvailableAmount();
		} else {
			reqAmount = Float.parseFloat(ctx.formParam("amount")) * f.getCoverage().getValue();
		}
		f.setRequestedAmount(reqAmount);

		Event e = new Event();
		log.debug("eventId: " + ctx.formParam("eid"));
		if (ctx.formParam("eid").isEmpty()) {
			log.debug("checking for Events ");
			if (ctx.formParam("EventName").equals(null) || ctx.formParam("date").equals(null)
					|| ctx.formParam("date").equals(null) || ctx.formParam("address").equals(null)
					|| ctx.formParam("city").equals(null) || ctx.formParam("state").equals(null)
					|| ctx.formParam("zip").equals(null) || ctx.formParam("description").equals(null)) {
				log.trace("Bad Request");
				ctx.status(400);
				return;
			}
			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyy");

			log.debug("Event Name: " + ctx.formParam("EventName"));
			e.setName(ctx.formParam("EventName"));
			e.setAddress(ctx.formParam("address"));
			e.setCity(ctx.formParam("city"));
			e.setDate(LocalDate.parse(ctx.formParam("date"), df));
			e.setState(ctx.formParam("state"));
			log.debug("date take: " + e.getDate());
			e.setDesc(ctx.formParam("description"));
			e.setZip(Integer.parseInt(ctx.formParam("zip")));
			log.trace("Created Event: " + e);
			Event search = es.getEventByConstants(e.getDate(), e.getState(), e.getAddress());
			log.debug("Searched Event " + search);
			if (search.getEventID() == 0) {
				log.debug("New Event");
				es.addEvent(e);
			}
			else {
				e = search;
			}
			f.setEventID(es.getEventByConstants(e.getDate(), e.getState(), e.getAddress()).getEventID());
		} else {
			e = es.getEventById(Integer.parseInt(ctx.formParam("eid")));
			f.setEventID(e.getEventID());
		}
		
		log.debug("adding form ");
		fs.addForm(f);
		
		log.debug("making notification");
		ns.addNotification(user.getMngrId(), user.getUserId(), f.getEventID(),
				"A form from " + user.getfName() + " " + user.getlName() + " needs approval");

	}

	public static void getForms(Context ctx) {

		User user = ctx.sessionAttribute("user");

		if (user == null) {
			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		String uName = ctx.pathParam("uName");
		if (user.getUserName().equals(uName)) {
			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		ctx.json(fs.getForms(user.getUserId()));

	}

	public static void addAttachment(Context ctx) {

		User u = ctx.sessionAttribute("user");
		if (u == null) {
			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		for (UploadedFile uf : ctx.uploadedFiles()) {
			int eName = es.getEventById(Integer.parseInt(ctx.pathParam("eid"))).getEventID();
			int uid = us.getUserByName(ctx.pathParam("uName")).getUserId();

			String key = uid + "_" + eName + "_" + uf.getFilename();
			log.debug("Key: " + key);

			S3Util.getinstance().uploadToBucket(key, 
					RequestBody.fromInputStream(uf.getContent(), uf.getSize()));
		}
	}

	public static void getAttachment(Context ctx) {
		User u = ctx.sessionAttribute("user");
		if (u == null) {
			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		String key = ctx.pathParam("aName");
		String name = new StringBuilder(key).toString();

		try {
			String s = S3Util.getinstance().getObject(name);
			ctx.result(s);
		} catch (Exception e) {
			ctx.status(500);
		}
	}

	public static void updateForm(Context ctx) {
		log.trace("Updating form");

		User user = ctx.sessionAttribute("user");

		if (user == null) {
			log.trace("Unauthorized access");
			ctx.status(401);
			return;
		}

		String empName = ctx.pathParam("uName");
		User empl = us.getUserByName(empName);
		int eid = Integer.parseInt(ctx.pathParam("eid"));
		Form f = fs.getForm(empl.getUserId(), eid);
		
		log.trace("checking if already denied");
		if(!f.getDenialComment().isEmpty()) {
			ctx.status(403);
			log.trace("Form already denied");
		}

		log.trace("Checking approval");
		if (ctx.formParam("approval").toLowerCase().equals("approve")) {

			if (user.getUserId() == empl.getMngrId()) {
				log.trace("User is DS");
				if (f.isDsApproval()) {
					log.trace("User already approved");
					ctx.status(403);
					return;
				}
				log.trace("Approving User");
				f.setDsApproval(true);
				fs.updateForm(f, "ds");
				ns.deleteNotification(user.getUserId(), eid , empl.getUserId());
				ns.addNotification(us.getDh(empl.getDepartment()).getUserId(),  empl.getUserId(), f.getEventID(),
						"A form from " + empl.getfName() + " " + empl.getlName() + " needs approval");
			}
			else if (user.getUserId() == us.getDh(empl.getDepartment()).getUserId()) {
				log.trace("User is DH");
				if(!f.isDsApproval() || us.getDh(empl.getDepartment()).getUserId() != empl.getMngrId()) {
					ctx.status(403);
					return;
				}
				ns.deleteNotification(user.getUserId(), eid , empl.getUserId());
				f.setDhApproval(true);
				fs.updateForm(f, "dh");
			}
			else if (user.getPosition().equals("Benco") && empl.getUserId() != user.getUserId()) {
				log.trace("User is BC");
				if (!f.isDhApproval()) {
					ctx.status(403);
					return;
				}
				if (!ctx.formParam("amount").isEmpty()) {
					f.setRequestedAmount(Float.parseFloat(ctx.formParam("amount")));
				}
				f.setDsApproval(true);
				fs.updateForm(f, "bc");
				us.updateUser(empl, f.getRequestedAmount());
			}
			return;
		}
		log.trace("Checking for Denial Comment");
		if (ctx.formParam("approval").toLowerCase().equals("deny")) {
			if (ctx.formParam("comment").isEmpty()) {
				ctx.status(400);
				return;
			}
			
			f.setDenialComment(ctx.formParam("comment"));

			log.trace("Denying form");
			if (user.getUserId() == empl.getMngrId()) {
				log.trace("User id DS");
				if (f.isDsApproval()) {
					ctx.status(403);
					return;
				}
				ns.deleteNotification(user.getUserId(), eid , empl.getUserId());
				f.setDsApproval(false);
				fs.updateForm(f, "ds");
			}
			if (user.getUserId() == us.getDh(empl.getDepartment()).getUserId()) {
				log.trace("User id DH");
				if (f.isDsApproval() && !f.isDhApproval()) {
					ctx.status(403);
					return;
				}
				f.setDhApproval(false);
				fs.updateForm(f, "dh");
			}
			if (user.getPosition().equals("Benco") && empl.getUserId() != user.getUserId()) {
				log.trace("User id BC");
				if (f.isDsApproval() && !f.isDhApproval()) {
					ctx.status(403);
					return;
				}
				f.setBenCoApproval(false);
				fs.updateForm(f, "bc");
			}
		}
		else {
			ctx.status(400);
		}
	}
}
