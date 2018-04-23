package com.sas.entity;

public class ClassStudentEntity {
	private String id;
	private String classId;
	private String studentId;
	
	public ClassStudentEntity() {
		super();
	}

	public ClassStudentEntity(String id, String classId, String studentId) {
		super();
		this.id = id;
		this.classId = classId;
		this.studentId = studentId;
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	

}
