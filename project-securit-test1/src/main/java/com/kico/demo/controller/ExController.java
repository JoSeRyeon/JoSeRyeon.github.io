package com.kico.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kico.demo.auth.MyUserDetail;
import com.kico.demo.entity.User;
import com.kico.demo.service.ExService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
// 초기화 되지 않은 final field에 대해 생성자 생성 - autowired 사용 필요 없음 
@RequiredArgsConstructor
//logger 사용을 위한 어노테이션
@Slf4j
public class ExController {
	private final ExService service;

	// 첫 페이지
	@GetMapping("/")
	public String userAccess(Model model, Authentication authentication) {
		
		// 로그인 된 상태일 때 
		if(authentication != null) {
			MyUserDetail userDetail = (MyUserDetail)authentication.getPrincipal();
			log.info(userDetail.getUsername()); 
			model.addAttribute("account",userDetail.getUsername());
			
		}
		
		return "index";
	}

	// 회원가입 페이지
	@GetMapping("/signUp")
	public String signUpForm() {
		return "signup";
	}

	// 회원가입 완료 후 페이지
	@PostMapping("/signUp")
	public String signUp(User user) {
		
		// Role 세팅
		user.setRole("USER");
		log.info(user.getPassword());
		
		// 회원 데이터를 DB에 저장
		service.joinUser(user);
		log.info(user.getEmail());

		return "redirect:/";
	}

	// 로그인폼 페이지 이동
	@GetMapping("logIn")
	public String logIn(Model model, Authentication authentication) {

		return "redirect:login";
	}
	
	//로그아웃
	@PostMapping("logOut")
	public String logOut(Model model, Authentication authentication) {

		return "redirect:logout";
	}
	

	
	

}
