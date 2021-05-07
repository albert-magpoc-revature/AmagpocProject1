package com.revature.models;

public enum CoverageType {
	UNIVERSITY(0.8f), 
	SEMINARS(0.6f),
	CERTPREP(0.75f),
	CERTIFICATION(1.00f),
	TECHTRAINING(0.9f),
	OTHER(0.3f);
	
	private float coverage;

	CoverageType(float f) {
		this.coverage = f;
	}
	
	public float getValue() {
		return this.coverage;
	}
}
