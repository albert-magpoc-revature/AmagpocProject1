package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.data.FormDao;
import com.revature.data.FormDaoImpl;
import com.revature.models.Form;
import com.revature.models.Form.GradingFormat;

public class FormServiceImpl implements FormService {
	private static FormDao fd= new FormDaoImpl();
	private static Logger log = LogManager.getLogger(FormServiceImpl.class);
	
	@Override
	public Form getForm(int uid, int eid) {
		log.debug("getting form with eid " + eid + " and uid " + uid);
		List<Form> fList = fd.getForms();
		
		Form f = fList.stream().
				filter(form -> form.getUserID() == uid)
				.filter(form -> form.getEventID() == eid)
				.collect(Collectors.toList())
				.get(0);
		
		return f;
	}

	@Override
	public List<Form> getForms(int uid) {
		List<Form> fList = fd.getForms().stream()
				.filter(f -> f.getUserID() == 1)
				.collect(Collectors.toList());
		return fList;
	}

	@Override
	public void addForm(Form f) {
		log.debug("Form to Upload: " + f);
		f.setStatus(Form.Status.NORMAL);
		log.debug("Form that changed: " + f);
		fd.addForm(f);
	}

	@Override
	public void updateForm(Form f, String string) {
		switch (string) {
		case "dh":
			fd.updateDHApproval(f);
			break;
		case "ds":
			fd.updateDSApproval(f);
			break;
		case "bc":
			fd.updateBCApproval(f);
			break;
		}

	}

}
