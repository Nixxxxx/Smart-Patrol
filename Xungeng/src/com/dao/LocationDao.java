package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.entity.Location;
import com.entity.PageBean;

@Repository
public class LocationDao {
	
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
	 
	public boolean save(Location location){
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		session.save(location);
		tx.commit();
		session.close();
		return true;
	}
	
	public boolean update(Location location){
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		session.merge(location);
		tx.commit();
		session.close();
		return true;
	}
	
	public boolean delete(int id){
		Location location=this.findById(id);
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr=session.beginTransaction();
		session.delete(location); 
		tr.commit();
		session.close();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Location> find(PageBean pageBean, Location s_location){
		StringBuffer sb=new StringBuffer("from Location");
//		if(s_location!=null){
//			if(StringUtil.isNotEmpty(s_location.getNumber())){
//				sb.append(" and deptName like '%"+s_location.getName()+"%'");
//			}
//		}
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
        q.setMaxResults(pageBean.getPageSize());
        List<Location> locationList=q.list();
        tx.commit();
        session.close();
		return locationList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Location> findAll(){
		String queryString="from Location";
		return (List<Location>) this.hibernateTemplate.find(queryString);
	}
	
	public Location findById(int id){
		return (Location) this.hibernateTemplate.get(Location.class, id);
	}
}
