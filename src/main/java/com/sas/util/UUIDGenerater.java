package com.sas.util;

import java.util.UUID;

/**
 * uuid生成器
 * @author ziyi
 *
 */
public class UUIDGenerater {
	
	public static String getUUID(){
        UUID uuid = UUID.randomUUID();

        String a = uuid.toString();
        a = a.toUpperCase();
        a = a.replaceAll("-","");
        return a;
    }

}
