package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.ClassTeacherEntity;

public interface ClassTeacherMapper {
	
	@Select("select teacher_id from class_teacher where class_id = #{classId}")
	List<String> getTeachers(String classId);
	
	@Select("select * from class_teacher where class_id = #{classId}")
	List<ClassTeacherEntity> getAllByClassId(String classId);
	
	@Select("select class_id from class_teacher where teacher_id = #{teacherId} "
			+ "order by time_stamp desc")
	List<String> getClasses(String teacherId);
	
	
	@Insert("insert into class_teacher(id,class_id,teacher_id) "
			+ "values(#{id},#{classId},#{teacherId})")  
    int insertClassTeacher(@Param("id")String id,@Param("classId")String classId,
    		@Param("teacherId")String teacherId); 
	
	
	@Update("update class_teacher set class_id=#{classId},teacher_id=#{teacherId}"
			+ " where id=#{id}")  
	int updateClassTeacher(@Param("id")String id,@Param("classId")String classId,
			@Param("teacherId")String teacherId);
	
	
	@Delete("delete from class_teacher where id=#{id}")  
	int deleteClassTeacher(String id);

}
