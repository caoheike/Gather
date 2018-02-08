package com.Gather.Contorller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Gather.ehtity.GrabZheJiangDataInfo;
import com.Gather.service.GrabZheJiangDataService;

@Controller
@RequestMapping("doData")
public class GrabZheJiangDataController {
	
    @Resource
    private GrabZheJiangDataService service;
	/**
     * 根据行政区域代码
     *
     * @param request
     * @param qydm
     * @return
     */
    @RequestMapping(value = "doDetail", method = RequestMethod.GET)
    @ResponseBody
    public String getBataByQydm(HttpServletRequest request, String qydm, Model model) {
    	List<GrabZheJiangDataInfo> list = service.getBataByQydm(qydm);
    	model.addAttribute("list", list);
    	model.addAttribute("total", list.size());
        return "";
    }
}
