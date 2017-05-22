package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.entity.PageBean;
import com.entity.TTime;

@Repository
public class TTimeDao {
	
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
	 
	public boolean save(TTime ttime){
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		session.save(ttime);
		tx.commit();
		session.close();
		return true;
	}
	
	public boolean update(TTime ttime){
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		session.merge(ttime);
		tx.commit();
		session.close();
		return true;
	}
	
	public boolean delete(int id){
		TTime ttime=this.findById(id);
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr=session.beginTransaction();
		session.delete(ttime); 
		tr.commit();
		session.close();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<TTime> find(PageBean pageBean, TTime s_ttime){
		StringBuffer sb=new StringBuffer("from TTime");
//		if(s_ttime!=null){
//			if(StringUtil.isNotEmpty(s_ttime.getNumber())){
//				sb.append(" and deptName like '%"+s_ttime.getName()+"%'");
//			}
//		}
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
        q.setMaxResults(pageBean.getPageSize());
        List<TTime> ttimeList=q.list();
        tx.commit();
        session.close();
		return ttimeList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TTime> findAll(){
		String queryString="from TTime";
		return (List<TTime>) this.hibernateTemplate.find(queryString);
	}
	
	public TTime findById(int id){
		return (TTime) this.hibernateTemplate.get(TTime.class, id);
	}
}
