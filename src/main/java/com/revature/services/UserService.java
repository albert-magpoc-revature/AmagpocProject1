package com.revature.services;

import com.revature.models.Department;
import com.revature.models.User;

public interface UserService {

	User getUserById(int id);

	boolean addUser(User u);

	User getDh(Department department);

	User getUserByName(String uName);
	
	User getBenCo(int notThisUser);

	void updateUser(User u, float reqAmount);

}
