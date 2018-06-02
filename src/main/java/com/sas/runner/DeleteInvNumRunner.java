package com.sas.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sas.thread.DeleteInvNumThread;

@Component
public class DeleteInvNumRunner implements ApplicationRunner {
	
	@Autowired
	DeleteInvNumThread deleteInvNumThread;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		deleteInvNumThread.execute();
	}

}
