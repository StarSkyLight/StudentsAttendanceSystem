package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface TempChickAttendanceNumMapper {
	
	@Select("select attendance_num from temp_chick_attendance_num where check_id=#{checkId}")
	String getAttendanceNumByCheckId(String checkId);
	
	
	@Select("select check_id from temp_chick_attendance_num where attendance_num=#{attendanceNum}")
	List<String> getCheckIdByAttendanceNum(String attendanceNum);
	
	
	
	@Insert("insert into temp_chick_attendance_num(check_id,attendance_num) "
			+ "values(#{checkId},#{attendanceNum})")  
    int insertAttendanceNum(@Param("checkId")String checkId,@Param("attendanceNum")String attendanceNum); 
	
	
	
	@Update("update temp_chick_attendance_num set check_id=#{checkId},"
			+ "attendance_num=#{attendanceNum} where check_id=#{checkId}")  
	int updateAttendanceNum(@Param("checkId")String checkId,@Param("attendanceNum")String attendanceNum);
	
	
	
	@Delete("delete from temp_chick_attendance_num where check_id=#{checkId}")  
	int deleteAttendanceNum(String checkId);

}
