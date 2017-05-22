package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.MemberDao;
import com.entity.Member;
import com.entity.PageBean;

@Service
public class MemberService {

	@Resource
	private MemberDao memberDao;

	
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public boolean save(Member member){
		return memberDao.save(member);
	}

	public boolean update(Member member) {
		return memberDao.update(member);
	}

	public boolean delete(int id) {
		return memberDao.delete(id);
	}

	public List<Member> find(PageBean pageBean,Member s_member){
		return memberDao.find(pageBean, s_member);
	}
	
	public List<Member> findAll(){
		return memberDao.findAll();
	}
	
	public Member findById(int id){
		return memberDao.findById(id);
	}
	
}
