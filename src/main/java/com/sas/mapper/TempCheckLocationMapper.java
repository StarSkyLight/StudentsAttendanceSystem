package com.sas.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.LocationEntity;

public interface TempCheckLocationMapper {
	
	@Select("select * from temp_check_location where check_id = #{checkId}")
	@Results({

		@Result(property = "checkId",  column = "check_id"),
		
		@Result(property = "latitude", column = "latitude"),

		@Result(property = "longitude", column = "longitude")
		
		})
	LocationEntity getLocationByCheckId(String checkId);
	
	@Insert("insert into temp_check_location(check_id,latitude,"
			+ "longitude) "
			+ "values(#{checkId},#{latitude},#{longitude})")  
    int insertLocation(LocationEntity locationEntity); 
	
	
	
	@Update("update temp_check_location set latitude=#{latitude},"
			+ "longitude=#{longitude}"
			+ " where check_id=#{checkId}")  
	int updateLocation(LocationEntity locationEntity);
	
	
	
	@Delete("delete from temp_check_location where check_id=#{checkId}")  
	int deleteLocation(String checkId);

}
