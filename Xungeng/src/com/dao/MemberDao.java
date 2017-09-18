package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.entity.Member;
import com.entity.PageBean;

@Repository
public class MemberDao {

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

	public boolean save(Member member) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(member);
		tx.commit();
		session.close();
		return true;
	}

	public boolean update(Member member) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.merge(member);
		tx.commit();
		session.close();
		return true;
	}

	public boolean delete(int id) {
		Member member = this.findById(id);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		session.delete(member);
		tr.commit();
		session.close();
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Member> find(PageBean pageBean) {
		StringBuffer sb = new StringBuffer("from Member");
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		q.setFirstResult(pageBean.getStart());
		q.setMaxResults(pageBean.getPageSize());
		List<Member> memberList = q.list();
		tx.commit();
		session.close();
		return memberList;
	}

	@SuppressWarnings("unchecked")
	public List<Member> findAll() {
		String queryString = "from Member";
		return (List<Member>) this.hibernateTemplate.find(queryString);
	}

	public Member findById(int id) {
		return (Member) this.hibernateTemplate.get(Member.class, id);
	}
}
