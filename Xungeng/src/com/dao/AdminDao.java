package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.entity.Admin;
import com.entity.PageBean;

@Repository
public class AdminDao {

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

	public Admin login(String userName, String password) {
		Admin user = null;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery("from admin u where u.username = ? and u.password =?");
		query.setParameter("username", userName);
		query.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<Admin> lists = query.list();
		if (lists != null && lists.size() > 0) {
			user = lists.get(0);
		}
		session.close();
		return user;
	}

	public boolean save(Admin admin) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(admin);
		tx.commit();
		session.close();
		return true;
	}

	public boolean update(Admin admin) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.merge(admin);
		tx.commit();
		session.close();
		return true;
	}

	public boolean changePassword(int id, String password) {
		Admin admin = this.findByUserId(id);
		admin.setPassword(password);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.merge(admin);
		tx.commit();
		session.close();
		return true;
	}

	public boolean delete(int id) {
		Admin admin = this.findByUserId(id);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		session.delete(admin);
		tr.commit();
		session.close();
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Admin> find(PageBean pageBean, Admin s_admin) {
		StringBuffer sb = new StringBuffer("from Admin");
		// if(s_admin!=null){
		// if(StringUtil.isNotEmpty(s_admin.getNumber())){
		// sb.append(" and deptName like '%"+s_admin.getName()+"%'");
		// }
		// }
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
		q.setMaxResults(pageBean.getPageSize());
		List<Admin> adminList = q.list();
		tx.commit();
		session.close();
		return adminList;
	}

	@SuppressWarnings("unchecked")
	public List<Admin> findAll() {
		String queryString = "from Admin";
		return (List<Admin>) this.hibernateTemplate.find(queryString);
	}

	public Admin findByUserId(int id) {
		return (Admin) this.hibernateTemplate.get(Admin.class, id);
	}

}
