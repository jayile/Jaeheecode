package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.BPageInfo;
import com.study.springboot.dto.BDto;

@Mapper
public interface IBDao
{
	public List<BDto> listDao(int start, int end);
	public int selectCountDao();
	public BPageInfo articlePageDao(int nPage);
	
	public BDto viewDao(String bId);
	public int upHitDao(String bId);
	
	public int writeDao(Map<String, String> map);
	public int modifyDao(Map<String, String> map);
	public int deleteDao(String bId);
	
	public List<BDto> noticelistDao(int start, int end);
	public int noticeCountDao();
	public int notice_writeDao(Map<String, String> map);
	public BDto notice_modify_viewDao(String bId);
	public int notice_modify_doDao(Map<String, String> map);
	public int notice_deleteDao(String bId);
}
