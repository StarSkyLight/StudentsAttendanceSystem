package com.sas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sas.entity.LoginEntity;
import com.sas.entity.StudentEntity;
import com.sas.mapper.LoginMapper;
import com.sas.mapper.StudentMapper;
import com.sas.util.UUIDGenerater;

@RestController
@RequestMapping("/registercontrol")
public class RegisterController {
	
	@Autowired
	private LoginMapper loginMapper;
	@Autowired
	private StudentMapper studentMapper;
	
	
	@RequestMapping(value="/register")
	public String register(@RequestParam String registerInfor) throws Exception{
		String tempJson = registerInfor;
		String userID = "";
		
		if(tempJson != null && !tempJson.equals("")){
			Gson gson = new Gson();
			LoginEntity loginEntity = new LoginEntity();
			StudentEntity studentEntity = new StudentEntity();
			
			String loginInfor = gson.fromJson(tempJson,JsonArray.class).get(0).getAsString();
			loginEntity = gson.fromJson(loginInfor, LoginEntity.class);
			
			String studentInfor = gson.fromJson(tempJson,JsonArray.class).get(1).getAsString();
			studentEntity = gson.fromJson(studentInfor, StudentEntity.class);
			
			if(loginEntity != null && studentEntity != null){
				/**
				 * 添加id
				 * uuid自动生成
				 */
				String tempId  = UUIDGenerater.getUUID();
				loginEntity.setUserID(tempId);
				studentEntity.setStudentId(tempId);
				/**
				 * 插入login表
				 * insertLogin()返回值为受影响的行数？
				 */
				if(loginMapper.insertLogin(loginEntity) >= 1){
					if(studentMapper.insertStudent(studentEntity) >= 1){
						userID = tempId;
					}
				}
				
			}
		}
		
		return userID;
		
	}
	
}
