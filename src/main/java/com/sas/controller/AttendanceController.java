package com.sas.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sas.entity.AttendanceEntity;
import com.sas.entity.CheckEntity;
import com.sas.entity.ClassEntity;
import com.sas.entity.StudentEntity;
import com.sas.mapper.AttendanceMapper;
import com.sas.mapper.CheckMapper;
import com.sas.mapper.ClassMapper;
import com.sas.mapper.ClassStudentMapper;
import com.sas.mapper.StudentMapper;

@RestController
@RequestMapping("/attendancecontrol")
public class AttendanceController {
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private CheckMapper checkMapper;
	@Autowired
	private ClassStudentMapper classStudentMapper;
	
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
	
	/**
	 * 修改签到状态
	 * @param attendanceChanged
	 * @return
	 */
	@RequestMapping(value="/changeattendance")
	public String changeAttendance(@RequestParam String attendanceChanged){
		String returnStr = "";
		
		if(attendanceChanged != null && !attendanceChanged.equals("")){
			
			AttendanceEntity attendanceEntity = new AttendanceEntity();
			AttendanceEntity attendChanged = new AttendanceEntity();
			Gson gson = new Gson();
			
			attendanceEntity = gson.fromJson(attendanceChanged, AttendanceEntity.class);
			
			attendChanged = attendanceMapper.getAttendanceByAttendanceId(
					attendanceEntity.getAttendanceId());
			attendChanged.setAttendanceValid(attendanceEntity.isAttendanceValid());
			
			Timestamp time= new Timestamp(System.currentTimeMillis());
			attendChanged.setAttendanceTime(time);
			
			if(attendanceMapper.updateAttendanceIncludeTime(attendChanged) > 0){
				returnStr = "OK";
			}
			
		}
		
		return returnStr;
	}
	
	/**
	 * 下载考勤记录文件
	 * @param response
	 * @param teacherId
	 * @param classId
	 */
	@RequestMapping(value="/download*",method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response,
			@RequestParam("teacherid") String teacherId,
			@RequestParam("classid") String classId){
		
		System.out.println("Downloading...");
		
		ClassEntity classEntity = new ClassEntity();
		classEntity = classMapper.getaClass(classId);
		String fileName = classEntity.getClassName() + "课程考勤记录.xlsx";
		
		String filePath = outputFile(teacherId,classId,classEntity,fileName);
		File file = new File(filePath);
		
		if(file.exists()){
			response.setContentType("multipart/form-data;charset=UTF-8");
			// 设置文件名
			try {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			response.addHeader("Content-Disposition", "attachment;fileName=" + 
					fileName);
			response.setCharacterEncoding("UTF-8");
			
			byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            
            try{
            	fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                
                int len;
                while ((len = bis.read(buffer)) != -1){
                	os.write(buffer, 0, len);
                }
            }catch(Exception e){
            	e.printStackTrace();
            }finally{
            	try{
            		if(bis != null){
            			bis.close();
            		}
            		if(fis != null){
            			fis.close();
            		}
            	}catch(Exception e){
                	e.printStackTrace();
            	}
            }
		}
	}
	
	/**
	 * 生成考勤记录excel文件
	 * @param teacherId
	 * @param classId
	 * @return
	 */
	public String outputFile(String teacherId,String classId,ClassEntity classEntity,String fileName){
		String filePath = "";
		
		
		if(classEntity != null){
			
			
			if(teacherId.equals(classEntity.getClassFounderId())){
				filePath = "/"+fileName;
				File file = new File(filePath);
				
				if(file.exists()){
					file.delete();
					try{
						file.createNewFile();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				//创建xlsx文件
				XSSFWorkbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("0");
				Row row = sheet.createRow(0);
				
				//绘制表头
				row.createCell(0).setCellValue("学号");
				row.createCell(1).setCellValue("姓名");
				
				List<CheckEntity> checkList = checkMapper.
						getCheckByClassIdAndTeacherId(classId, teacherId);
				
				int index = 2;
				for(CheckEntity checkEntity : checkList){
					switch (checkEntity.getCheckKind()){
					case 0:
						row.createCell(index).setCellValue(checkEntity.getCheckTime() + 
								"随机数字考勤");
						break;
					case 1:
						row.createCell(index).setCellValue(checkEntity.getCheckTime() + 
								"二维码考勤");
						break;
					}
					
					index++;
				}
				
				
				//填写数据
				List<String> studentIdList = classStudentMapper.getStudents(classId);
				index = 1;
				for(String studentId : studentIdList){
					
					StudentEntity studentEntity = new StudentEntity();
					studentEntity = studentMapper.getStudent(studentId);
					
					row = sheet.createRow(index);
					
					row.createCell(0).setCellValue(studentEntity.getStudentNumber());
					row.createCell(1).setCellValue(studentEntity.getStudentName());
					
					int index1 = 2;
					for(CheckEntity checkEntity : checkList){
						AttendanceEntity attendanceEntity = new AttendanceEntity();
						attendanceEntity = attendanceMapper.
								getAttendanceByCheckIdStudentId(checkEntity.getCheckId(), studentId);
						
						if(attendanceEntity.isAttendanceValid()){
							row.createCell(index1).setCellValue("到课");
						}else{
							row.createCell(index1).setCellValue("旷课");
						}
					}
					
					index++;
				}
				
				workbook.setSheetName(0, "考勤记录");
				
		        try{
		        	FileOutputStream fileoutputStream = new FileOutputStream(file);
		        	workbook.write(fileoutputStream);
		        	fileoutputStream.close();
		        	workbook.close();
		        }catch(Exception e){
		        	e.printStackTrace(); 
		        }
			}
		}else{
			//输出报错文件
			System.out.println("null error!");
		}
		
		
		return filePath;
	}

}
