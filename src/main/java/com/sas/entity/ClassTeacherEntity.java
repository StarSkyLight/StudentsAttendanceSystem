package com.sas.entity;

public class ClassTeacherEntity {
	private String id;
	private String classId;
	private String teacherId;
	
	public ClassTeacherEntity() {
		super();
	}

	public ClassTeacherEntity(String id, String classId, String teacherId) {
		super();
		this.id = id;
		this.classId = classId;
		this.teacherId = teacherId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	

}
