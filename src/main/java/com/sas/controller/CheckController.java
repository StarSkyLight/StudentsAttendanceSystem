package com.sas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sas.entity.CheckEntity;
import com.sas.mapper.CheckMapper;
import com.sas.util.UUIDGenerater;

@RestController
@RequestMapping("/checkcontrol")
public class CheckController {
	
	@Autowired
	private CheckMapper checkMapper;
	
	/**
	 * 添加课程
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
				returnInfor = getCheck(checkEntity.getClassId(),checkEntity.getTeacherId());
			}
		}
		
		return returnInfor;
		
	}
	
	
	
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
	
	
	@RequestMapping(value="/getcheck")
	public String getChecks(@RequestParam String classTeacherInfor ) throws Exception{
		String returnInfor = "";
		
		CheckEntity tempCheck = new CheckEntity();
		Gson gson = new Gson();
		tempCheck = gson.fromJson(classTeacherInfor, CheckEntity.class);
		returnInfor = getCheck(tempCheck.getClassId(),tempCheck.getTeacherId());
		
		return returnInfor;
	}
	
}
