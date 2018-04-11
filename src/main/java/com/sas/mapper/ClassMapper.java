package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.ClassEntity;

public interface ClassMapper {
	
	@Select("select * from class")

	@Results({

	@Result(property = "classId",  column = "class_id"),

	@Result(property = "className", column = "class_name"),
	
	@Result(property = "classFounderId",  column = "class_founder_id")

	})

	List<ClassEntity> getAllClasses();
	
	
	
	@Select("select * from class where class_id = #{classId}")

	@Results({

	@Result(property = "classId",  column = "class_id"),

	@Result(property = "className", column = "class_name"),
	
	@Result(property = "classFounderId",  column = "class_founder_id")

	})

	ClassEntity getaClass(String classId);
	
	
	
	@Insert("insert into class(class_id,class_name,class_founder_id) "
			+ "values(#{classId},#{className},#{classFounderId})")  
    int insertClass(ClassEntity classEntity); 
	
	
	
	@Update("update class set class_name=#{className},class_founder_id=#{classFounderId}"
			+ " where class_id=#{classId}")  
	int updateClass(ClassEntity classEntity);
	
	
	@Delete("delete from class where class_id=#{classId}")  
	int deleteClass(String classId);

}
