package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.Driver;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.models.Department;
import com.revature.models.User;

public class UserServiceImpl implements UserService{
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	private static UserDao ud = new UserDaoImpl();

	@Override
	public User getUserById(int uid) {
		
		User u = ud.getUsers().stream()
				.filter(user -> user.getUserId() == uid)
				.collect(Collectors.toList())
				.get(0);
		return u;
	}

	@Override
	public boolean addUser(User u) {
		ud.addUser(u);
		return false;
	}

	@Override
	public User getDh(Department department) {
		log.trace("finding the departmentHead of" + department.name());
		User u = ud.getUsers().stream()
				.filter(user -> user.getDepartment() == department)
				.filter(user -> user.getPosition().equals("DepartmentHead"))
				.collect(Collectors.toList())
				.get(0);
		return u;
	}

	@Override
	public User getUserByName(String uName) {
		User user = new User();
		List<User> uList = ud.getUsers();
		user = uList.stream()
				.filter(u -> u.getUserName().equals(uName))
				.collect(Collectors.toList()).get(0);
		log.debug("User: " + user);
		return user;
	}

	@Override
	public User getBenCo(int notThisUser) {
		User u = new User();
		u = ud.getUsers().stream()
				.filter( user -> user.getPosition().equals("Benco"))
				.filter( user -> user.getUserId() != notThisUser)
				.findAny()
				.get();
		return u;
	}

	
	@Override
	public void updateUser(User u, float reqAmount) {
		float f = u.getAvailableAmount() - reqAmount;
		float l = reqAmount;
		ud.updateUserMoney(u.getUserId(), f, l, 0);
	}

}
