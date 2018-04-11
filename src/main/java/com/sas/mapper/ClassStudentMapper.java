package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface ClassStudentMapper {
	
	@Select("select student_id from class_student where class_id = #{classId}")
	List<String> getStudents(String classId);
	
	
	
	@Select("select class_id from class_student where student_id = #{studentId}")
	List<String> getClasses(String studentId);
	
	
	
	

}
