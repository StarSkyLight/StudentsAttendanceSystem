package com.sas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sas.entity.StudentEntity;
import com.sas.entity.TeacherEntity;
import com.sas.mapper.StudentMapper;
import com.sas.mapper.TeacherMapper;

@RestController
@RequestMapping("/basicinfocontrol")
public class BasicInfoController {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	
	
	@RequestMapping(value="/getstudentbasicinfo")
	public String getStudentBasicInfo(@RequestParam String studentId) throws Exception {
		String result = "";
		
		if(studentId != null && !studentId.equals("")){
			Gson gson = new Gson();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentMapper.getStudent(studentId);
			
			result = gson.toJson(studentEntity);
		}
		
		return result;
	}
	
	
	
	@RequestMapping(value="/getteacherbasicinfo")
	public String getTeacherBasicInfo(@RequestParam String teacherId) throws Exception {
		String result = "";
		
		if(teacherId != null && !teacherId.equals("")){
			Gson gson = new Gson();
			TeacherEntity teacherEntity = new TeacherEntity();
			teacherEntity = teacherMapper.getTeacher(teacherId);
			
			result = gson.toJson(teacherEntity);
		}
		
		return result;
	}
}
