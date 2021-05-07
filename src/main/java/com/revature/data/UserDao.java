package com.revature.data;

import java.util.List;

import com.revature.models.Department;
import com.revature.models.User;

public interface UserDao {
	void addUser(User u);
	List<User> getUsers();
	void updateUserMoney(int id, float avail, float pen, float aw);
}
