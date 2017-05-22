package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.TaskDao;
import com.entity.PageBean;
import com.entity.Task;

@Service
public class TaskService {

	@Resource
	private TaskDao taskDao;
	
	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public boolean save(Task task){
		return taskDao.save(task);
	}


	public boolean delete(int id) {
		return taskDao.delete(id);
	}

	public List<Task> find(PageBean pageBean,Task s_task){
		return taskDao.find(pageBean, s_task);
	}
	
	public List<Task> findAll(){
		return taskDao.findAll();
	}
	
	
}
