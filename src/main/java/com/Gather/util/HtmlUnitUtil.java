package com.Gather.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnitUtil {
	
	/**
	 * 获取webClient
	 * @return
	 */
	public static WebClient  getWebClient(){
		return new WebClientFactory().getWebClient();
	}
	
	/**
	 * 关闭webClient
	 */
	public static void closeWebClient(WebClient webClient){
		if(webClient != null){
			webClient.close();
		}
	}
	
	
	/**
	 * 保存验证码图片,返回图片本地存储绝对路径+图片名
	 * @param htmlImg 图片流
	 * @param prefix  图片名称前缀,如滨州社保可写为bz
	 * @param verifyImagesPath 图片保存的绝对路径
	 * @param suffix 图片名称后缀 如png
	 * @return
	 * @throws IOException
	 */
	public static String saveImg(HtmlImage htmlImg,String prefix, String verifyImagesPath,String suffix) throws IOException{
	  
		File file = new File(verifyImagesPath + File.separator);
       	if (!file.exists()) {
       		file.mkdir();
       	}
	   	ImageReader imgReader = htmlImg.getImageReader();
	    BufferedImage bufferedImage  = ImageIO.read((ImageInputStream)imgReader.getInput());
		String fileName = prefix + System.currentTimeMillis()+"."+suffix;
		ImageIO.write(bufferedImage, suffix, new File(file,fileName));
	   	String filePath = verifyImagesPath + File.separator + fileName;
		return filePath;
	}
	/**
	 * 保存验证码图片,返回浏览器可访问到图片的地址
	 * @param htmlImg 图片流
	 * @param prefix  图片名称前缀
	 * @param verifyImagesPath 图片在项目中的相对路径 如：/verifyImages
	 * @param suffix 图片名称后缀 如png
	 * @param request 
	 * @return
	 * @throws IOException
	 */
	public static String saveImg(HtmlImage htmlImg,String prefix, String verifyImagesPath,String suffix,HttpServletRequest request) throws IOException{
	    String verifyImages = request.getSession().getServletContext().getRealPath(verifyImagesPath);
		File file = new File(verifyImages + File.separator);
		if (!file.exists()) {
			file.mkdir();
		}
		ImageReader imgReader = htmlImg.getImageReader();
		BufferedImage bufferedImage  = ImageIO.read((ImageInputStream)imgReader.getInput());
		String fileName = prefix + System.currentTimeMillis()+"."+suffix;
		ImageIO.write(bufferedImage, suffix, new File(file,fileName));

		String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + verifyImagesPath + "/" + fileName;
		return filePath;
	}
	
	
	/**
	 * 根据参数获取请求结果
	 * @param url 请求地址
	 * @param list 请求参数
	 * @param webClient 请求的webClient
	 * @return 请求结果
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	public String webRequestByPost(String url,List<NameValuePair> list,WebClient webClient) throws FailingHttpStatusCodeException, IOException{
		WebRequest webRequest = new WebRequest(new URL(url));
		
		webRequest.setRequestParameters(list);
		webRequest.setHttpMethod(HttpMethod.POST);
		
		HtmlPage page =	webClient.getPage(webRequest);
		String response = page.getWebResponse().getContentAsString();
		
		return response;
	}
	
	/**
	 * 根据参数获取请求结果
	 * @param url 请求地址
	 * @param webClient 请求的webClient
	 * @return 请求结果
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	public String webRequestByGet(String url,WebClient webClient) throws Exception{
		WebRequest webRequest = new WebRequest(new URL(url));
		
		webRequest.setHttpMethod(HttpMethod.GET);
		
		HtmlPage page =	webClient.getPage(webRequest);
		String response = page.getWebResponse().getContentAsString();
		
		return response;
	}
}
