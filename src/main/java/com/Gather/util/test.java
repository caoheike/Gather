package com.Gather.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;





public class test {
	static Connection connect = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.hxtt.sql.access.AccessDriver");// 注册驱动
		// Access中的数据库默认编码为GBK，本地项目为UTF-8，若不转码会出现乱码
		Properties p = new Properties();
		p.put("charSet", "GBK");
	     connect = DriverManager.getConnection("Jdbc:Access:///D:/qytj/datebase/#cn-enet#.mdb","","");  
		if (connect != null) {
			System.out.println(connect + "\n连接成功");
		} else {
			System.out.println("连接失败");
		}
		rs = DBConnection.selectQuery("reckon", "select * from xzqydm");
		JSONObject jsonObject=new JSONObject();  //最大的key
		String jsonstr = null;
		if (rs != null) {
//			JSONObject jsonObject=new JSONObject(); //最大的key
			JSONArray arrays=new JSONArray();

//			jsonObject1.put("province_id","1");
//			jsonObject1.put("province_name", EncodeUtil.stringToUnicode("浙江"));
//			arrays.add(jsonObject1);
			JSONArray arrays2=new JSONArray();
			JSONArray arrays3=new JSONArray();
			while (rs.next()) {
				String var1=rs.getString(1);//代码
				String var2=rs.getString(2);//名字
				String var3=rs.getString(3);//类型
				String var4=rs.getString(4);//无用
				String var5=rs.getString(5);//父id
				
				
					if(var3==null||var3.equals("")) {
						
					}else if(var3.contains("市")) {
						JSONObject jsonObject3=new JSONObject();//放入区
						jsonObject3.put("city_id",var1 );
						jsonObject3.put("zip_code", "03401");
						jsonObject3.put("province_id",1);//父级
						jsonObject3.put("city_name",var2);
						arrays2.add(jsonObject3);
						jsonObject.put("city",arrays2 );
					
					}else if(var3.contains("区")||var3.contains("县")) {
						
						JSONObject jsonObject3=new JSONObject();//放入区
						jsonObject3.put("district_id",var1 );
						jsonObject3.put("city_id",var5);//父级
						jsonObject3.put("district_name",var2 );
						arrays3.add(jsonObject3);
						jsonObject.put("district",arrays3);
						
					}
						//else if(var3.contains("县")) {
//						JSONObject jsonObject3=new JSONObject();//放入区
//						jsonObject3.put("city_id",var1 );
//						jsonObject3.put("zip_code", "03401");
//						jsonObject3.put("province_id",var5);//父级
//						jsonObject3.put("city_name",EncodeUtil.stringToUnicode(var2) );
//						arrays2.add(jsonObject3);
//						jsonObject.put("district",arrays2 );
//					}
				
			}
			jsonObject.put("province",arrays );
			System.out.println(jsonObject);
	}
	}
}
