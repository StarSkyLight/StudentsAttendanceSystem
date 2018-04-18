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
import com.sas.entity.CheckEntity;
import com.sas.entity.ClassEntity;
import com.sas.mapper.CheckMapper;
import com.sas.mapper.ClassMapper;
import com.sas.mapper.ClassStudentMapper;
import com.sas.mapper.ClassTeacherMapper;
import com.sas.util.UUIDGenerater;

@RestController
@RequestMapping("/classescontrol")
public class ClassesController {
	
	@Autowired
	private ClassTeacherMapper classTeacherMapper;
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private CheckMapper checkMapper;
	@Autowired
	private ClassStudentMapper classStudentMapper;
	
	/**
	 * 查询某个教师所有课程的方法
	 * @param teacherInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getteacherclasses")
	public String teacherClasses(@RequestParam String teacherInfor) throws Exception{
		String tempJson = teacherInfor;
		String result = "";
		List<String> classesIdList = new ArrayList<String>();
		List<ClassEntity> classesInforList = new ArrayList<ClassEntity>();
		
		if(tempJson != null && !tempJson.equals("")){
			/**
			 * 查询当前教师的所有课程id
			 */
			classesIdList = classTeacherMapper.getClasses(tempJson);
			/**
			 * 遍历所有id，查询相应的课程信息，添加到队列中
			 */
			for(int i = 0;i<classesIdList.size();i++){
				ClassEntity tempClass = new ClassEntity();
				tempClass = classMapper.getaClass(classesIdList.get(i));
				classesInforList.add(tempClass);
			}
			/**
			 * 将课程信息封装成json格式
			 */
			Gson gson = new Gson();
			result = gson.toJson(classesInforList);
		}
		
		return result;
		
	}
	
	/**
	 * 教师添加课程的方法
	 * @param classInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addclass")
	public String addClass(@RequestParam String classInfor) throws Exception{
		
		String tempJson = classInfor;
		Gson gson = new Gson();
		ClassEntity classEntity = new ClassEntity();
		String result = "";
		
		classEntity = gson.fromJson(tempJson, ClassEntity.class);
		classEntity.setClassId(UUIDGenerater.getUUID());
		
		if(classMapper.insertClass(classEntity) >= 1){
			if(classTeacherMapper.insertClassTeacher(UUIDGenerater.getUUID(), 
					classEntity.getClassId(), classEntity.getClassFounderId()) >= 1){
				/**
				 * 若插入成功，返回该教师所有课程列表
				 */
				result = teacherClassSimpInfo(classEntity.getClassFounderId());
			}
			else{
				classMapper.deleteClass(classEntity.getClassId());
			}
			
		}
		
		return result;
		
	}
	
	@RequestMapping(value="/getteacherclasssipminfo")
	public String teacherClassSimpInfo(@RequestParam String teacherInfor){
		String tempJson = teacherInfor;
		String result = "";
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		Gson gson = new Gson();
		
		if(tempJson != null && !tempJson.equals("")){
			/**
			 * 查询当前教师的所有课程id
			 */
			List<String> classesIdList = new ArrayList<String>();
			classesIdList = classTeacherMapper.getClasses(tempJson);
			/**
			 * 遍历所有id，查询相应的点名信息，添加到队列中
			 */
			for(int i = 0;i<classesIdList.size();i++){
				List<CheckEntity> classesSimpInforList = new ArrayList<CheckEntity>();
				ClassEntity classEntity = new ClassEntity();
				List<String> studentsNum = new ArrayList<String>();
				Map<String,String> tempMap = new HashMap<String,String>();
				/**
				 * 查询课程点名信息
				 */
				classesSimpInforList = checkMapper.getCheckByClassIdAndTeacherId(classesIdList.get(i),
						tempJson);
				/**
				 * 查询课程信息
				 */
				classEntity = classMapper.getaClass(classesIdList.get(i));
				/**
				 * 查询该课程的所有学生
				 */
				studentsNum = classStudentMapper.getStudents(classesIdList.get(i));
				/**
				 * 将课程点名信息和课程信息转换为json，并加入map中
				 */
				tempMap.put("classesSimpInforList", gson.toJson(classesSimpInforList));
				tempMap.put("classEntity", gson.toJson(classEntity));
				tempMap.put("studentsNum", gson.toJson(studentsNum));
				
				resultList.add(tempMap);
			}
			/**
			 * 将课程信息封装成json格式
			 */
			
			result = gson.toJson(resultList);
		}
		
		return result;
	}

}
