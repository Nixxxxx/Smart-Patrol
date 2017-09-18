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

	public boolean save(Task task) {
		return taskDao.save(task);
	}

	public boolean delete(int id) {
		return taskDao.delete(id);
	}

	public List<Task> find(PageBean pageBean, boolean status) {
		return taskDao.find(pageBean, status);
	}

	public List<Task> findAll() {
		return taskDao.findAll();
	}

	public Task findById(int id) {
		return taskDao.findById(id);
	}

	public boolean stageUpdate(int locationId, int deviceId) {
		return taskDao.stageUpdate(locationId, deviceId);
	}

	public List<int[]> findData(Integer nowStage, String start, String end) {
		return taskDao.findData(nowStage, start, end);
	}

	public List<Task> findNotComplete() {
		return taskDao.findNotComplete();
	}

	public boolean update(Task task) {
		return taskDao.update(task);
	}

}
