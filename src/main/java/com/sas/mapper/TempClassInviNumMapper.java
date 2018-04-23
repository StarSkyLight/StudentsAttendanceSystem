package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.TempClassINEntity;

public interface TempClassInviNumMapper {
	
	@Select("select invite_num from temp_class_invi_num where class_id=#{classId}")
	List<String> getInviteNumByClassId(String classId);
	
	
	@Select("select class_id from temp_class_invi_num where invite_num=#{inviteNum}")
	List<String> getClassIdByInviteNum(String inviteNum);
	
	
	@Select("select * from temp_class_invi_num")
	@Results({

		@Result(property = "classId",  column = "class_id"),

		@Result(property = "inviteNum", column = "invite_num"),
		
		@Result(property = "timeStamp",  column = "time_stamp")

		})
	List<TempClassINEntity> getAllTimestampByClassId();
	
	
	@Insert("insert into temp_class_invi_num(class_id,invite_num) "
			+ "values(#{classId},#{inviteNum})")  
    int insertInvNum(@Param("classId")String classId,@Param("inviteNum")String inviteNum); 
	
	
	
	@Update("update temp_class_invi_num set class_id=#{classId},"
			+ "invite_num=#{inviteNum} where class_id=#{classId}")  
	int updateInvNum(@Param("classId")String classId,@Param("inviteNum")String inviteNum);
	
	
	
	@Delete("delete from temp_class_invi_num where class_id=#{classId}")  
	int deleteInvNum(String classId);

}
