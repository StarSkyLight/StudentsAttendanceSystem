package com.sas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sas.mapper")
public class StudentsAttendanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsAttendanceSystemApplication.class, args);
	}
}
