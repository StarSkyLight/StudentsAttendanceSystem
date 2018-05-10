package com.sas.util;

public class DistanceCalculator {
	/**
	 * 计算结果单位为1米
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static Double getDistance(double lat1,double lng1,   
            double lat2,double lng2){
		final double EARTH_RADIUS = 6378.137;
		
		double radLat1 = lat1 * Math.PI / 180.0;;  
        double radLat2 = lat2 * Math.PI / 180.0;;  
        double a = radLat1 - radLat2;  
        double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);  
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
                + Math.cos(radLat1) * Math.cos(radLat2)  
                * Math.pow(Math.sin(b / 2), 2)));  
        s = s * EARTH_RADIUS;  
        s = Math.round(s * 10000)/10;  
        
        return s;
	}

}
