package com.sas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sas.entity.AttendanceEntity;

public interface AttendanceMapper {
	
	@Select("select * from attendance")

	@Results({

	@Result(property = "attendanceId",  column = "attendance_id"),
	
	@Result(property = "checkId", column = "check_id"),

	@Result(property = "studentId", column = "student_id"),
	
	@Result(property = "attendanceKind",  column = "attendance_kind"),
	
	@Result(property = "attendanceValid",  column = "attendance_valid"),
	
	@Result(property = "attendanceTime",  column = "attendance_time")

	})

	List<AttendanceEntity> getAllAttendance();
	
	
	
	@Select("select * from attendance where attendance_id = #{attendanceId}")

	@Results({

	@Result(property = "attendanceId",  column = "attendance_id"),
	
	@Result(property = "checkId", column = "check_id"),

	@Result(property = "studentId", column = "student_id"),
	
	@Result(property = "attendanceKind",  column = "attendance_kind"),
	
	@Result(property = "attendanceValid",  column = "attendance_valid"),
	
	@Result(property = "attendanceTime",  column = "attendance_time")

	})

	AttendanceEntity getAttendanceByAttendanceId(String attendanceId);
	
	
	
	@Select("select * from attendance where check_id = #{checkId}")

	@Results({

	@Result(property = "attendanceId",  column = "attendance_id"),
	
	@Result(property = "checkId", column = "check_id"),

	@Result(property = "studentId", column = "student_id"),
	
	@Result(property = "attendanceKind",  column = "attendance_kind"),
	
	@Result(property = "attendanceValid",  column = "attendance_valid"),
	
	@Result(property = "attendanceTime",  column = "attendance_time")

	})

	List<AttendanceEntity> getAttendanceByCheckId(String checkId);
	
	
	
	@Select("select * from attendance where student_id = #{studentId}")

	@Results({

	@Result(property = "attendanceId",  column = "attendance_id"),
	
	@Result(property = "checkId", column = "check_id"),

	@Result(property = "studentId", column = "student_id"),
	
	@Result(property = "attendanceKind",  column = "attendance_kind"),
	
	@Result(property = "attendanceValid",  column = "attendance_valid"),
	
	@Result(property = "attendanceTime",  column = "attendance_time")

	})

	List<AttendanceEntity> getAttendanceByStudentId(String studentId);
	
	
	@Select("select * from attendance where check_id = #{checkId} and student_id = #{studentId}")

	@Results({

	@Result(property = "attendanceId",  column = "attendance_id"),
	
	@Result(property = "checkId", column = "check_id"),

	@Result(property = "studentId", column = "student_id"),
	
	@Result(property = "attendanceKind",  column = "attendance_kind"),
	
	@Result(property = "attendanceValid",  column = "attendance_valid"),
	
	@Result(property = "attendanceTime",  column = "attendance_time")

	})

	AttendanceEntity getAttendanceByCheckIdStudentId(@Param("checkId")String checkId,@Param("studentId")String studentId);
	
	
	
	@Insert("insert into attendance(attendance_id,check_id,"
			+ "student_id,attendance_kind, attendance_valid) "
			+ "values(#{attendanceId},#{checkId},#{studentId},"
			+ "#{attendanceKind},#{attendanceValid})")  
    int insertAttendance(AttendanceEntity attendanceEntity); 
	
	
	
	@Update("update attendance set check_id=#{checkId},"
			+ "student_id=#{studentId},attendance_kind=#{attendanceKind},"
			+ "attendance_valid=#{attendanceValid}"
			+ " where attendance_id=#{attendanceId}")  
	int updateAttendance(AttendanceEntity attendanceEntity);
	
	
	@Update("update attendance set check_id=#{checkId},"
			+ "student_id=#{studentId},attendance_kind=#{attendanceKind},"
			+ "attendance_valid=#{attendanceValid},attendance_time=#{attendanceTime}"
			+ " where attendance_id=#{attendanceId}")  
	int updateAttendanceIncludeTime(AttendanceEntity attendanceEntity);
	
	
	@Delete("delete from attendance where attendance_id=#{attendanceId}")  
	int deleteAttendance(String attendanceId);

}
