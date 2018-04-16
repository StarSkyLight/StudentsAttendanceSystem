package com.sas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sas.entity.ClassEntity;
import com.sas.mapper.ClassMapper;
import com.sas.mapper.ClassTeacherMapper;
import com.sas.util.UUIDGenerater;

@RestController
@RequestMapping("/classescontrol")
public class ClassesController {
	
	@Autowired
	private ClassTeacherMapper classTeacherMapper;
	@Autowired
	private ClassMapper classMapper;
	
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
			classesIdList = classTeacherMapper.getClasses(teacherInfor);
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
			if(classTeacherMapper.insertClass(UUIDGenerater.getUUID(), 
					classEntity.getClassId(), classEntity.getClassFounderId()) >= 1){
				/**
				 * 若插入成功，返回该教师所有课程列表
				 */
				result = teacherClasses(classEntity.getClassFounderId());
			}
			else{
				classMapper.deleteClass(classEntity.getClassId());
			}
			
		}
		
		return result;
		
	}

}
