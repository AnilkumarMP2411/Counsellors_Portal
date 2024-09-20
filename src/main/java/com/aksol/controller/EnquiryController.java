package com.aksol.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aksol.dto.DashboardResponse;
import com.aksol.dto.ViewFiltRequest;
import com.aksol.entity.Enquiry;
import com.aksol.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	private EnquiryService enqService;
	
	public EnquiryController(EnquiryService enqService) {
		this.enqService=enqService;
	}
	
	@PostMapping("/filter-enqs")
	public String filterEnquires(@ModelAttribute ("viewFilterRequset") ViewFiltRequest viewFilterRequset,HttpServletRequest req,Model model) {
		
		//get existing session object
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList = enqService.getEnquiresWithFilter(viewFilterRequset, counsellorId);
		model.addAttribute("enquires",enqList);	
		
		return "ViewEnqsPage";
	}
	
	
	@GetMapping("/view-enquires")
	public String getEnquires(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList = enqService.getAllEnquiries(counsellorId);
		
		model.addAttribute("enquires",enqList);
		
		//Search form Binding object
		ViewFiltRequest filterReq=new ViewFiltRequest();
		model.addAttribute("viewFilterRequset",filterReq);
		
		return "ViewEnqsPage";
	}
	
	
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		Enquiry enqObj=new Enquiry();
		model.addAttribute("enq", enqObj);
		return "enquiryForm";
	}
	
	@GetMapping("/editEnq")
	public String editEnquiryPage(@RequestParam("enqId") Integer enqId,Model model) {
		Enquiry enquiry = enqService.getEnquiryById(enqId);
		model.addAttribute("enq", enquiry);
		return "enquiryForm";
	}
	
	
	
	@PostMapping("/addEnq")
	public String handleAddEnquiry(@ModelAttribute("enq") Enquiry enq,HttpServletRequest req,Model model) throws Exception {
		
		//get existing session obj
		
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		
		boolean isSaved = enqService.addEnquiry(enq, counsellorId);
		
		
		if(isSaved) {
			model.addAttribute("smsg", "Enquiry Added");
		}
		else
		{
			model.addAttribute("emsg", "Failed to Add Enquiry");
		}
		
		return "enquiryForm";
	}
}
