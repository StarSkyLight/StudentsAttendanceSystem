package com.sas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sas.entity.LoginEntity;
import com.sas.entity.StudentEntity;
import com.sas.entity.TeacherEntity;
import com.sas.mapper.LoginMapper;
import com.sas.mapper.StudentMapper;
import com.sas.mapper.TeacherMapper;

@RestController
@RequestMapping("/logincontrol")
public class LoginController {
	
	@Autowired
	private LoginMapper loginMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	
	@RequestMapping(value="/login")
	public String login(@RequestParam String username_password ) throws Exception{
		String tempJson = username_password;
		String userID = "";
		
		if(tempJson != null && !tempJson.equals("")){
			Gson gson = new Gson();
			Map<String,String > tempMap = new HashMap<String,String>();
			
			tempMap = gson.fromJson(tempJson,new TypeToken<Map<String,String >>(){}.getType());
			
			String tempStr = "";
			
			tempStr = tempMap.get("user");
			LoginEntity loginEntity = new LoginEntity();
			loginEntity = gson.fromJson(tempStr, LoginEntity.class);
			
			tempStr = tempMap.get("character");
			
			
			
			if(loginEntity.getUserName() != null && loginEntity.getUserPassword() != null){
				String tString = "";
				tString = loginMapper.getUserId(loginEntity.getUserName(), 
						loginEntity.getUserPassword());
				if(tempStr.equals("student")){
					StudentEntity getStudent = studentMapper.getStudent(tString);
					if(getStudent != null){
						userID = tString;
					}
				}
				else if(tempStr.equals("teacher")){
					TeacherEntity getTeacher = teacherMapper.getTeacher(tString);
					if(getTeacher != null){
						userID = tString;
					}
				}
			}
		}
		
		
		return userID;
		
	}

}
