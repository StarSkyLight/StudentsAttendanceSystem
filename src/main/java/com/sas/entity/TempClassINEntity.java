package com.sas.entity;

import java.sql.Timestamp;

public class TempClassINEntity {
	
	private String classId;
	private String inviteNum;
	private Timestamp timeStamp;
	
	public TempClassINEntity() {
		super();
	}

	public TempClassINEntity(String classId, String inviteNum, Timestamp timeStamp) {
		super();
		this.classId = classId;
		this.inviteNum = inviteNum;
		this.timeStamp = timeStamp;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(String inviteNum) {
		this.inviteNum = inviteNum;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
