package com.sas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sas.entity.AttendanceEntity;
import com.sas.entity.StudentEntity;
import com.sas.mapper.AttendanceMapper;
import com.sas.mapper.StudentMapper;

@RestController
@RequestMapping("/attendancecontrol")
public class AttendanceController {
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	@Autowired
	private StudentMapper studentMapper;
	
	/**
	 * 通过checkId查询该考勤下的所有签到信息
	 * @param checkInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getallattendance")
	public String getAllAttendance(@RequestParam String checkInfor) throws Exception{
		String returnStr = "";
		
		if(checkInfor != null && !checkInfor.equals("")){
			List<AttendanceEntity> list = new ArrayList<AttendanceEntity>();
			list = attendanceMapper.getAttendanceByCheckId(checkInfor);
			
			List<Map<String,String>> templist = new ArrayList<Map<String,String>>();
			Gson gson = new Gson();
			for(AttendanceEntity tempAttendance : list){
				String tempStuId = tempAttendance.getStudentId();
				StudentEntity tempStudent = studentMapper.getStudent(tempStuId);
				
				Map<String,String> tempMap = new HashMap<String,String>();
				
				
				tempMap.put("attendanceEntity", gson.toJson(tempAttendance));
				tempMap.put("studentEntity", gson.toJson(tempStudent));
				
				templist.add(tempMap);
			}
			
			returnStr = gson.toJson(templist);
		}
		
		return returnStr;
	}
	
	

}
