package com.sas.util;

import com.sas.controller.AttendanceController;

public class FilePathGetter {
	
	public static String getFilePath(String fileName){
		String filePath = "";
		filePath = AttendanceController.class.getClassLoader()
				.getResource("../../fileofTeacherCheck/"+fileName).getPath();
		return filePath;
	}

}
