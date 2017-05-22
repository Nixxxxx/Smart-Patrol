package com.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Device;
import com.entity.PageBean;
import com.service.DeviceService;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/device")
public class DeviceAction {
	
	@Autowired
	private DeviceService deviceService;

	private String msg;
	private boolean success;
	private JSONObject resultJson=new JSONObject();
	
	
	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@RequestMapping(value="/insert")
	public void insert(HttpServletRequest request,HttpServletResponse response){
		String number=request.getParameter("number");
		if(checkNumber(number)){
			String name=request.getParameter("name");
			String extra=request.getParameter("extra");
			Device device=new Device(number,name,extra);
			success=deviceService.save(device);
			if(success)
				msg="添加成功";
			else msg="添加失败";
		}else{
			success=false;
			msg="编号已存在";
		}
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}

	@RequestMapping(value="/update")
	public void update(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		String number=request.getParameter("number");
		if(checkNumber(number)){
			String name=request.getParameter("name");
			String extra=request.getParameter("extra");
			Device device=new Device(id,number,name,extra);
			success=deviceService.update(device);
			if(success)
				msg="更新成功";
			else msg="更新失败";
		}else{
			success=false;
			msg="编号已存在";
		}
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	@RequestMapping(value="/del")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		success=deviceService.delete(id);
		if(success)
			msg="删除成功";
		else msg="删除失败";
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	public boolean checkNumber(String number){
		List<Device> devices=deviceService.findAll();
		for(Device device:devices){
			if(number.equals(device.getNumber()))
				return false;
		}
		return true;
	}
	
	@RequestMapping(value="/showAdd")
	public ModelAndView showAdd(HttpServletRequest request){
		return new ModelAndView("/device/add");
	}
	
	@RequestMapping(value="/showList")
	public ModelAndView showList(Device s_device,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/device/list");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}else{
//			s_device=(Device) session.getAttribute("s_device");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		List<Device> deviceList=deviceService.find(pageBean, s_device);
		int total=deviceService.findAll().size();
		String pageCode=PageUtil.rootPageTion("/Xungeng/device/showList",total, pageBean.getPage(),pageBean.getPageSize(),null,null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("deviceList", deviceList);
		return mav;
	}
}
