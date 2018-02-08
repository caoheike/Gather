package com.Gather.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.Gather.ehtity.GrabZheJiangDataInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	/**
	 * 返回json字符串中某个key的值
	 * @param str json字符串
	 * @param key 
	 * @return
	 */
	public static String getJsonValue(String str,String key){
			JSONObject jsonObject = JSONObject.fromObject(str);
			Iterator iterator = jsonObject.keys();
			String value = "";
			while(iterator.hasNext()){
		        	String key1 = (String) iterator.next();
		        	String value1 = jsonObject.getString(key1);
		        	if(key1.equals(key)){
		        		value = value1;
		        		break;
		        	}else{
		        		if(isGoodJson(value1)){
		        			value = getJsonValue(value1, key);
		        		}
		        	}
			}
			
			return value;
	}
	
	
	
	/**
	 * 返回json中某个key的值
	 * @param str json字符串
	 * @param key 
	 * @return
	 */
	public static Object getJsonValue1(Object str,String key){
		JSONObject jsonObject = JSONObject.fromObject(str);
		Iterator iterator = jsonObject.keys();
		Object value = new Object();
		while(iterator.hasNext()){
			Object key1 = iterator.next();
			Object value1 = jsonObject.get(key1);
			if(key1.equals(key)){
				value = value1;
				break;
			}else{
				if(isGoodJson(value1)){
					value = getJsonValue1(value1, key);
				}
			}
		}
		
		return value;
	}
	
	
	
	
	/**
	 * 判断Object是否为json格式
	 * @param json
	 * @return
	 */
	public static boolean isGoodJson(Object json) {  
		if(json == null || (json + "").equals("null")){
			return false;
		}
		try {  
			JSONObject.fromObject(json);
			return true;  
		} catch (Exception e) {  
			return false;  
		}  
	} 
	
	
	
	
	/**
	 * 判断字符串是否为json格式
	 * @param json
	 * @return
	 */
    public static boolean isGoodJson(String json) {  
        if (StringUtils.isBlank(json)) {  
            return false;  
        }  
        try {  
        	JSONObject.fromObject(json);
            return true;  
        } catch (Exception e) {  
            return false;  
        }  
    }  
    /**
     * 将jsonArray转为数组
     * @param jsonString
     * @param pojoCalss
     * @return
     */
    @SuppressWarnings("unchecked")  
	public static <T> List<T> jsonToList(String jsonString, Class<T> pojoCalss) {  
    	List<T> list = new ArrayList<T>(16);
		try{  
//		  JSONObject str = JSONObject.fromObject(jsonString);
		  JSONArray array = JSONArray.fromObject(jsonString.replace("\"", ""));
		  for (int i = 0; i < array.size(); i++) {
			  JSONObject obj = array.getJSONObject(i);
			  Object pojo = net.sf.json.JSONObject.toBean(obj, pojoCalss);  
			  list.add((T)pojo);  
		  }
		}catch(Exception ex){  
		  ex.printStackTrace();  
		} 
		return list;
    }
    
    @SuppressWarnings("unchecked")  
	public static <T> T jsonToObject(JSONObject obj, Class<T> pojoCalss) {  
		try{  
		  Object pojo;  
		  pojo = net.sf.json.JSONObject.toBean(obj, pojoCalss);  
		  return (T)pojo;  
		}catch(Exception ex){  
		  ex.printStackTrace();  
		   return null;
		} 
    }
    
    public static void main(String[] args) {
		String str = "\"[{'approvalItemId':'330104351801101000006','projectId':'1C265OK1I2I8DD77B2760000D16D3F88','itemId':'2017-330104-52-03-087708-000','itemName':'危险化学品建设项目安全条件审查','deptName':'江干区安全生产监督管理局','spareA':'2018-02-06','spareB':'办结','spareE':'杭州杭石大塘加油站双层罐改造项目'},{'approvalItemId':'330104351801101000008','projectId':'1C265OK1I2I8DD77B2760000D16D3F88','itemId':'2017-330104-52-03-087708-000','itemName':'生产、储存危险化学品的建设项目安全设施设计审查','deptName':'江干区安全生产监督管理局','spareA':'2018-02-06','spareB':'办结','spareE':'杭州杭石大塘加油站双层罐改造项目'},{'approvalItemId':'330104741802011000004','projectId':'8B9AD049B7C111E7ACB7008CFAE57B00','itemId':'2017-330104-70-03-065666-000','itemName':'民用建筑项目节能审查','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-02-05','spareB':'办结','spareE':'杭政储出[2017]68号地块商品住宅（设配套公建）'},{'approvalItemId':'330104741801241000001','projectId':'1C4GC2CO49BIDD77B276000079BC0B19','itemId':'2018-330104-82-01-005639-000','itemName':'民用建筑项目节能审查','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-02-05','spareB':'办结','spareE':'江干区牛田单元R22-02地块中学'},{'approvalItemId':'330104741801171000002','projectId':'9A7A79F96E7E11E6ACB7008CFAE57B00','itemId':'2016-330104-48-01-017524-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-02-05','spareB':'办理中','spareE':'红五月路（建华路-红建路）道路工程'},{'approvalItemId':'330104741801161000008','projectId':'C57984B7475B11E7ACB7008CFAE57B00','itemId':'2017-330104-47-03-025883-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-02-05','spareB':'办理中','spareE':'杭州市笕桥镇弄口社区经济联合社商业综合用房'},{'approvalItemId':'330104741801291000001','projectId':'86233CCAF8E611E6ACB7008CFAE57B00','itemId':'2017-330104-70-03-004583-000','itemName':'应建防空地下室的民用建筑项目报建审批','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-02-02','spareB':'办结','spareE':'杭政储出[2016]40号地块商业商务用房'},{'approvalItemId':'330101661801308000131','projectId':'ED84FAC21E6A11E7ACB7008CFAE57B00','itemId':'2017-330104-70-03-013564-000','itemName':'建设工程消防设计备案','deptName':'江干区公安消防大队','spareA':'2018-01-30','spareB':'办理中','spareE':'杭政储出（2016）37号地块商品住宅兼容商业商务用房'},{'approvalItemId':'330101661801268000104','projectId':'1C4O7B1LB342DD77B27600005C74A922','itemId':'2018-330104-47-01-006428-000','itemName':'建设工程竣工验收消防备案','deptName':'江干区公安消防大队','spareA':'2018-01-26','spareB':'办理中','spareE':'九福路公交中心站'},{'approvalItemId':'330104061801251000001','projectId':'032F990A75A311E7ACB7008CFAE57B00','itemId':'2017-330104-48-01-041842-000','itemName':'政府投资项目可行性研究报告审批','deptName':'区发改局（区物价局）','spareA':'2018-01-25','spareB':'办结','spareE':'新塘支路（兴建路—红建路）道路工程'},{'approvalItemId':'330104741801181000001','projectId':'3797BCDB8D4F11E7ACB7008CFAE57B00','itemId':'2017-330104-54-01-050376-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-22','spareB':'办理中','spareE':'杭州市皋亭单元JG0107-01地块公共停车场'},{'approvalItemId':'330104741801161000006','projectId':'1BVEJ4TF538FDD77B27600001F88BD1C','itemId':'2017-330104-48-01-075860-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-22','spareB':'办理中','spareE':'杭州国际商贸城单元三卫路（红普路-九华路）道路工程'},{'approvalItemId':'330104741801161000005','projectId':'62D12E3C9A6011E6ACB7008CFAE57B00','itemId':'2016-330100-82-01-024177-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-22','spareB':'办理中','spareE':'长睦单元R22-10地块中学'},{'approvalItemId':'330104741801161000007','projectId':'4243F7EBBD1111E6ACB7008CFAE57B00','itemId':'2016-330104-70-03-032299-000','itemName':'应建防空地下室的民用建筑项目报建审批','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-18','spareB':'办结','spareE':'杭政储出（2013）95号地块商业商务用房'},{'approvalItemId':'330101661801178000027','projectId':'1C3V21PIK2VCDD77B2760000CCCF4CB3','itemId':'2018-330104-47-01-003877-000','itemName':'建设工程竣工验收消防备案','deptName':'江干区公安消防大队','spareA':'2018-01-17','spareB':'办理中','spareE':'长睦单元R21-12A地块经济适用房项目'},{'approvalItemId':'330104061801151000001','projectId':'1C32OEMUD2AFDD77B27600004EAB31C2','itemId':'2018-330104-47-01-001828-000','itemName':'政府投资项目项目建议书审批','deptName':'区发改局（区物价局）','spareA':'2018-01-16','spareB':'办理中','spareE':'凯旋单元G12-122地块公园绿地工程'},{'approvalItemId':'330104741712111000005','projectId':'ED84FAC21E6A11E7ACB7008CFAE57B00','itemId':'2017-330104-70-03-013564-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-16','spareB':'办结','spareE':'杭政储出（2016）37号地块商品住宅兼容商业商务用房'},{'approvalItemId':'330104741712051000001','projectId':'4089768F606511E7ACB7008CFAE57B00','itemId':'2017-330104-70-03-034033-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-16','spareB':'办结','spareE':'杭政储出（2017）22号地块商品住宅（设配套公建）'},{'approvalItemId':'330104741712141000007','projectId':'EE417CAB138511E7ACB7008CFAE57B00','itemId':'2017-330104-47-01-010765-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-16','spareB':'办结','spareE':'牛田单元R22-12地块居住区配套公共服务设施'},{'approvalItemId':'330104741712111000004','projectId':'60C6B6561B4011E7ACB7008CFAE57B00','itemId':'2017-330104-70-03-012829-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-16','spareB':'办结','spareE':'杭政储出【2016】36号地块住宅'},{'approvalItemId':'330104741712051000002','projectId':'E9A6DF7E676C11E7ACB7008CFAE57B00','itemId':'2017-330104-48-03-036345-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-16','spareB':'办结','spareE':'杭政储出[2016]3号地块加油站项目'},{'approvalItemId':'330104061801121000001','projectId':'1C0851GG02F0DD77B2760000AF931C86','itemId':'2017-330104-47-01-079297-000','itemName':'政府投资项目可行性研究报告审批','deptName':'区发改局（区物价局）','spareA':'2018-01-16','spareB':'办理中','spareE':'笕桥生态公园单元JG0701-R21-01地块黎明社区拆迁安置房'},{'approvalItemId':'330104741712261000002','projectId':'1C1U4IHUI0CFDD77B27600006692D15A','itemId':'2017-330104-82-01-087800-000','itemName':'房屋建筑和市政基础设施工程竣工验收备案','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-15','spareB':'办结','spareE':'丁桥单元R22-02地块幼儿园'},{'approvalItemId':'330104741801051000007','projectId':'8476B470B23111E7ACB7008CFAE57B00','itemId':'2017-330104-70-03-063391-000','itemName':'民用建筑项目节能审查','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-15','spareB':'办结','spareE':'杭政储出（2017）36号地块商业商务用房'},{'approvalItemId':'330104741801031000001','projectId':'1C2SKFF1DB7TDD77B2760000FD4BBC9B','itemId':'2018-330104-47-01-000519-000','itemName':'房屋建筑和市政基础设施工程竣工验收备案','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-15','spareB':'办结','spareE':'彭埠单元R21-12地块农转非居民拆迁安置房工程'},{'approvalItemId':'330104741712261000001','projectId':'1C1U3VFO30B7DD77B276000032485BB7','itemId':'2017-330104-82-01-087801-000','itemName':'房屋建筑和市政基础设施工程竣工验收备案','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-15','spareB':'办结','spareE':'丁桥单元R22-01地块小学'},{'approvalItemId':'330104741801051000005','projectId':'1C321C6Q61AIDD77B2760000665F180B','itemId':'2018-330104-70-03-001139-000','itemName':'房屋建筑和市政基础设施工程竣工验收备案','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-12','spareB':'办结','spareE':'杭政储出[2015]3号地块商品住宅（设配套公建）'},{'approvalItemId':'330104741801051000006','projectId':'93867F114A9611E7ACB7008CFAE57B00','itemId':'2017-330104-47-01-026671-000','itemName':'建筑工程施工许可证核发','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-12','spareB':'办理中','spareE':'江干科技园JG1505-12地块公共服务设施中心'},{'approvalItemId':'330104741711071000003','projectId':'DB2D5776555411E6ACB7008CFAE57B00','itemId':'2016-330104-48-01-012927-000','itemName':'$建筑工程施工许可','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-11','spareB':'办理中','spareE':'双凉环路（南都路-南都环路）道路工程项目'},{'approvalItemId':'3.30104741801091E20','projectId':'1C3AFHHKP40ADD77B2760000F2FDEE79','itemId':'2018-330104-70-03-001860-000','itemName':'房屋建筑和市政基础设施工程竣工验收备案','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-10','spareB':'办结','spareE':'杭政储出【2015】4号地块商品住宅(设配套公建)'},{'approvalItemId':'330104741801091000001','projectId':'1C3AFHHKP40ADD77B2760000F2FDEE79','itemId':'2018-330104-70-03-001860-000','itemName':'房屋建筑和市政基础设施工程竣工验收备案','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-10','spareB':'办结','spareE':'杭政储出【2015】4号地块商品住宅(设配套公建)'},{'approvalItemId':'330104741801051000001','projectId':'DB241A8CA55611E6ACB7008CFAE57B00','itemId':'2016-330104-47-02-024664-000','itemName':'民用建筑项目节能审查','deptName':'区住建局（区人防办、区民防局）','spareA':'2018-01-10','spareB':'办结','spareE':'浙江智慧高速业务用房建设工程'}]\"";
		System.out.println(jsonToList(str.replace("\"", ""), GrabZheJiangDataInfo.class).size());;
	}
    
}
