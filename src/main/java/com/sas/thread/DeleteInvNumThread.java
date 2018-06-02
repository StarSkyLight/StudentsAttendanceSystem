package com.sas.thread;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.entity.TempClassINEntity;
import com.sas.mapper.TempClassInviNumMapper;

@Component
public class DeleteInvNumThread{
	@Autowired
	private TempClassInviNumMapper tempClassInviNumMapper;
	
	public void execute(){
		new DeleteInvNum().start();
	}
	
	
	
	public class DeleteInvNum extends Thread {
		
		
		
		@Override
		public void run(){
			while(true){
				try{
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					/*
					 * 线程休眠15分钟
					 */
					Thread.sleep(15*60*1000);
					
					List<TempClassINEntity> invNumList = new ArrayList<TempClassINEntity>();
					//查出所有课程邀请码
					invNumList = DeleteInvNumThread.this.tempClassInviNumMapper
							.getAllTimestamp();
					/**
					 * 遍历课程邀请码
					 * 如果是15分钟之前产生的，删除之
					 */
					for(TempClassINEntity tempClassINEntity : invNumList){
						if(tempClassINEntity.getTimeStamp().before(timestamp)){
							DeleteInvNumThread.this.tempClassInviNumMapper
							.deleteInvNum(tempClassINEntity.getClassId());
						}
					}
					
					System.out.println("清除过期课程邀请码");
					
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
		}

	}
}


