package com.sas.entity;

import java.sql.Timestamp;

public class CheckEntity {
	
	private String checkId;
	private String classId;
	private String teacherId;
	private int checkKind;
	private boolean checkIsOver;
	private Timestamp checkTime;
	
	public CheckEntity() {
		super();
	}

	

	public CheckEntity(String checkId, String classId, String teacherId, int checkKind, boolean checkIsOver,
			Timestamp checkTime) {
		super();
		this.checkId = checkId;
		this.classId = classId;
		this.teacherId = teacherId;
		this.checkKind = checkKind;
		this.checkIsOver = checkIsOver;
		this.checkTime = checkTime;
	}



	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getCheckKind() {
		return checkKind;
	}

	public void setCheckKind(int checkKind) {
		this.checkKind = checkKind;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}



	public boolean isCheckIsOver() {
		return checkIsOver;
	}



	public void setCheckIsOver(boolean checkIsOver) {
		this.checkIsOver = checkIsOver;
	}
	
	
	

}
