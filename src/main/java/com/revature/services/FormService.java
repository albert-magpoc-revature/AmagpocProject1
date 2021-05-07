package com.revature.services;

import java.util.List;

import com.revature.models.Form;

public interface FormService {

	Form getForm(int uid, int eid);

	List<Form> getForms(int uid);

	void addForm(Form f);

	void updateForm(Form f, String string);

}
