package com.action;

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
import com.entity.Record;
import com.entity.TTime;
import com.entity.Task;
import com.service.DeviceService;
import com.service.LocationService;
import com.service.MemberService;
import com.service.RecordService;
import com.service.TTimeService;
import com.service.TaskService;
import com.util.PageUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/patrol")
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
	@Autowired
	private RecordService recordService;

	private String msg;
	private boolean success;
	private JSONObject resultJson=new JSONObject();
	

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public TTimeService getTtimeService() {
		return ttimeService;
	}

	public void setTtimeService(TTimeService ttimeService) {
		this.ttimeService = ttimeService;
	}

	public LocationService getLocationService() {
		return locationService;
	}

	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@RequestMapping(value="/insertTask")
	public void insert(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		int memberId=Integer.parseInt(request.getParameter("member"));
		int deviceId=Integer.parseInt(request.getParameter("device"));
		String extra=request.getParameter("extra");
		String date=request.getParameter("date");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		String[] locationTimes=request.getParameter("location_time").split(",");
		for(int i=0;i<locationTimes.length;i++){
			String[] locationTime=locationTimes[i].split("\\|");
			int locationId=Integer.parseInt(locationTime[0]);
			System.out.println(locationId);
			for(int j=1;j<locationTime.length;j++){
				Date startTime=sdf.parse(date+"|"+locationTime[j].split("-")[0]);
				Date endTime=sdf.parse(date+"|"+locationTime[j].split("-")[1]);
				Task task=new Task(memberId,deviceId,locationId,startTime,endTime,extra);
				success=taskService.save(task);
			}
		}
		if(success)
			msg="添加成功";
		else msg="添加失败";
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}

	@RequestMapping(value="/update")
	public void update(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		String number=request.getParameter("number");
		String name=request.getParameter("name");
		String mobile=request.getParameter("mobile");
		String email=request.getParameter("email");
		String extra=request.getParameter("extra");
		Member member=new Member(id,number,name,mobile,email,extra);
		success=memberService.update(member);
		if(success)
			msg="修改成功";
		else msg="修改失败";
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	@RequestMapping(value="/taskDel")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		success=taskService.delete(id);
		if(success)
			msg="删除成功";
		else msg="删除失败";
		resultJson.put("msg",msg);
		resultJson.put("success", success);
		ResponseUtil.writeJson(response,resultJson);
	}
	
	@RequestMapping(value="/showTaskAdd")
	public ModelAndView showTaskAdd(HttpServletRequest request){
		List<Member> members=memberService.findAll();
		List<Location> locations=locationService.findAll();
		List<Device> devices=deviceService.findAll();
		List<TTime> ttimes=ttimeService.findAll();
		request.setAttribute("members", members);
		request.setAttribute("locations", locations);
		request.setAttribute("devices", devices);
		request.setAttribute("ttimes", ttimes);
		return new ModelAndView("/patrol/taskAdd");
	}
	
	@RequestMapping(value="/showTaskList")
	public ModelAndView showTaskList(Task s_task,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/patrol/taskList");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}else{
//			s_record=(record) session.getAttribute("s_record");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		List<Task> taskList=taskService.find(pageBean, s_task);
		List<Task> tasks=taskService.findAll();
		int total=tasks.size();
		String pageCode=PageUtil.rootPageTion("/Xungeng/patrol/showTaskList",total, pageBean.getPage(),pageBean.getPageSize(),null,null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("taskList", taskList);
		List<String> devices=new ArrayList<String>();
		List<String> members=new ArrayList<String>();
		List<String> locations=new ArrayList<String>();
		for(Task task:tasks){
			devices.add(deviceService.findById(task.getDeviceId()).getName());
			devices.add(deviceService.findById(task.getDeviceId()).getNumber());
			members.add(memberService.findById(task.getMemberId()).getName());
			members.add(memberService.findById(task.getMemberId()).getNumber());
			locations.add(locationService.findById(task.getLocationId()).getName());
			locations.add(locationService.findById(task.getLocationId()).getNumber());
		}
		request.setAttribute("tasks", tasks);
		request.setAttribute("members", members);
		request.setAttribute("devices", devices);
		request.setAttribute("locations", locations);
		return mav;
	}

	
	@RequestMapping(value="/showRecordList")
	public ModelAndView showList(Record s_record,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/patrol/taskList");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}else{
//			s_record=(record) session.getAttribute("s_record");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		List<Record> recordList=recordService.find(pageBean, s_record);
		int total=recordService.findAll().size();
		String pageCode=PageUtil.rootPageTion("/Xungeng/patrol/showRecordList",total, pageBean.getPage(),pageBean.getPageSize(),null,null);
		mav.addObject("pageCode", pageCode);
		mav.addObject("recordList", recordList);
		return mav;
	}
}
