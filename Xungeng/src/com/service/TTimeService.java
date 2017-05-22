package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.TTimeDao;
import com.entity.PageBean;
import com.entity.TTime;

@Service
public class TTimeService {

	@Resource
	private TTimeDao ttimeDao;
	
	public TTimeDao getTtimeDao() {
		return ttimeDao;
	}

	public void setTtimeDao(TTimeDao ttimeDao) {
		this.ttimeDao = ttimeDao;
	}

	public boolean save(TTime ttime){
		return ttimeDao.save(ttime);
	}

	public boolean update(TTime ttime) {
		return ttimeDao.update(ttime);
	}

	public boolean delete(int id) {
		return ttimeDao.delete(id);
	}

	public List<TTime> find(PageBean pageBean,TTime s_ttime){
		return ttimeDao.find(pageBean, s_ttime);
	}
	
	public List<TTime> findAll(){
		return ttimeDao.findAll();
	}
	
	
}
