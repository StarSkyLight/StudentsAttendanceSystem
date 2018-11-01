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
import com.sas.entity.ClassEntity;
import com.sas.entity.ClassStudentEntity;
import com.sas.entity.ClassTeacherEntity;
import com.sas.mapper.AttendanceMapper;
import com.sas.mapper.CheckMapper;
import com.sas.mapper.ClassMapper;
import com.sas.mapper.ClassStudentMapper;
import com.sas.mapper.ClassTeacherMapper;
import com.sas.mapper.TempClassInviNumMapper;
import com.sas.util.InviteNumberGenerater;
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
	@Autowired
	private TempClassInviNumMapper tempClassInviNumMapper;
	@Autowired
	private AttendanceMapper attendanceMapper;
	
	
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
	
	/**
	 * 教师获取课程信息
	 * @param teacherInfor
	 * @return
	 */
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
	
	/**
	 * 学生查询课程信息
	 * @param studentInfor
	 * @return
	 */
	@RequestMapping(value="/getstudentclasssipminfo")
	public String studentClassSimpInfo(@RequestParam String studentInfor){
		String tempJson = studentInfor;
		String result = "";
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		Gson gson = new Gson();
		
		if(tempJson != null && !tempJson.equals("")){
			/**
			 * 查询当前学生的所有课程id
			 */
			List<String> classesIdList = new ArrayList<String>();
			classesIdList = classStudentMapper.getClasses(tempJson);
			/**
			 * 遍历所有id，查询相应的点名信息，添加到队列中
			 */
			for(int i = 0;i<classesIdList.size();i++){
				List<CheckEntity> classesSimpInforList = new ArrayList<CheckEntity>();
				ClassEntity classEntity = new ClassEntity();
				Map<String,String> tempMap = new HashMap<String,String>();
				/**
				 * 查询课程信息
				 */
				classEntity = classMapper.getaClass(classesIdList.get(i));
				
				/**
				 * 查询课程点名信息
				 */
				classesSimpInforList = checkMapper.getCheckByClassIdAndTeacherId(classesIdList.get(i),
						classEntity.getClassFounderId());
				
				
				/**
				 * 将课程点名信息和课程信息转换为json，并加入map中
				 */
				tempMap.put("classesSimpInforList", gson.toJson(classesSimpInforList));
				tempMap.put("classEntity", gson.toJson(classEntity));
				
				resultList.add(tempMap);
				
			}
			/**
			 * 将课程信息封装成json格式
			 */
			
			result = gson.toJson(resultList);
		}
		
		return result;
	}
	
	/**
	 * 学生添加课程，通过邀请码
	 * @param infor
	 * @return
	 */
	@RequestMapping(value="/addstudentclasss")
	public String addStudentClass(@RequestParam String infor){
		String result = "";
		
		if(infor != null && !infor.equals("")){
			Map<String,String> tempMap = new HashMap<String,String>();
			Gson gson = new Gson();
			
			tempMap = gson.fromJson(infor, new TypeToken<Map<String,String>>(){}.getType());
			
			String inviteNum = tempMap.get("inviteNum");
			
			List<String> tempList = tempClassInviNumMapper.getClassIdByInviteNum(inviteNum);
			
			if(tempList.size() > 0){
				if(classStudentMapper.insertClassStudent(UUIDGenerater.getUUID(), 
						tempList.get(0), tempMap.get("studentId")) > 0){
					studentClassSimpInfo(tempMap.get("studentId"));
					
					result = "OK";
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * 教师生成邀请码
	 * @param classId
	 * @return
	 */
	@RequestMapping(value="/getinvitenumber")
	public String teacherInviStu(@RequestParam String classId){
		String result = "";
		
		if(classId != null && !classId.equals("")){
			String tempStr = InviteNumberGenerater.getInviteNumber();
			List<String> tempList = tempClassInviNumMapper.getInviteNumByClassId(classId);
			if(tempList.size() > 0){
				tempClassInviNumMapper.deleteInvNum(classId);
			}
			/**
			 * 如果课程邀请码已经存在，则重新生成一个，直到不重复
			 */
			while(true){
				tempList = tempClassInviNumMapper.getClassIdByInviteNum(tempStr);
				if(tempList.size() > 0){
					tempStr = InviteNumberGenerater.getInviteNumber();
				}
				else{
					break;
				}
			}
			
			if(tempClassInviNumMapper.insertInvNum(classId, tempStr) > 0){
				result = tempStr;
			}
		}
		
		return result;
		
	}
	
	/**
	 * 教师删除课程
	 * @param classId
	 * @return
	 */
	@RequestMapping(value="/deleteclass")
	public String teacherDelClass(@RequestParam String classId){
		String result = "";
		
		ClassEntity classEntity = new ClassEntity();
		List<ClassStudentEntity> classStudent = new ArrayList<ClassStudentEntity>();
		List<ClassTeacherEntity> classTeacher = new ArrayList<ClassTeacherEntity>();
		List<CheckEntity> check = new ArrayList<CheckEntity>();
		
		
		/**
		 * 数据备份，以防插入不成功无法恢复
		 */
		classEntity = classMapper.getaClass(classId);
		classStudent = classStudentMapper.getAllByClassId(classId);
		classTeacher = classTeacherMapper.getAllByClassId(classId);
		check = checkMapper.getCheckByClassIdAndTeacherId(classId, classEntity.getClassFounderId());
		List<List<AttendanceEntity>> attList = new ArrayList<List<AttendanceEntity>>();
		for(CheckEntity tCheckEntity : check){
			List<AttendanceEntity> attendance = new ArrayList<AttendanceEntity>();
			attendance = attendanceMapper.getAttendanceByCheckId(tCheckEntity.getCheckId());
			attList.add(attendance);
		}
		
		
		classMapper.deleteClass(classId);
		for(ClassStudentEntity tClassStudentEntity : classStudent){
			classStudentMapper.deleteClassStudent(tClassStudentEntity.getId());
		}
		for(ClassTeacherEntity tClassTeacherEntity : classTeacher){
			classTeacherMapper.deleteClassTeacher(tClassTeacherEntity.getId());
		}
	    tempClassInviNumMapper.deleteInvNum(classId);
		for(CheckEntity tCheckEntity : check){
			checkMapper.deleteCheck(tCheckEntity.getCheckId());
		}
		for(List<AttendanceEntity> tList : attList){
			for(AttendanceEntity tAttendanceEntity : tList){
				attendanceMapper.deleteAttendance(tAttendanceEntity.getAttendanceId());
			}
		}
		
		result = "OK";
		
		return result;
	}
	
	
	
	@RequestMapping(value="/studnetdeleteclass")
	public String studentDeleteClass(@RequestParam String classId_studentInfor){
		String result = "";
		
		if(classId_studentInfor != null && !classId_studentInfor.equals("")){
			Map<String,String> tempMap = new HashMap<String,String>();
			Gson gson = new Gson();
			tempMap = gson.fromJson(classId_studentInfor, new TypeToken<Map<String,String>>(){}.getType());
			
			/**
			 * 每一个class对应多个check
			 * 每一个check对应一个该学生的一个attendance
			 * 首先，用classId查找对应的class，得到对应class的teacherId
			 * 之后，用classId（和teacherId）查找出所有的check
			 * 再用每个checkId与studentId配合找到该学生的attendance并删除
			 * 用classId和studentId找到并删除class与student关系
			 */
			ClassEntity classEntity = classMapper.getaClass(tempMap.get("classId"));
			if(classEntity != null){
				List<CheckEntity> checkList = new ArrayList<CheckEntity>();
				checkList = checkMapper.getCheckByClassIdAndTeacherId(classEntity.getClassId(),
						classEntity.getClassFounderId());
				
				if(checkList != null){
					
					for(CheckEntity checkEntity : checkList){
						
						AttendanceEntity attendanceEntity = 
								attendanceMapper.getAttendanceByCheckIdStudentId(
										checkEntity.getCheckId(), tempMap.get("studentInfor"));
						attendanceMapper.deleteAttendance(attendanceEntity.getAttendanceId());
					}
					
					ClassStudentEntity classStudentEntity = classStudentMapper
							.getclassByclassIdStudentId(tempMap.get("classId"), 
							tempMap.get("studentInfor"));
					classStudentMapper.deleteClassStudent(classStudentEntity.getId());
					
					result = "OK";
				}
				
			}
			
			
			
			
		}
		
		return result;
	}
}
