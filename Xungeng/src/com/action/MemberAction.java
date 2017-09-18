package com.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Member;
import com.entity.PageBean;
import com.service.MemberService;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/member")
public class MemberAction {

	@Autowired
	private MemberService memberService;

	private String msg;
	private boolean success;
	private JSONObject resultJson = new JSONObject();

	/**
	 * 添加成员
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		String number = request.getParameter("number");
		if (checkNumber(number, 0)) {
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String email = request.getParameter("email");
			String extra = request.getParameter("extra");
			Member member = new Member(number, name, mobile, email, extra);
			success = memberService.save(member);
			if (success)
				msg = "添加成功";
			else
				msg = "添加失败";
		} else {
			success = false;
			msg = "编号已存在";
		}
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 更新成员信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String number = request.getParameter("number");
		if (checkNumber(number, id)) {
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String email = request.getParameter("email");
			String extra = request.getParameter("extra");
			Member member = new Member(id, number, name, mobile, email, extra);
			success = memberService.update(member);
			if (success)
				msg = "更新成功";
			else
				msg = "更新失败";
		} else {
			success = false;
			msg = "编号已存在";
		}
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 删除成员
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/del")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		success = memberService.delete(id);
		if (success)
			msg = "删除成功";
		else
			msg = "删除失败";
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 检查编号是否存在
	 * @param number
	 * @param id
	 * @return
	 */
	public boolean checkNumber(String number, int id) {
		List<Member> members = memberService.findAll();
		for (Member member : members) {
			if (number.equals(member.getNumber()) && member.getId() != id)
				return false;
		}
		return true;
	}

	/**
	 * 显示添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showAdd")
	public ModelAndView showAdd(HttpServletRequest request) {
		return new ModelAndView("member/add");
	}

	/**
	 * 显示成员列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showList")
	public ModelAndView showList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/list");
		String page = request.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		List<Member> memberList = memberService.find(pageBean);
		int total = memberService.findAll().size();
		String pageCode = PageUtil.rootPageTion("member/showList", total, pageBean.getPage(),
				pageBean.getPageSize(), null, null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("memberList", memberList);
		return mav;
	}
}
