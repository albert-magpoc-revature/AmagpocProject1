package com.revature.models;

public class User {
	private Department department;
	private String UserName;
	private String fName;
	private String lName;
	private String position;
	private int userId;
	private int mngrId;
	private float availableAmount;
	private float pendingAmount;
	private float amountAwarded;
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMngrId() {
		return mngrId;
	}
	public void setMngrId(int mngrId) {
		this.mngrId = mngrId;
	}
	public float getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(float availableAmount) {
		this.availableAmount = availableAmount;
	}
	public float getPendingAmount() {
		return pendingAmount;
	}
	public void setPendingAmount(float pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
	public float getAmountAwarded() {
		return amountAwarded;
	}
	public void setAmountAwarded(float amountAwarded) {
		this.amountAwarded = amountAwarded;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((UserName == null) ? 0 : UserName.hashCode());
		result = prime * result + Float.floatToIntBits(amountAwarded);
		result = prime * result + Float.floatToIntBits(availableAmount);
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		result = prime * result + mngrId;
		result = prime * result + Float.floatToIntBits(pendingAmount);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (UserName == null) {
			if (other.UserName != null)
				return false;
		} else if (!UserName.equals(other.UserName))
			return false;
		if (Float.floatToIntBits(amountAwarded) != Float.floatToIntBits(other.amountAwarded))
			return false;
		if (Float.floatToIntBits(availableAmount) != Float.floatToIntBits(other.availableAmount))
			return false;
		if (department != other.department)
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		if (mngrId != other.mngrId)
			return false;
		if (Float.floatToIntBits(pendingAmount) != Float.floatToIntBits(other.pendingAmount))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [department=" + department + ", UserName=" + UserName + ", fName=" + fName + ", lName=" + lName
				+ ", position=" + position + ", userId=" + userId + ", mngrId=" + mngrId + ", availableAmount="
				+ availableAmount + ", pendingAmount=" + pendingAmount + ", amountAwarded=" + amountAwarded + "]";
	}
}
