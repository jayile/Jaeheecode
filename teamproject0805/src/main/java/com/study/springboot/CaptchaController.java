package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CaptchaController {
	
	@RequestMapping("/guest/naverCaptcha")
	public String naverCaptcha(){
		return "naverCaptcha"; 
	}
}
