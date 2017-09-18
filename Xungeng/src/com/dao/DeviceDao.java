package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.entity.Device;
import com.entity.PageBean;

@Repository
public class DeviceDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@Resource
	private SessionFactory sessionFactory;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean save(Device device) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		session.save(device);
		session.close();
		return true;
	}

	public boolean update(Device device) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		session.merge(device);
		tr.commit();
		session.close();
		return true;
	}

	public boolean delete(int id) {
		Device device = this.findById(id);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		session.delete(device);
		tr.commit();
		session.close();
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Device> find(PageBean pageBean) {
		StringBuffer sb = new StringBuffer("from Device");
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
		q.setMaxResults(pageBean.getPageSize());
		List<Device> deviceList = q.list();
		tx.commit();
		session.close();
		return deviceList;
	}

	@SuppressWarnings("unchecked")
	public List<Device> findAll() {
		String queryString = "from Device";
		return (List<Device>) this.hibernateTemplate.find(queryString);
	}

	public Device findById(int id) {
		return (Device) this.hibernateTemplate.get(Device.class, id);
	}

	public Device findByNumber(String deviceNumber) {
		String query = "from Device where number = '"+deviceNumber+"'";
		@SuppressWarnings("unchecked")
		List<Device> devices = (List<Device>) this.hibernateTemplate.find(query);
		if(devices.size() != 1) 
			return null;
		else
			return (Device) devices.get(0);
	}
}
