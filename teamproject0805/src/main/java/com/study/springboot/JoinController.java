package com.study.springboot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.dto.MDto;
import com.study.springboot.service.IMService;

@Controller
public class JoinController
{
	@Autowired
	IMService ims;
	
	@Value("${captcha.path}")
    private String captchaPath;
	
	@RequestMapping("/homejoin/joincheck")
	public String gojoincheck() {
		return "joincheck";
	}
	
	@RequestMapping("/homejoin/adduser")
	public String homejoin() {
		return "join";
	}
	
	@RequestMapping("/homejoin/idcheck")
	public @ResponseBody String doIdcheck(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		
		String mid = request.getParameter("id");
		System.out.println("check id : " + mid);
		int nResult = ims.idcheck(mid);
		
		if(nResult == 0) {
			obj.put("code", "success");
			obj.put("desc", "사용가능한 아이디입니다.");
		} else {
			obj.put("code", "fail");
			obj.put("desc", "사용할수 없는 아이디입니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/homejoin/dojoin")
	public @ResponseBody String memberjoin(@ModelAttribute("dto") @Valid MDto MDto,
			  								BindingResult result,
			  								HttpServletRequest request,
			  								Model model)
	{
		Boolean captchaResult = false;
		
		JSONObject obj = new JSONObject();
		
		String captchakey = request.getParameter("key");
		String user = request.getParameter("value");
		System.out.println("captchakey : " + captchakey);
		System.out.println("user : " + user);
		
		captchaResult = userAnswer(captchakey, user);
		// 네이버 캡차 입력값이 맞을시
		if (captchaResult == true) {
			if(result.hasErrors()) {
//				System.out.println("입력검증");
				// 입력 검증
				if(result.getFieldError("mid") != null) {
					String massage = result.getFieldError("mid").getDefaultMessage();
					System.out.println("1 : " + massage);
					obj.put("code", "fail");
					obj.put("desc", massage);
				} else if(result.getFieldError("mpw") != null) {
					String massage = result.getFieldError("mpw").getDefaultMessage();
					System.out.println("2 : " + massage);
					obj.put("code", "fail");
					obj.put("desc", massage);
				} else if(result.getFieldError("mname") != null) {
					String massage = result.getFieldError("mname").getDefaultMessage();
					System.out.println("3 : " + massage);
					obj.put("code", "fail");
					obj.put("desc", massage);
				}
				return obj.toJSONString();
			
			} else {
				// 검증 후 정보 등록
				int nResult = ims.join(request.getParameter("mid"),
									   request.getParameter("mpw"),
									   request.getParameter("mname"),
									   request.getParameter("memail"),
									   request.getParameter("mpnum"));
				
				if(nResult == 1) {
					obj.put("code", "success");
					obj.put("desc", "회원가입 성공");
				} else {
					obj.put("code", "fail");
					obj.put("desc", "회원가입 실패");
				}				
			}
		// 캡차 입력값이 틀릴 시
		} else {
			obj.put("code", "fail");
			obj.put("desc", "자동입력값이 틀립니다.");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping("/homejoin/joinOk")
	public String joinsucess() {
		return "joinok";
	}
	
	@RequestMapping("/homejoin/captchaReset")
	public @ResponseBody String captchaNkey(HttpServletRequest request, Model model) {
		JSONObject obj = new JSONObject();
		
		String clientId = "";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "";//애플리케이션 클라이언트 시크릿값";
        String tempname = "";
        try {
            String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String apiURL = "https://naveropenapi.apigw.ntruss.com/captcha/v1/nkey?code=" + code;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println("11111 : " + response);
            JSONParser jsonParse = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParse.parse(response.toString());
            
            String key = (String) jsonObject.get("key");
            System.out.println(key);
            tempname = getImage(key, request, model);
            System.out.println("tempname : " + tempname);
            
            obj.put("key", key);
			obj.put("image", tempname);
        } catch (Exception e) {
            System.out.println(e);
        }
        return obj.toJSONString();
    }
	
	public String getImage(String imageKey, HttpServletRequest request, Model model) {
		String clientId = "";//애플리케이션 클라이언트 아이디값";
        String tempname = "";
        try {
            String key = imageKey; // https://naveropenapi.apigw.ntruss.com/captcha/v1/nkey 호출로 받은 키값
            String apiURL = "https://naveropenapi.apigw.ntruss.com/captcha-bin/v1/ncaptcha?key=" + key + "&X-NCP-APIGW-API-KEY-ID" + clientId;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            
            String path = ResourceUtils.getFile(captchaPath).toPath().toString();
            System.out.println(path);
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 파일 생성
                tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(path + File.separator + tempname + ".jpg");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
//                outputStream.close();
                System.out.println(tempname);
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tempname;
    }
	
	public Boolean userAnswer(String captchakey, String user) {
		Boolean result = false;
		String clientId = "";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "";//애플리케이션 클라이언트 시크릿값";
        try {
            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String key = captchakey; // 캡차 키 발급시 받은 키값
            String value = user; // 사용자가 입력한 캡차 이미지 글자값
            String apiURL = "https://naveropenapi.apigw.ntruss.com/captcha/v1/nkey?code=" + code + "&key=" + key + "&value=" + value;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            JSONParser jsonParse = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParse.parse(response.toString());
            
            result = (Boolean) jsonObject.get("result");
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
