package com.Gather.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Gather.ehtity.GrabZheJiangDataInfo;
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
				for (GrabZheJiangDataInfo info : list) {
					
					String enterpriseName = this.approvalDetail(info.getProjectId(), info.getApprovalItemId(), webClient);
					info.setEnterpriseName(enterpriseName);
					info.setZcrq(this.getCurrentTime());
//				String xmdm = info.getItemId();
//				List<GrabDomainBasicInfo> infos = grabZheJiangDataDao.getByXmdm(xmdm);
//				if(infos != null && infos.size() > 0) {
//					list.remove(info);
//				}else {
//					info.setEnterpriseName(this.approvalDetail(info.getProjectId(), info.getApprovalItemId(), webClient));
//				}
				}
				System.out.println(list);
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
	
	
	private String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		// new Date()为获取当前系统时间
		return df.format(new Date());
	}
}
