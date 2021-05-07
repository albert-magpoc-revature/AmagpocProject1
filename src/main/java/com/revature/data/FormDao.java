package com.revature.data;

import java.util.List;

import com.revature.models.Form;

public interface FormDao {
	void addForm(Form f);
	List<Form> getForms();
	void updateBCApproval(Form f);
	void updateDHApproval(Form f);
	void updateDSApproval(Form f);
}
