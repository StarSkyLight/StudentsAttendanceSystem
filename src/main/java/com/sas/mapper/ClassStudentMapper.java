package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.ClassStudentEntity;

public interface ClassStudentMapper {
	
	@Select("select student_id from class_student where class_id = #{classId} "
			+ "order by time_stamp desc")
	List<String> getStudents(String classId);
	
	@Select("select * from class_student where class_id = #{classId} "
			+ "order by time_stamp desc")
	List<ClassStudentEntity> getAllByClassId(String classId);
	
	@Select("select class_id from class_student where student_id = #{studentId} "
			+ "order by time_stamp desc")
	List<String> getClasses(String studentId);
	
	@Select("select * from class_student where class_id = #{classId} and "
			+ "student_id = #{studentId} "
			+ "order by time_stamp desc")
	ClassStudentEntity getclassByclassIdStudentId(@Param("classId")String classId,
    		@Param("studentId")String studentId);
	
	@Insert("insert into class_student(id,class_id,student_id) "
			+ "values(#{id},#{classId},#{studentId})")  
    int insertClassStudent(@Param("id")String id,@Param("classId")String classId,
    		@Param("studentId")String studentId); 
	
	
	@Update("update class_student set class_id=#{classId},student_id=#{studentId}"
			+ " where id=#{id}")  
	int updateClassStudent(@Param("id")String id,@Param("classId")String classId,
			@Param("studentId")String studentId);
	
	
	@Delete("delete from class_student where id=#{id}")  
	int deleteClassStudent(String id);
	

}
