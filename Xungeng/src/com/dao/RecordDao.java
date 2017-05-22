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
import com.entity.Record;

@Repository
public class RecordDao {
	
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
	
	@SuppressWarnings("unchecked")
	public List<Record> find(PageBean pageBean, Record s_record){
		StringBuffer sb=new StringBuffer("from Record");
//		if(s_record!=null){
//			if(StringUtil.isNotEmpty(s_record.getNumber())){
//				sb.append(" and deptName like '%"+s_record.getName()+"%'");
//			}
//		}
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
        q.setMaxResults(pageBean.getPageSize());
        List<Record> recordList=q.list();
        tx.commit();
        session.close();
		return recordList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Record> findAll(){
		String queryString="from Record";
		return (List<Record>) this.hibernateTemplate.find(queryString);
	}
	
	public Record findById(int id){
		return (Record) this.hibernateTemplate.get(Record.class, id);
	}
}
