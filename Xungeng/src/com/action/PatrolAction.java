package com.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Device;
import com.entity.Location;
import com.entity.Member;
import com.entity.PageBean;
import com.entity.TTime;
import com.entity.Task;
import com.service.DeviceService;
import com.service.LocationService;
import com.service.MemberService;
import com.service.TTimeService;
import com.service.TaskService;
import com.util.JavaSmsApi;
import com.util.PageUtil;
import com.util.PoiExcel;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/patrol")
public class PatrolAction {

	@Autowired
	private MemberService memberService;
	@Autowired
	private TTimeService ttimeService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private TaskService taskService;

	private String msg;
	private boolean success;
	private JSONObject resultJson = new JSONObject();


	/**
	 * 插入巡更任务
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "/insertTask")
	public void insert(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		int memberId = Integer.parseInt(request.getParameter("member"));
		int deviceId = Integer.parseInt(request.getParameter("device"));
		String extra = request.getParameter("extra");
		String date = request.getParameter("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		String[] locationTimes = request.getParameter("location_time").split(",");
		//短信内容
		String message = "";
		message += date+"\n";
		for (int i = 0; i < locationTimes.length; i++) {
			String[] locationTime = locationTimes[i].split("\\|");
			int locationId = Integer.parseInt(locationTime[0]);
			//添加地点信息
			Location location = locationService.findById(locationId);
			message += location.getNumber()+"-"+location.getName()+":\n";
			for (int j = 1; j < locationTime.length; j++) {
				Date startTime = sdf.parse(date + "|" + locationTime[j].split("-")[0]);
				Date endTime = sdf.parse(date + "|" + locationTime[j].split("-")[1]);
				Task task = new Task(memberId, deviceId, locationId, startTime, endTime, extra);
				success = taskService.save(task);
				//添加时间信息
				message += locationTime[j]+"、";
			}
			message += "\n";
		}
		if (success){
			msg = "添加成功";
			Member member = memberService.findById(memberId);
			Device device = deviceService.findById(deviceId);
			try {
				JavaSmsApi.sendMessage(member.getMobile(), member.getNumber()+"-"+member.getName(), device.getNumber()+"-"+device.getName(), message);
				PoiExcel.sendWeChat(member.getNumber()+"...【无线巡更网站】"+member.getNumber()+"-"+member.getName()+"，巡更任务("+device.getNumber()+"-"+device.getName()+")：\n"+message);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}else
			msg = "添加失败";
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}
	
	/**
	 * 巡更状态更新
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/stageUpdate")
	public void stageUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Task> tasks = taskService.findNotComplete();
		if(tasks != null){
			for(Task task:tasks){
				task.setNowStage(4);
				taskService.update(task);
			}
		}
		String locationNumber = request.getParameter("location");
		String deviceNumber = request.getParameter("device");
		Location location = locationService.findByNumber(locationNumber);
		Device device = deviceService.findByNumber(deviceNumber);
		if(location == null || device == null){
			response.setStatus(400);
			return ;
		}
		int locationId = location.getId();
		int deviceId = device.getId();
		if(taskService.stageUpdate(locationId, deviceId))
			response.setStatus(200);
		else 
			response.setStatus(500);
	}

	
	/**
	 * 获取所有成员任务状态及数量（除去待完成）
	 * @param start 开始日期
	 * @param end  结束日期
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getTaskData")
	public void getTaskData(String start, String end, HttpServletRequest request, HttpServletResponse response) {
		List<int[]> notCompleteNum = taskService.findData(4, start, end);
		List<int[]> notOnTimeNum = taskService.findData(3, start, end);
		List<int[]> onTimeNum = taskService.findData(2, start, end);
		List<Member> members = memberService.findAll();
		List<String> notCompleteds = new ArrayList<String>();
		List<String> notOnTimes = new ArrayList<String>();
		List<String> onTimes = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		for(Member member : members){
			names.add(member.getName());
			notCompleteds.add("0");
			for(int[] notComplete : notCompleteNum){
				if(member.getId() == notComplete[0]){
					notCompleteds.set(notCompleteds.size()-1, notComplete[1]+"");
					break;
				}
			}
			notOnTimes.add("0");
			for(int[] notOnTime : notOnTimeNum){
				if(member.getId() == notOnTime[0]){
					notOnTimes.set(notOnTimes.size()-1, notOnTime[1]+"");
					break;
				}
			}
			onTimes.add("0");
			for(int[] onTime : onTimeNum){
				if(member.getId() == onTime[0]){
					onTimes.set(onTimes.size()-1, onTime[1]+"");
					break;
				}
			}
		}
		JSONObject data=new JSONObject();
		data.put("name", names);
		data.put("notCompleted", notCompleteds);
		data.put("notOnTime", notOnTimes);
		data.put("onTime", onTimes);
		resultJson.put("data", data);
		resultJson.put("success", true);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 下载巡更任务统计EXCEL
	 * @param start  开始日期
	 * @param end  结束日期
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadExcel")
	public void downloadExcel(String start, String end, HttpServletRequest request, HttpServletResponse response){
		List<int[]> notCompleteNum = taskService.findData(4, start, end);
		List<int[]> notOnTimeNum = taskService.findData(3, start, end);
		List<int[]> onTimeNum = taskService.findData(2, start, end);
		List<Member> members = memberService.findAll();
		List<String> notCompleteds = new ArrayList<String>();
		List<String> notOnTimes = new ArrayList<String>();
		List<String> onTimes = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		for(Member member : members){
			names.add(member.getName());
			notCompleteds.add("0");
			for(int[] notComplete : notCompleteNum){
				if(member.getId() == notComplete[0]){
					notCompleteds.set(notCompleteds.size()-1, notComplete[1]+"");
					break;
				}
			}
			notOnTimes.add("0");
			for(int[] notOnTime : notOnTimeNum){
				if(member.getId() == notOnTime[0]){
					notOnTimes.set(notOnTimes.size()-1, notOnTime[1]+"");
					break;
				}
			}
			onTimes.add("0");
			for(int[] onTime : onTimeNum){
				if(member.getId() == onTime[0]){
					onTimes.set(onTimes.size()-1, onTime[1]+"");
					break;
				}
			}
		}
		
		//获得请求文件名
		String filename = "poi_test.xlsx";
		
		//设置文件MIME类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		//设置Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		//读取目标文件，通过response将目标文件写到客户端
		//获取目标文件的绝对路径
		String fullFileName = request.getServletContext().getRealPath("/static/xungeng/" + filename);
		//读取文件
		PoiExcel.expExcel(names, notCompleteds, notOnTimes, onTimes, fullFileName, start, end);
		InputStream in;
		OutputStream out;
		try {
			in = new FileInputStream(fullFileName);
			try {
				out = response.getOutputStream();
				//写文件
				int b;
				while((b = in.read())!= -1)
				{
					out.write(b);
				}
				
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除任务
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/taskDel")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		Task task = taskService.findById(id);
		Member member = memberService.findById(task.getMemberId());
		Device device = deviceService.findById(task.getDeviceId());
		Location location = locationService.findById(task.getLocationId());
		success = taskService.delete(id);
		if (success){
			msg = "删除成功";
			String message = "任务取消("+location.getNumber()+"-"+location.getName()+"):\n";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
			message += sdf.format(task.getStartTime())+"  "+sdff.format(task.getStartTime())+"-"+sdff.format(task.getEndTime());
			try {
				JavaSmsApi.sendMessage(member.getMobile(), member.getNumber()+"-"+member.getName(), device.getNumber()+"-"+device.getName(), message);
				PoiExcel.sendWeChat(member.getNumber()+"...【无线巡更网站】"+member.getNumber()+"-"+member.getName()+"，巡更任务("+device.getNumber()+"-"+device.getName()+")：\n"+message);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		else
			msg = "删除失败";
		resultJson.put("msg", msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response, resultJson);
	}

	/**
	 * 显示巡更任务添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showTaskAdd")
	public ModelAndView showTaskAdd(HttpServletRequest request) {
		List<Member> members = memberService.findAll();
		List<Location> locations = locationService.findAll();
		List<Device> devices = deviceService.findAll();
		List<TTime> ttimes = ttimeService.findAll();
		request.setAttribute("members", members);
		request.setAttribute("locations", locations);
		request.setAttribute("devices", devices);
		request.setAttribute("ttimes", ttimes);
		return new ModelAndView("patrol/taskAdd");
	}

	/**
	 * 显示巡更任务列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showTaskList")
	public ModelAndView showTaskList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("patrol/taskList");
		String page = request.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		List<Task> taskList = taskService.find(pageBean, false);
		String pageCode = PageUtil.rootPageTion("patrol/showTaskList", taskService.find(null, false).size(), pageBean.getPage(),
				pageBean.getPageSize(), null, null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("taskList", taskList);
		List<String> devices = new ArrayList<String>();
		List<String> members = new ArrayList<String>();
		List<String> locations = new ArrayList<String>();
		for (Task task : taskList) {
			devices.add(deviceService.findById(task.getDeviceId()).getName());
			devices.add(deviceService.findById(task.getDeviceId()).getNumber());
			members.add(memberService.findById(task.getMemberId()).getName());
			members.add(memberService.findById(task.getMemberId()).getNumber());
			locations.add(locationService.findById(task.getLocationId()).getName());
			locations.add(locationService.findById(task.getLocationId()).getNumber());
		}
		request.setAttribute("members", members);
		request.setAttribute("devices", devices);
		request.setAttribute("locations", locations);
		return mav;
	}

	/**
	 * 显示巡更记录列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showRecordList")
	public ModelAndView showRecordList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("patrol/recordList");
		String page = request.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		List<Task> taskList = taskService.find(pageBean, true);
		String pageCode = PageUtil.rootPageTion("patrol/showRecordList", taskService.find(null, true).size(), pageBean.getPage(),
				pageBean.getPageSize(), null, null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("taskList", taskList);
		List<String> devices = new ArrayList<String>();
		List<String> members = new ArrayList<String>();
		List<String> locations = new ArrayList<String>();
		for (Task task : taskList) {
			devices.add(deviceService.findById(task.getDeviceId()).getName());
			devices.add(deviceService.findById(task.getDeviceId()).getNumber());
			members.add(memberService.findById(task.getMemberId()).getName());
			members.add(memberService.findById(task.getMemberId()).getNumber());
			locations.add(locationService.findById(task.getLocationId()).getName());
			locations.add(locationService.findById(task.getLocationId()).getNumber());
		}
		request.setAttribute("members", members);
		request.setAttribute("devices", devices);
		request.setAttribute("locations", locations);
		return mav;
	}
	
	/**
	 * 显示巡更统计页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showStatistics")
	public ModelAndView showStatistics(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("patrol/statistics");
	}
	
}
