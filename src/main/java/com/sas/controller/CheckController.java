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
import com.google.gson.reflect.TypeToken;
import com.sas.entity.AttendanceEntity;
import com.sas.entity.CheckEntity;
import com.sas.mapper.AttendanceMapper;
import com.sas.mapper.CheckMapper;
import com.sas.mapper.TempChickAttendanceNumMapper;
import com.sas.util.InviteNumberGenerater;
import com.sas.util.UUIDGenerater;

@RestController
@RequestMapping("/checkcontrol")
public class CheckController {
	
	@Autowired
	private CheckMapper checkMapper;
	@Autowired
	private AttendanceMapper attendanceMapper;
	@Autowired
	private TempChickAttendanceNumMapper tempChickAttendanceNumMapper;
	
	/**
	 * 添加点名
	 * @param addCheckInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addcheck")
	public String addCheck(@RequestParam String addCheckInfor) throws Exception{
		String returnInfor = "";
		
		if(addCheckInfor != null && !addCheckInfor.equals("")){
			CheckEntity checkEntity = new CheckEntity();
			Gson gson = new Gson();
			checkEntity = gson.fromJson(addCheckInfor,CheckEntity.class);
			
			checkEntity.setCheckId(UUIDGenerater.getUUID());
			
			if(checkMapper.insertCheck(checkEntity) >= 1){
				
				String tempAttendanceNum = InviteNumberGenerater.getInviteNumber();
				if(tempChickAttendanceNumMapper.insertAttendanceNum(checkEntity.getCheckId(),
						tempAttendanceNum) > 0){
					
					Map<String,String> tempMap = new HashMap<String,String>();
					String tStr = "";
					tStr = getCheck(checkEntity.getClassId(),checkEntity.getTeacherId());
					tempMap.put("check", tStr);
					tempMap.put("attendanceNum", tempAttendanceNum);
					
					returnInfor = gson.toJson(tempMap);
				}else{
					checkMapper.deleteCheck(checkEntity.getCheckId());
				}
				
			}
		}
		
		return returnInfor;
		
	}
	
	/**
	 * 教师停止考勤
	 * @param checkId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stopcheck")
	public String stopCheck(@RequestParam String checkId) throws Exception{
		String returnInfor = "";
		
		if(checkId != null && !checkId.equals("")){
			CheckEntity checkEntity = new CheckEntity();
			checkEntity = checkMapper.getCheckByCheckId(checkId);
			checkEntity.setCheckIsOver(true);
			
			if(checkMapper.updateCheck(checkEntity) > 0){
				returnInfor = "OK";
			}
		}
		
		
		return returnInfor;
	}
	
	
	/**
	 * 获取点名信息，内部方法
	 * @param classInfor
	 * @param teacherInfor
	 * @return
	 * @throws Exception
	 */
	public String getCheck(@RequestParam String classInfor,@RequestParam String teacherInfor) 
			throws Exception{
		String returnInfor = "";
		
		if(classInfor != null && !classInfor.equals("")){
			if(teacherInfor != null && !teacherInfor.equals("")){
				List<CheckEntity> list = new ArrayList<CheckEntity>();
				list = checkMapper.getCheckByClassIdAndTeacherId(classInfor, teacherInfor);
				
				Gson gson = new Gson();
				returnInfor = gson.toJson(list);
			}
		}
		
		return returnInfor;
	}
	
	/**
	 * 获取点名列表
	 * @param classTeacherInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getcheck")
	public String getChecks(@RequestParam String classTeacherInfor ) throws Exception{
		String returnInfor = "";
		
		CheckEntity tempCheck = new CheckEntity();
		Gson gson = new Gson();
		tempCheck = gson.fromJson(classTeacherInfor, CheckEntity.class);
		returnInfor = getCheck(tempCheck.getClassId(),tempCheck.getTeacherId());
		
		return returnInfor;
	}
	
	
	/**
	 * 学生获取考勤和签到
	 * @param classteacherInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getcheckstudent")
	public String getChecksStudent(@RequestParam String class_teacher_studentInfor ) throws Exception{
		String returnInfor = "";
		
		Gson gson = new Gson();
		
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap = gson.fromJson(class_teacher_studentInfor, 
				new TypeToken<Map<String,String>>(){}.getType());
		
		CheckEntity tempCheck = new CheckEntity();
		
		tempCheck = gson.fromJson(tempMap.get("checkEntity"), CheckEntity.class);

		List<CheckEntity> list = new ArrayList<CheckEntity>();
		if(tempCheck.getClassId() != null && !tempCheck.getClassId().equals("")){
			if(tempCheck.getTeacherId() != null && !tempCheck.getTeacherId().equals("")){
				
				list = checkMapper.getCheckByClassIdAndTeacherId(tempCheck.getClassId(),
						tempCheck.getTeacherId());
				
			}
		}
		
		List<Map<String,String>> listResult = new ArrayList<Map<String,String>>();
		for(CheckEntity checkEntity : list){
			Map<String,String> tMap = new HashMap<String,String>();
			AttendanceEntity attendanceEntity = 
					attendanceMapper.getAttendanceByStudentIdCheckId(checkEntity.getCheckId(),
							tempMap.get("studentInfor"));
			tMap.put("check", gson.toJson(checkEntity));
			tMap.put("attendance", gson.toJson(attendanceEntity));
			
			listResult.add(tMap);
		}
		
		returnInfor = gson.toJson(listResult);
		
		return returnInfor;
	}
	
	/**
	 * 学生签到方法
	 * @param attendanceInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addAttendance")
	public String getAllAttendance(@RequestParam String attendanceInfor) throws Exception{
		String returnInfor = "";
		
		if(attendanceInfor != null && !attendanceInfor.equals("")){
			Gson gson = new Gson();

	        Map<String,String> tempMap = new HashMap<String,String>();
	        tempMap = gson.fromJson(attendanceInfor, new TypeToken<Map<String,String>>(){}.getType());
	        
	        AttendanceEntity attendanceEntity = new AttendanceEntity();
	        attendanceEntity = gson.fromJson(tempMap.get("attendanceEntity"), AttendanceEntity.class);
	        
	    
	        CheckEntity checkEntity = new CheckEntity();
	        checkEntity = checkMapper.getCheckByCheckId(attendanceEntity.getCheckId());
	        if(!checkEntity.isCheckIsOver()){
	        	if(tempChickAttendanceNumMapper.getAttendanceNumByCheckId(attendanceEntity.getCheckId()).equals(tempMap.get("attendanceNum"))){
	        		attendanceEntity.setAttendanceId(UUIDGenerater.getUUID());
		        	attendanceEntity.setAttendanceValid(true);
		        	if(attendanceMapper.insertAttendance(attendanceEntity) > 0){
		        		returnInfor = "OK";
		        	}
	        	}
	        }
		}
		
		return returnInfor;
	}
}
