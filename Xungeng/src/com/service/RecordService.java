package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.RecordDao;
import com.entity.PageBean;
import com.entity.Record;

@Service
public class RecordService {

	@Resource
	private RecordDao recordDao;


	public RecordDao getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(RecordDao recordDao) {
		this.recordDao = recordDao;
	}

	public List<Record> find(PageBean pageBean,Record s_record){
		return recordDao.find(pageBean, s_record);
	}
	
	public List<Record> findAll(){
		return recordDao.findAll();
	}
	
	
}
