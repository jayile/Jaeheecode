package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.CDto;
import com.study.springboot.dto.GDto;

@Mapper
public interface ICDao
{
	public int customer_write1Dao(Map map);
	public int customer_write2Dao(Map map);
	public int customer_write3Dao(Map map);
	
	public List<CDto> qna_listDao(String mid, int start, int end);
	public int selectCountDao(String mid);
	public List<CDto> all_qna_listDao(int start, int end);
	public int all_qnaCountDao();
	
	public CDto qna_viewDao(String qId);
	
	public int qna_reply_writeDao(Map map);
	public void qna_anwser_updateDao(Map map);
}
