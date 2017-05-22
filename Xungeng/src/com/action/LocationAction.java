package com.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Location;
import com.entity.PageBean;
import com.service.LocationService;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/location")
public class LocationAction {
	
	@Autowired
	private LocationService locationService;

	public LocationService getLocationService() {
		return locationService;
	}

	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	private String msg;
	private boolean success;
	private JSONObject resultJson=new JSONObject();


	@RequestMapping(value="/insert")
	public void insert(HttpServletRequest request,HttpServletResponse response){
		String number=request.getParameter("number");
		if(checkNumber(number)){
			String name=request.getParameter("name");
			String extra=request.getParameter("extra");
			Location location=new Location(number,name,extra);
			success=locationService.save(location);
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
			Location location=new Location(id,number,name,extra);
			success=locationService.update(location);
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
		success=locationService.delete(id);
		if(success)
			msg="删除成功";
		else msg="删除失败";
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	public boolean checkNumber(String number){
		List<Location> locations=locationService.findAll();
		for(Location location:locations){
			if(number.equals(location.getNumber()))
				return false;
		}
			return true;
	}
	
	@RequestMapping(value="/showAdd")
	public ModelAndView showAdd(HttpServletRequest request){
		return new ModelAndView("/location/add");
	}
	
	@RequestMapping(value="/showList")
	public ModelAndView showList(Location s_location,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/location/list");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}else{
//			s_location=(Location) session.getAttribute("s_location");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		List<Location> locationList=locationService.find(pageBean, s_location);
		int total=locationService.findAll().size();
		String pageCode=PageUtil.rootPageTion("/Xungeng/location/showList",total, pageBean.getPage(),pageBean.getPageSize(),null,null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("locationList", locationList);
		return mav;
	}
}
