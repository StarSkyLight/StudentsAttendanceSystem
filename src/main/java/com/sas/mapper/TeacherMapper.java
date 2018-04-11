package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.TeacherEntity;

public interface TeacherMapper {
	
	@Select("select * from teacher")

	@Results({

	@Result(property = "teacherId",  column = "teacher_id"),

	@Result(property = "teacherName", column = "teacher_name"),
	
	@Result(property = "teacherSex",  column = "teacher_sex"),
	
	@Result(property = "teacherSchool",  column = "teacher_school"),
	
	@Result(property = "teacherEmail",  column = "teacher_email")

	})

	List<TeacherEntity> getAllTeachers();
	
	
	
	
	
	@Select("select * from teacher where teacher_id = #{teacherId}")

	@Results({

	@Result(property = "teacherId",  column = "teacher_id"),

	@Result(property = "teacherName", column = "teacher_name"),
	
	@Result(property = "teacherSex",  column = "teacher_sex"),
	
	@Result(property = "teacherSchool",  column = "teacher_school"),
	
	@Result(property = "teacherEmail",  column = "teacher_email")

	})

	TeacherEntity getTeacher(String teacherId);
	
	
	
	
	@Insert("insert into teacher(teacher_id,teacher_name,teacher_sex,teacher_school, teacher_email) "
			+ "values(#{teacherId},#{teacherName},#{teacherSex},#{teacherSchool},#{teacherEmail})")  
    int insertTeacher(TeacherEntity teacherEntity); 
	
	
	
	@Update("update teacher set teacher_name=#{teacherName},"
			+ "teacher_sex=#{teacherSex},teacher_school=#{teacherSchool},"
			+ "teacher_email=#{teacherEmail} where teacher_id=#{teacherId}")  
	int updateTeacher(TeacherEntity teacherEntity);
	
	
	
	@Delete("delete from teacher where teacher_id=#{teacherId}")  
	int deleteTeacher(String teacherId);

}
