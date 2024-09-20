package com.aksol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.aksol.dto.DashboardResponse;
import com.aksol.entity.Counsellor;
import com.aksol.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class CounsellorController {
	
	private CounsellorService counsellorService;
	
	public CounsellorController(CounsellorService counsellorService) {
		this.counsellorService=counsellorService;
	}

	@GetMapping("/")
	public String index(Model model) {
		
		Counsellor cobj=new Counsellor();
		
		//sending data from controller to UI
		model.addAttribute("counsellor",cobj);
		
		//returning view name
		return "index";
	}
		
	@PostMapping("login")
	public String handleLoginBtn(Counsellor counsellor,HttpServletRequest request,Model model) {
		
		Counsellor c = counsellorService.login(counsellor.getEmailId(), counsellor.getPwd());
		
		if(c==null) {
		model.addAttribute("emsg","Invalid Credentials");
		return "index";
		}
		else {
			
			//valid login, store counsellorId in session for future purpose
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());
					
			DashboardResponse dbrespobj = counsellorService.getDashboardInfo(c.getCounsellorId());
		
			model.addAttribute("dashboardInfo",dbrespobj);
		
			return "dashboard";	
		}			
	}
	
	@GetMapping("dashboard")
	public String displayDashboard(HttpServletRequest req,Model model) {
		
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		DashboardResponse dbrespobj = counsellorService.getDashboardInfo(counsellorId);
		
		model.addAttribute("dashboardInfo",dbrespobj);
		
		return "dashboard";
	}
	
	
	@GetMapping("/register")
	public String registerPage(Model model) {
Counsellor cobj=new Counsellor();
		
		//sending data from controller to UI
		model.addAttribute("counsellor",cobj);
		
		//returning view name
		return "register";
	}
	
	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {
		
		Counsellor byemailId = counsellorService.findByemailId(counsellor.getEmailId());
		
		if(byemailId != null) {
			model.addAttribute("emsg","Duplicate Email");
			return "register";
		}		
		
		boolean isregistered = counsellorService.register(counsellor);
		
		if(isregistered) {
			//success
			model.addAttribute("smsg", "Registration success..!!");
		}
		else {
			//failure
			model.addAttribute("emsg","Registration failed");
		}
		return "register";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		
		//get existing session and invalidate it
		HttpSession session=req.getSession(false);
		session.invalidate();
		
		//redirect login page
		return "redirect:/";
	}
	
}
