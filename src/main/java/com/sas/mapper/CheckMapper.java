package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.CheckEntity;

public interface CheckMapper {
	
	@Select("select * from teacher_check")

	@Results({

	@Result(property = "checkId",  column = "check_id"),
	
	@Result(property = "classId", column = "class_id"),

	@Result(property = "teacherId", column = "teacher_id"),
	
	@Result(property = "checkKind",  column = "check_kind"),
	
	@Result(property = "checkTime",  column = "check_time")
	
	})

	List<CheckEntity> getAllCheck();
	
	
	
	@Select("select * from teacher_check where check_id = #{checkId}")

	@Results({

	@Result(property = "checkId",  column = "check_id"),
	
	@Result(property = "classId", column = "class_id"),

	@Result(property = "teacherId", column = "teacher_id"),
	
	@Result(property = "checkKind",  column = "check_kind"),
	
	@Result(property = "checkTime",  column = "check_time")
	
	})

	CheckEntity getCheckByCheckId(String checkId);
	
	
	
	@Select("select * from teacher_check where class_id = #{classId} and teacher_id = #{teacherId}")

	@Results({

	@Result(property = "checkId",  column = "check_id"),
	
	@Result(property = "classId", column = "class_id"),

	@Result(property = "teacherId", column = "teacher_id"),
	
	@Result(property = "checkKind",  column = "check_kind"),
	
	@Result(property = "checkTime",  column = "check_time")
	
	})

	List<CheckEntity> getCheckByClassIdAndTeacherId(@Param("classId")String classId,
			@Param("teacherId")String teacherId);
	
	
	
	@Insert("insert into teacher_check(check_id,class_id,"
			+ "teacher_id,check_kind) "
			+ "values(#{checkId},#{classId},#{teacherId},"
			+ "#{checkKind})")  
    int insertCheck(CheckEntity checkEntity); 
	
	
	
	@Update("update teacher_check set class_id=#{classId},"
			+ "teacher_id=#{teacherId},check_kind=#{checkKind}"
			+ " where check_id=#{checkId}")  
	int updateCheck(CheckEntity checkEntity);
	
	
	
	@Delete("delete from teacher_check where check_id=#{checkId}")  
	int deleteCheck(String checkId);

}
