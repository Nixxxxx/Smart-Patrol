package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.LocationDao;
import com.entity.Location;
import com.entity.PageBean;

@Service
public class LocationService {

	@Resource
	private LocationDao locationDao;

	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public boolean save(Location location){
		return locationDao.save(location);
	}

	public boolean update(Location location) {
		return locationDao.update(location);
	}

	public boolean delete(int id) {
		return locationDao.delete(id);
	}

	public List<Location> find(PageBean pageBean,Location s_location){
		return locationDao.find(pageBean, s_location);
	}
	
	public List<Location> findAll(){
		return locationDao.findAll();
	}
	
	public Location findById(int id){
		return locationDao.findById(id);
	}
	
}
