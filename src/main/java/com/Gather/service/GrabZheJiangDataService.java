package com.Gather.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Gather.ehtity.GrabZheJiangDataInfo;
import com.Gather.util.DBConnection;
import com.Gather.util.HtmlUnitUtil;
import com.Gather.util.JsonUtil;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

@Service
public class GrabZheJiangDataService {
	
	private Logger logger= LoggerFactory.getLogger(GrabZheJiangDataService.class);
	
//	@Resource
//    private GrabZheJiangDataDao grabZheJiangDataDao;
	
	/**
	 * 跟据区域代码获取信息
	 * @param qydm
	 * @return
	 */
	public List<GrabZheJiangDataInfo> getBataByQydm(String qydm) {
		List<GrabZheJiangDataInfo> list = new ArrayList<GrabZheJiangDataInfo>();
		WebClient webClient = null;
		try {
			webClient = HtmlUnitUtil.getWebClient();
			
//			webClient.getPage("http://tzxm.zjzwfw.gov.cn/?webId=1&tabid=00001");
			
			String url = "";
			if(qydm != null && !qydm.equals("")) {
				url = "http://tzxm.zjzwfw.gov.cn/homeIndex/projectItemByCityId?id="
						+ qydm;
			}else {
				url = "http://tzxm.zjzwfw.gov.cn/homeIndex/projectItemApproval";
			}
			
			//请求列表
			String response = new HtmlUnitUtil().webRequestByGet(url,webClient);
			if(!response.equals("[]")) {
				list = JsonUtil.jsonToList(response.replace("\\\"", "'"), GrabZheJiangDataInfo.class);
				Iterator<GrabZheJiangDataInfo> iter = list.iterator();  
				while (iter.hasNext()) {  
					GrabZheJiangDataInfo info = iter.next();  
					String xmdm = info.getItemId();
					if(this.isExist(xmdm)) {
						iter.remove();  
					}else {
						String enterpriseName = this.approvalDetail(info.getProjectId(), info.getApprovalItemId(), webClient);
						info.setEnterpriseName(enterpriseName);
						info.setZcrq(this.getCurrentTime());
					}
		            	
		        }
				
//				for (int i = 0; i < list.size(); i++) {
//					GrabZheJiangDataInfo info = list.get(i);
//					String xmdm = info.getItemId();
//					if(this.isExist(xmdm)) {
//						list.remove(info);
//					}else {
//						String enterpriseName = this.approvalDetail(info.getProjectId(), info.getApprovalItemId(), webClient);
//						info.setEnterpriseName(enterpriseName);
//						info.setZcrq(this.getCurrentTime());
//					}
//				}
				this.insertEntity(list);
			}
		} catch (Exception e) {
			logger.error("获取数据失败",e);
		}finally {
			if(webClient != null) {
				webClient.close();
			}
		}
		return list;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public boolean doDelete(String id)  {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DBConnection.getConnect("reckon");
			statement = connection.createStatement();
			String deleteTableSQL = "DELETE FROM xmbqxx WHERE xmbadm = '"+id+"'";
			statement.execute(deleteTableSQL);
			connection.commit();
			return true;
		} catch (SQLException e) {
			logger.error("删除失败，xmbadm="+ id,e);
			return false;
		}finally {
            if (statement != null) {
                try {
					statement.close();
				} catch (SQLException e) {
					logger.error("关闭数据库失败",e);
				}
            }
            if (connection != null) {
            	try {
					connection.close();
				} catch (SQLException e) {
					logger.error("关闭数据库失败",e);
				}
            }
 
        }
		
	}
	
	/**
	 * 获取项目法人单位
	 * @param projectId
	 * @param approvalItemId
	 * @param webClient
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	private String approvalDetail(String projectId,String approvalItemId,WebClient webClient) throws Exception {
		String url = "http://tzxm.zjzwfw.gov.cn/homeIndex/projectItemApprovalReprint?projectId="
				+ projectId
				+ "&approvalItemId="
				+ approvalItemId;
		String response = new HtmlUnitUtil().webRequestByGet(url, webClient).replace("\\\"", "'");
		
		Object obj = JsonUtil.getJsonValue1(response.replace("\"", ""), "enterpriseName");
		String enterpriseName = "";
		if(obj != null) {
			enterpriseName = obj.toString();
		}
		return enterpriseName;
	}
	
	/**
	 * 获取系统当前时间
	 * @return
	 */
	private String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		// new Date()为获取当前系统时间
		return df.format(new Date());
	}
	
	/**
	 * 根据项目代码查询是否有该条信息
	 * @param xmbadm
	 * @return
	 * @throws SQLException 
	 */
	private Boolean isExist(String xmbadm) throws SQLException {
//		DBConnection connection = new DBConnection("reckon");  
		ResultSet rs = DBConnection.selectQuery("reckon", "select * from xmbqxx where xmbadm = '"+xmbadm+"'");
		Boolean flag = false;
		if(rs != null) {
			int count = 0;  
			while(rs.next()) {  
				count++;  
			}   
			if(count > 0) {
				flag = true;
			}
		}
		DBConnection.closeConn();
		return flag;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 * @throws SQLException 
	 */
	private void insertEntity(List<GrabZheJiangDataInfo> list) throws SQLException {
		String sql = "insert into xmbqxx(xmbadm, xmmc, xmfrdw,spsx, glbm, blzd, blrq, zcrq)"
				+ "values (?,?,?,?,?,?,?,?)";

		Connection connection = DBConnection.getConnect("reckon");

		PreparedStatement pstmt = connection.prepareStatement(sql);

		//每次提交最大条数

		final int batchSize = 5000;

		int count = 0;

		for (GrabZheJiangDataInfo item: list) {

		    pstmt.setString(1, item.getItemId());
		    pstmt.setString(2, item.getSpareE());
		    pstmt.setString(3, item.getEnterpriseName());
		    pstmt.setString(4, item.getItemName());
		    pstmt.setString(5, item.getDeptName());
		    pstmt.setString(6, item.getSpareB());
		    pstmt.setString(7, item.getSpareA());
		    pstmt.setString(8, item.getZcrq());

		    pstmt.addBatch();

		    if(++count % batchSize == 0) {

		       pstmt.executeBatch();

		    }

		}

		//提交剩余的数据

		pstmt.executeBatch(); 
		connection.commit();
		pstmt.close();

		connection.close();
	}
	
}
