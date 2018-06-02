package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.LoginEntity;

public interface LoginMapper {
	
	@Select("select user_id from login where user_name = #{userName} and user_password = #{userPassword}")
	String getUserId(@Param("userName")String userName,@Param("userPassword")String userPassword);
	
	
	
	@Select("select * from login where user_name = #{userName}")
	@Results({

		@Result(property = "userID",  column = "user_id"),

		@Result(property = "userName", column = "user_name"),
		
		@Result(property = "userPassword",  column = "user_password")

		})
	List<LoginEntity> isUserNameExisted(String userName);
	
	
	
	@Insert("insert into login(user_id,user_name,"
			+ "user_password) "
			+ "values(#{userID},#{userName},#{userPassword})")  
    int insertLogin(LoginEntity loginEntity); 
	
	
	
	@Update("update login set user_name=#{userName},"
			+ "user_password=#{userPassword}"
			+ " where user_id=#{userID}")  
	int updateLogin(LoginEntity loginEntity);
	
	
	
	@Delete("delete from login where user_id=#{userID}")  
	int deleteLogin(@Param("userID")String userId);

}
