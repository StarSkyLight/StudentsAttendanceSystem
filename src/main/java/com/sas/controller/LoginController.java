package com.sas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sas.entity.LoginEntity;
import com.sas.mapper.LoginMapper;

@RestController
@RequestMapping("/logincontrol")
public class LoginController {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@RequestMapping(value="/login")
	public String login(@RequestParam String username_password ) throws Exception{
		String tempJson = username_password;
		String userID = "";
		
		if(tempJson != null && !tempJson.equals("")){
			Gson gson = new Gson();
			LoginEntity loginEntity = new LoginEntity();
			loginEntity = gson.fromJson(tempJson, LoginEntity.class);
			
			if(loginEntity.getUserName() != null && loginEntity.getUserPassword() != null){
				userID = loginMapper.getUserId(loginEntity.getUserName(), 
						loginEntity.getUserPassword());
			}
		}
		
		
		return userID;
		
	}

}
