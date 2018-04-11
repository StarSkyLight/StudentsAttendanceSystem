package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.StudentEntity;

public interface StudentMapper {
	
	@Select("select * from student")

	@Results({

	@Result(property = "studentId",  column = "student_id"),

	@Result(property = "studentName", column = "student_name"),
	
	@Result(property = "studentSex",  column = "student_sex"),
	
	@Result(property = "studentSchool",  column = "student_school"),
	
	@Result(property = "studentNumber",  column = "student_number"),
	
	@Result(property = "studentEmail",  column = "student_email")

	})

	List<StudentEntity> getAllStudents();
	
	
	
	@Select("select * from student where student_id = #{studentId}")

	@Results({

	@Result(property = "studentId",  column = "student_id"),

	@Result(property = "studentName", column = "student_name"),
	
	@Result(property = "studentSex",  column = "student_sex"),
	
	@Result(property = "studentSchool",  column = "student_school"),
	
	@Result(property = "studentNumber",  column = "student_number"),
	
	@Result(property = "studentEmail",  column = "student_email")

	})

	StudentEntity getStudent(String studentId);
	
	
	
	@Insert("insert into student(student_id,student_name,"
			+ "student_sex,student_school, student_number,student_email) "
			+ "values(#{studentId},#{studentName},#{studentSex},"
			+ "#{studentSchool},#{studentNumber},#{studentEmail})")  
    int insertStudent(StudentEntity studentEntity); 
	
	
	
	@Update("update student set student_name=#{studentName},"
			+ "student_sex=#{studentSex},student_school=#{studentSchool},"
			+ "student_number=#{studentNumber},student_email=#{studentEmail}"
			+ " where student_id=#{studentId}")  
	int updateStudent(StudentEntity studentEntity);
	
	
	
	@Delete("delete from student where student_id=#{studentId}")  
	int deleteStudent(String studentId);

}
