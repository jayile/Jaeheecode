package com.study.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.study.springboot.dao.MDao;
import com.study.springboot.dto.AddressDto;
import com.study.springboot.dto.GDto;
import com.study.springboot.dto.MDto;
import com.study.springboot.dto.OrderDto;
import com.study.springboot.dto.WDto;

@Service
public class MService implements IMService
{
	@Autowired 
	MDao mdao;
	
	
	@Override
	public int join(String mid, String mpw, String mname, String memail, String mpnum)
	{
		
		System.out.println("변환전 mpw : " + mpw);
		
		String encodePw = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(mpw);
		System.out.println("변환후 mpw : " + encodePw);
		
		int nResult = mdao.join(mid, encodePw, mname, memail, mpnum);
		System.out.println("Result : " + nResult);
		return nResult;
		
	}
	
	@Override
	public int idcheck(String mid) { 
		int nResult = 0;
		nResult = mdao.idcheck(mid);
		
		return nResult;
	}
	
	@Override
	public int idpwcheck(String mid, String mpw) {
		int nResult = 0;
		
		String encodePw = mdao.idpwcheck(mid);
		System.out.println("불러온 pw : " + encodePw);
		boolean chk = PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(mpw, encodePw);
		
		if(chk == true) {
			nResult = 1;
			System.out.println("pw 일치함");
		} else {
			nResult = 0;
		}
		
		return nResult;
	}
	
	@Override
	public MDto userinfo(String mid) {
		return mdao.userinfo(mid);
	}
	
	@Override
	public int modiuser(String mid, String memail, String mpnum) { 
		int nResult = 0;
		nResult = mdao.modiuser(mid, memail, mpnum);
		
		return nResult;
	}
	
	@Override
	public int modipw(String mid, String mpw) {
		int nResult = 0;
		System.out.println("변환전 mpw : " + mpw);
		
		String encodePw = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(mpw);
		System.out.println("변환후 mpw : " + encodePw);
		
		nResult = mdao.modipw(mid, encodePw);
		
		return nResult;
	}
	
	@Override
	public int snsjoin(String mid, String mname, String memail, String mpnum) {
		int nResult = 0;
		String encodePw = "snsuser";
		
		String mpw = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(encodePw);
		System.out.println("sns 비밀번호 : " + mpw);
		
		nResult = mdao.snsjoin(mid, mpw, mname, memail, mpnum);
		
		return nResult;
	}
	
	@Override
	public int deleteuser(String mid) {
		int nResult = 0;
		nResult = mdao.deleteuser(mid);
		
		return nResult;
	}
	
	
	// 아이디기준 위시리스트
	@Override
	public void findWish(String mid, Model model) {
		int nResult = mdao.getUser(mid);

			if (nResult > 0) {
				List<GDto> wlist = mdao.wishlist(mid);
				model.addAttribute("list", wlist);
			}

		
	}
	
	// 주소
	public void adr_List(String mid, Model model) {
		List<AddressDto> adrlist = mdao.adrlist(mid);
		model.addAttribute("adr", adrlist);
	}
	
	public int adr_Add(String mid, String adrName, String aName, String aZipcode, String aMain, String aSub, String aPrimary) {
		int nResult = 0;
		int zip = Integer.parseInt(aZipcode);
		int apir = Integer.parseInt(aPrimary);

		if(adrName != null) {
			// 주소지 이름 중복 찾기
			int exist1 = mdao.adr_find(mid, aName);
			if(exist1 != 1) {
				// 주 배송지 설정시
				if(apir == 1) {
					int exist2 = mdao.adr_find_pry(mid);
					if(exist2 == 0) {
						nResult = mdao.add_adr(mid, adrName, aName, zip, aMain, aSub, apir);
					} else {
						// 이미 주 배송지가 존재할 경우 db수정
						int exist3 = mdao.modi_primary_no(mid);
						if(exist3 == 1) {
							nResult = mdao.add_adr(mid, adrName, aName, zip, aMain, aSub, apir);
						}
					}
				} else {
					nResult = mdao.add_adr(mid, adrName, aName, zip, aMain, aSub, apir);
				}
			} 
		}
		return nResult;		// 0이면 중복된 주소지 이름 존재, 1이면 정상 등록
	}
	
	
	public int adr_Del(String mid, String adrName) {
		int nResult = 0;
		
		int exist1 = mdao.adr_find(mid, adrName);
		if(exist1 == 1) {
			nResult = mdao.del_adr(mid, adrName);
		}

		return nResult;
	}
	
	public int pay_adr(String mid, Model model) {
		int nResult = 0;
		AddressDto adrdata = mdao.pay_adr(mid);
		if(adrdata != null) {
			model.addAttribute("adr", adrdata);
			nResult = 1;
		} 
		
		return nResult;
	}
	
	// 배송중 상품
	public void del_ing(String mid, Model model) {
		int nResult = 0;
		String mid2 = mid;
		nResult = mdao.delivery_ing(mid);
		if(nResult > 0) {
			List<OrderDto> delivery = mdao.delivery_ing_info(mid, mid2);
			model.addAttribute("dinfo", delivery);
		}
	}
	
	// 최근에 찜한 상품
	public void mywish(String mid, Model model) {
		List<GDto> wishone = mdao.wishlist(mid);
		model.addAttribute("wish", wishone);
	}
}
