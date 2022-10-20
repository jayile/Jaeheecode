package com.study.springboot;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bootpay.Bootpay;

@Controller
public class BootpayController
{
	 static Bootpay bootpay;
	 @RequestMapping("/bootpay_cfm")
	 public static void main(String[] args) {

	        bootpay = new Bootpay("62ce2babe38c3000235af958", "dtVFUKhRDsvwIY1ubVOB32vRl9GPLhjXw0Lx9buiTmY=");

	        goGetToken();
	        confirm();
	    }
	 
	 public static void goGetToken() {
	        try {
	            HashMap<String, Object> res = bootpay.getAccessToken();
	            if(res.get("error_code") == null) { //success
	                System.out.println("goGetToken success: " + res);
	            } else {
	                System.out.println("goGetToken false: " + res);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static void confirm() {
	        String receiptId = "62ce2babe38c3000235af958";
	        try {
	            HashMap<String, Object> res = bootpay.confirm(receiptId);
	            if(res.get("error_code") == null) { //success
	                System.out.println("confirm success: " + res);
	            } else {
	                System.out.println("confirm false: " + res);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
