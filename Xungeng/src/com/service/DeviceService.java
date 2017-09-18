package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.DeviceDao;
import com.entity.Device;
import com.entity.PageBean;

@Service
public class DeviceService {

	@Resource
	private DeviceDao deviceDao;

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	public boolean save(Device device) {
		return deviceDao.save(device);
	}

	public boolean update(Device device) {
		return deviceDao.update(device);
	}

	public boolean delete(int id) {
		return deviceDao.delete(id);
	}

	public List<Device> find(PageBean pageBean) {
		return deviceDao.find(pageBean);
	}

	public List<Device> findAll() {
		return deviceDao.findAll();
	}

	public Device findById(int id) {
		return deviceDao.findById(id);
	}

	public Device findByNumber(String deviceNumber) {
		return deviceDao.findByNumber(deviceNumber);
	}

}
