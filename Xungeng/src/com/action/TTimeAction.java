package com.action;

import java.sql.Time;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.PageBean;
import com.entity.TTime;
import com.service.TTimeService;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/ttime")
public class TTimeAction {

	@Autowired
	private TTimeService ttimeService;


	private String msg;
	private boolean success;
	private JSONObject resultJson = new JSONObject();

	/**
	 * 插入时间
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		Time startTime = Time.valueOf(request.getParameter("startTime") + ":00");
		Time endTime = Time.valueOf(request.getParameter("endTime") + ":00");
		String extra = request.getParameter("extra");
		TTime ttime = new TTime(startTime, endTime, extra);
		success = ttimeService.save(ttime);
		if (success)
			msg = "添加成功";
		else
			msg = "添加失败";
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 更新时间
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		Time startTime = Time.valueOf(request.getParameter("startTime") + ":00");
		Time endTime = Time.valueOf(request.getParameter("endTime") + ":00");
		String extra = request.getParameter("extra");
		TTime ttime = new TTime(id, startTime, endTime, extra);
		success = ttimeService.update(ttime);
		if (success)
			msg = "修改成功";
		else
			msg = "修改失败";
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 删除时间
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/del")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		success = ttimeService.delete(id);
		if (success)
			msg = "删除成功";
		else
			msg = "删除失败";
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 显示添加页面
	 * @return
	 */
	@RequestMapping(value = "/showAdd")
	public ModelAndView showAdd() {
		return new ModelAndView("ttime/add");
	}

	/**
	 * 显示时间列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showList")
	public ModelAndView showList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("ttime/list");
		String page = request.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		List<TTime> ttimeList = ttimeService.find(pageBean);
		int total = ttimeService.findAll().size();
		String pageCode = PageUtil.rootPageTion("ttime/showList", total, pageBean.getPage(),
				pageBean.getPageSize(), null, null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("ttimeList", ttimeList);
		return mav;
	}
}
