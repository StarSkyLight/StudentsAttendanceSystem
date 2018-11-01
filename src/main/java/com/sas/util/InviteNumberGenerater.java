package com.sas.util;

public class InviteNumberGenerater {
	public static String getInviteNumber() {
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[6]; 
        for (int i = 0; i < rands.length; i++) 
        { 
            int rand = (int) (Math.random() * a.length()); 
            rands[i] = a.charAt(rand); 
        } 
        return String.valueOf(rands).toUpperCase();
    }

}
