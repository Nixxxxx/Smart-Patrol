package com.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Admin;
import com.entity.PageBean;
import com.service.AdminService;
import com.util.MD5Util;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/admin")
public class AdminAction {
	
	
	@Autowired
	private AdminService adminService;
	
	private String msg;
	private boolean success;
	private JSONObject resultJson=new JSONObject();
	

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	
	@RequestMapping(value="/login")
	public void login(String userName,String password,String captcha,String checkbox,HttpServletRequest request,HttpServletResponse response){
		List<Admin> admins=adminService.findAll();
		String sRand=(String)request.getSession().getAttribute("sRand");
		int flag=0;
		String result="";
		JSONObject resultJson=new JSONObject();
		if(captcha.equalsIgnoreCase(sRand)){
			flag=1;
			for(Admin admin:admins){
				if(admin.getUserName().equals(userName)&&MD5Util.getMD5Code(password).equals(admin.getPassword())){
					admin=adminService.findByUserId(admin.getId());
					request.getSession().setAttribute("admin", admin);
					if("true".equals(checkbox)){
						Cookie cookie=new Cookie(admin.getUserName(),admin.getPassword());
						cookie.setMaxAge(1*60*60*24*7);
					}
					flag=2;break;
				}
			}
		}
		if(flag==1) 
			result="用户名或密码错误";
		if(flag==0)
			result="验证码错误";
		resultJson.put("result",result);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	
	@RequestMapping(value="/insert")
	public void insert(HttpServletRequest request,HttpServletResponse response){
		String userName=request.getParameter("userName");
		if(checkUserName(userName)){
			String password=request.getParameter("password");
			String mobile=request.getParameter("mobile");
			String email=request.getParameter("email");
			String extra=request.getParameter("extra");
			Admin admin=new Admin(userName,MD5Util.getMD5Code(password),mobile,email,extra);
			success=adminService.save(admin);
			if(success)
				msg="添加成功";
			else msg="添加失败";
		}else {
			success=false;
			msg="用户名已存在";
		}
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}

	@RequestMapping(value="/update")
	public void update(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		Admin admin=adminService.findByUserId(id);
		if(checkUserName(request.getParameter("userName"))){
			admin.setUserName(request.getParameter("userName"));
			admin.setMobile(request.getParameter("mobile"));
			admin.setEmail(request.getParameter("email"));
			admin.setExtra(request.getParameter("extra"));
			admin.setAddTime(new Date());
			success=adminService.update(admin);
			if(success)
				msg="修改成功";
			else msg="修改失败";
		}else {
			success=false;
			msg="用户名已存在";
		}
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	public boolean checkUserName(String userName){
		List<Admin> admins=adminService.findAll();
		for(Admin admin:admins){
			if(userName.equals(admin.getUserName()))
				return false;
		}
		return true;
	}
	
	@RequestMapping(value="/changePassword")
	public void changePassword(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		String oldPassword=request.getParameter("oldPassword");
		Admin admin=adminService.findByUserId(id);
		if(admin.getPassword().equals(MD5Util.getMD5Code(oldPassword))){
			String newPassword=request.getParameter("newPassword");
			success=adminService.changePassword(id,MD5Util.getMD5Code(newPassword));
			if(success)
				msg="修改密码成功";
			else msg="修改密码失败";
		}else {
			success=false;
			msg="原密码错误";
		}
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	@RequestMapping(value="/del")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		success=adminService.delete(id);
		if(success)
			msg="删除成功";
		else msg="删除失败";
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	
	@RequestMapping(value="/index")
	public ModelAndView loginn(){
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/showSetting")
	public ModelAndView showList(Admin s_admin,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/admin/setting");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}else{
//			s_admin=(Admin) session.getAttribute("s_admin");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		List<Admin> adminList=adminService.find(pageBean, s_admin);
		int total=adminService.findAll().size();
		String pageCode=PageUtil.rootPageTion("/Xungeng/admin/showSetting",total, pageBean.getPage(),pageBean.getPageSize(),null,null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("adminList", adminList);
		return mav;
	}
	
	@RequestMapping(value="/showMessage")
	public ModelAndView showMessage(){
		return new ModelAndView("/admin/message");
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request){
		Admin admin=(Admin) request.getSession().getAttribute("admin");
		if(admin!=null){
			request.getSession().removeAttribute("admin");
		}
		return new ModelAndView("../../login");
	}
	
}
