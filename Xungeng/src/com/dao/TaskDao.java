package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
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

	public boolean save(Task task) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(task);
		tx.commit();
		session.close();
		return true;
	}

	public boolean delete(int id) {
		Task task = this.findById(id);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		session.delete(task);
		tr.commit();
		session.close();
		return true;
	}

	/**
	 * 巡更任务/记录查询  status=true为记录，反之为任务
	 * @param pageBean 
	 * @param status 状态码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> find(PageBean pageBean, boolean status) {
		StringBuffer sb = null;
		if(status){
			sb = new StringBuffer("select * from task where now_stage!=1 order by end_datetime desc ");
		}else{
			sb = new StringBuffer("select * from task where now_stage=1 order by end_datetime desc ");
		}
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createSQLQuery(sb.toString()).addEntity(Task.class);
		if(pageBean != null){
			q.setFirstResult(pageBean.getStart());
			q.setMaxResults(pageBean.getPageSize());
		}
		List<Task> taskList = q.list();
		tx.commit();
		session.close();
		return taskList;
	}

	@SuppressWarnings("unchecked")
	public List<Task> findAll() {
		return (List<Task>) this.hibernateTemplate.find("from Task");
	}

	public Task findById(int id) {
		return (Task) this.hibernateTemplate.get(Task.class, id);
	}

	public boolean stageUpdate(int locationId, int deviceId) {
		String queryString = "select * from task where location_id = "+locationId +" and device_id = "+deviceId
				+" and now_stage = 1 order by end_datetime asc";
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createSQLQuery(queryString).addEntity(Task.class);
		@SuppressWarnings("unchecked")
		List<Task> tasks = q.list();
		tx.commit();
		session.close();
		if(tasks.size() != 0){
			Date date = new Date();
			Task task = tasks.get(0);
			task.setCompleteTime(date);
			if(date.before(task.getStartTime())){
				task.setNowStage(3);
			}else if(date.before(task.getEndTime())){
				task.setNowStage(2);
			}else{
				task.setNowStage(4);
			}
			return this.update(task);
		}else{
			return false;
		}
	}

	public boolean update(Task task) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.merge(task);
		tx.commit();
		session.close();
		return true;
	}

	@SuppressWarnings("rawtypes")
	public List<int[]> findData(int nowStage, String start, String end) {
		String query = "select member_id as memberId,count(now_stage) as nowStage from task "
				+ "where now_stage='"+nowStage+"' and date_format(complete_time, '%Y-%m-%d') "
						+ "between '"+start+"' and '"+end+"' group by memberId order by memberId asc";
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createSQLQuery(query)
				.addScalar("memberId", StandardBasicTypes.INTEGER)
				.addScalar("nowStage", StandardBasicTypes.INTEGER);
		List list = q.list();
		List<int[]> taskStages = new ArrayList<int[]>();
		for(Iterator iterator = list.iterator();iterator.hasNext();){
			Object[] ob =  (Object[]) iterator.next();
			taskStages.add(new int[]{(int) ob[0], (int) ob[1]});
		}
		tx.commit();
		session.close();
		return taskStages;
	}
	

	/**
	 * 查找所有待完成并且结束时间在当前时间之前的任务
	 * @return
	 */
	public List<Task> findNotComplete() {
		String query = "select * from task where now_stage='1' and end_datetime <now()";
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createSQLQuery(query).addEntity(Task.class);
		@SuppressWarnings("unchecked")
		List<Task> tasks = q.list();
		tx.commit();
		session.close();
		return tasks;
	}
}
