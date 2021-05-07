package com.revature.models;

import java.time.LocalDate;

public class Form {
	private int UserID;
	private CoverageType coverage;
	private LocalDate dateOfSubmission;
	private String denialComment;
	private GradingFormat gradingFormat;
	private boolean grade;
	private int eventID;
	private float requestedAmount;
	private boolean benCoApproval;
	private boolean dsApproval;
	private boolean dhApproval;
	private Status status;
	
	public static enum GradingFormat{
		PRESENTATION, PERCENTAGE
	}
	public static enum Status{
		NORMAL, URGENT
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public CoverageType getCoverage() {
		return coverage;
	}
	public void setCoverage(CoverageType coverage) {
		this.coverage = coverage;
	}
	public LocalDate getDateOfSubmission() {
		return dateOfSubmission;
	}
	public void setDateOfSubmission(LocalDate dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}
	public String getDenialComment() {
		return denialComment;
	}
	public void setDenialComment(String denialComment) {
		this.denialComment = denialComment;
	}
	public GradingFormat getGradingFormat() {
		return gradingFormat;
	}
	public void setGradingFormat(GradingFormat gradingFormat) {
		this.gradingFormat = gradingFormat;
	}
	public boolean isGrade() {
		return grade;
	}
	public void setGrade(boolean grade) {
		this.grade = grade;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public float getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(float requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	public boolean isBenCoApproval() {
		return benCoApproval;
	}
	public void setBenCoApproval(boolean benCoApproval) {
		this.benCoApproval = benCoApproval;
	}
	public boolean isDsApproval() {
		return dsApproval;
	}
	public void setDsApproval(boolean dsApproval) {
		this.dsApproval = dsApproval;
	}
	public boolean isDhApproval() {
		return dhApproval;
	}
	public void setDhApproval(boolean dhApproval) {
		this.dhApproval = dhApproval;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + UserID;
		result = prime * result + (benCoApproval ? 1231 : 1237);
		result = prime * result + ((coverage == null) ? 0 : coverage.hashCode());
		result = prime * result + ((dateOfSubmission == null) ? 0 : dateOfSubmission.hashCode());
		result = prime * result + ((denialComment == null) ? 0 : denialComment.hashCode());
		result = prime * result + (dhApproval ? 1231 : 1237);
		result = prime * result + (dsApproval ? 1231 : 1237);
		result = prime * result + eventID;
		result = prime * result + (grade ? 1231 : 1237);
		result = prime * result + ((gradingFormat == null) ? 0 : gradingFormat.hashCode());
		result = prime * result + Float.floatToIntBits(requestedAmount);
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Form other = (Form) obj;
		if (UserID != other.UserID)
			return false;
		if (benCoApproval != other.benCoApproval)
			return false;
		if (coverage != other.coverage)
			return false;
		if (dateOfSubmission == null) {
			if (other.dateOfSubmission != null)
				return false;
		} else if (!dateOfSubmission.equals(other.dateOfSubmission))
			return false;
		if (denialComment == null) {
			if (other.denialComment != null)
				return false;
		} else if (!denialComment.equals(other.denialComment))
			return false;
		if (dhApproval != other.dhApproval)
			return false;
		if (dsApproval != other.dsApproval)
			return false;
		if (eventID != other.eventID)
			return false;
		if (grade != other.grade)
			return false;
		if (gradingFormat != other.gradingFormat)
			return false;
		if (Float.floatToIntBits(requestedAmount) != Float.floatToIntBits(other.requestedAmount))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Form [UserID=" + UserID + ", coverage=" + coverage + ", dateOfSubmission=" + dateOfSubmission
				+ ", denialComment=" + denialComment + ", gradingFormat=" + gradingFormat + ", grade=" + grade
				+ ", eventID=" + eventID + ", requestedAmount=" + requestedAmount + ", benCoApproval=" + benCoApproval
				+ ", dsApproval=" + dsApproval + ", dhApproval=" + dhApproval + ", status=" + status + "]";
	}
	
	
}
