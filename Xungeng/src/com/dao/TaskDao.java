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
import com.entity.Task;

@Repository
public class TaskDao {
	
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
	 
	public boolean save(Task task){
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		session.save(task);
		tx.commit();
		session.close();
		return true;
	}
	
	public boolean delete(int id){
		Task task=this.findById(id);
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr=session.beginTransaction();
		session.delete(task); 
		tr.commit();
		session.close();
		return true;
	}
	
//	public void updateTaskState(){
//		List<Task> tasks=this.findAll();
//		long currentTime=System.currentTimeMillis();
//		for(Task task:tasks){
//			if(currentTime>Integer.parseUnsignedInt(task.getStartTime()))
//				task.setNowStage("1");
//			else task.setNowStage("1");
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public List<Task> find(PageBean pageBean, Task s_task){
		StringBuffer sb=new StringBuffer("from Task");
//		if(s_task!=null){
//			if(StringUtil.isNotEmpty(s_task.getNumber())){
//				sb.append(" and deptName like '%"+s_task.getName()+"%'");
//			}
//		}
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
        q.setMaxResults(pageBean.getPageSize());
        List<Task> taskList=q.list();
        tx.commit();
        session.close();
		return taskList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> findAll(){
		String queryString="from Task";
		return (List<Task>) this.hibernateTemplate.find(queryString);
	}
	
	public Task findById(int id){
		return (Task) this.hibernateTemplate.get(Task.class, id);
	}
}
