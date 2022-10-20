package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BPageInfo;
import com.study.jsp.dao.SDao;
import com.study.jsp.dto.BDto;

public class SearchCommand implements BCommand {
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("do Serch");
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch(Exception e) {
		}
		
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		
		SDao sDao = SDao.getInstance();
		BPageInfo pinfo = sDao.serchPage(nPage, key, value);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> sDtos = sDao.slist(nPage, key, value);
		request.setAttribute("serchlist", sDtos);
		
//		System.out.println("nPage : " + nPage);
//		System.out.println("key : " + key);
//		System.out.println("value : " + value);
//		System.out.println("pinfo : " + pinfo);
//		System.out.println("sDtos : " + sDtos);
		
//		String json = null;
//        response.setContentType("text/html; charset=UTF-8");
//        PrintWriter writer;
//		try
//		{
//			writer = response.getWriter();
//			
//	        for(int i =0 ; i<sDtos.size() ; i++ ) {
//	            if( i == (sDtos.size()-1)) {
//	            	json = "{\"bId\":\""+sDtos.get(i).getbId()+"\",";
//	            	json += "\"bTitle\":\"" +sDtos.get(i).getbTitle()+"\",";
//	            	json += "\"bName\":\"" + sDtos.get(i).getbName()+"\",";
//	            	json += "\"bDate\":\""+ sDtos.get(i).getbDate()+"\",";
//	            	json += "\"bHit\":\"" + sDtos.get(i).getbHit()+"\",";
//	            	json += "\"bReco\":\"" + sDtos.get(i).getbReco()+"\"}]";
//	                
//	                System.out.println(json);
//	                writer.print(json);
//	            } else {
//	                if(i == 0)
//	                {
//	                	json ="[";
//	                	json += "{\"bId\":\""+sDtos.get(i).getbId()+"\",";
//	                	json += "\"bTitle\":\"" +sDtos.get(i).getbTitle()+"\",";
//	                	json += "\"bName\":\"" + sDtos.get(i).getbName()+"\",";
//	                	json += "\"bDate\":\""+ sDtos.get(i).getbDate()+"\",";
//	                	json += "\"bHit\":\"" + sDtos.get(i).getbHit()+"\",";
//	                	json += "\"bReco\":\"" + sDtos.get(i).getbReco()+"\"},";
//	                    
//	                }else {
//	                	json = "{\"bId\":\""+sDtos.get(i).getbId()+"\",";
//	                	json += "\"bTitle\":\"" +sDtos.get(i).getbTitle()+"\",";
//	                	json += "\"bName\":\"" + sDtos.get(i).getbName()+"\",";
//	                	json += "\"bDate\":\""+ sDtos.get(i).getbDate()+"\",";
//	                	json += "\"bHit\":\"" + sDtos.get(i).getbHit()+"\",";
//	                	json += "\"bReco\":\"" + sDtos.get(i).getbReco()+"\"},";
//	                }
//	                System.out.println(json);
//	                writer.print(json);
//	            }
//	        }
//	        writer.close();
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}
}
