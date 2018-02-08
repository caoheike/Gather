package com.Gather.util;

import java.io.UnsupportedEncodingException;

public class EncodeUtil {
	/** 
	 * 获取字符串的unicode编码 
	 * 汉字“木”的Unicode 码点为Ox6728 
	 * 
	 * @param s 木 
	 * @return \ufeff\u6728  \ufeff控制字符 用来表示「字节次序标记（Byte Order Mark）」不占用宽度 
	 * 在java中一个char是采用unicode存储的 占用2个字节 比如 汉字木 就是 Ox6728 4bit+4bit+4bit+4bit=2字节 
	 */  
	public static String stringToUnicode(String s) {  
	    try {  
	        StringBuffer out = new StringBuffer("");  
	        //直接获取字符串的unicode二进制  
	        byte[] bytes = s.getBytes("unicode");  
	        //然后将其byte转换成对应的16进制表示即可  
	        for (int i = 0; i < bytes.length - 1; i += 2) {  
	            out.append("\\u");  
	            String str = Integer.toHexString(bytes[i + 1] & 0xff);  
	            for (int j = str.length(); j < 2; j++) {  
	                out.append("0");  
	            }  
	            String str1 = Integer.toHexString(bytes[i] & 0xff);  
	            out.append(str1);  
	            out.append(str);  
	        }  
	        return out.toString();  
	    } catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	}  
}
