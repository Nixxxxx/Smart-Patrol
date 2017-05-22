package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Task;
import com.service.TaskService;

@Controller
@RequestMapping(value="/main")
public class MainAction {
	
	@Autowired
	private TaskService taskService;
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value="/showInfo")
	public ModelAndView showInfo(HttpServletRequest request){
		List<Task> tasks=taskService.findAll();
		int onTimeCompletedTask=0,toBeCompletedTask=0,notOnTimeCompletedTask=0,notCompletedTask=0;
		for(Task task:tasks){
			if(task.getNowStage()==1)
				toBeCompletedTask++;
			if(task.getNowStage()==2)
				onTimeCompletedTask++;
			if(task.getNowStage()==3)
				notOnTimeCompletedTask++;
			if(task.getNowStage()==4)
				notCompletedTask++;
		}
		List<String> taskNumbers=new ArrayList<String>();
		taskNumbers.add(onTimeCompletedTask+"");
		taskNumbers.add(toBeCompletedTask+"");
		taskNumbers.add(notOnTimeCompletedTask+"");
		taskNumbers.add(notCompletedTask+"");
		request.setAttribute("taskNumbers", taskNumbers);
		return new ModelAndView("info");
	}
}
