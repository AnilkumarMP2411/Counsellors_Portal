package com.aksol.service;

import java.util.List;

import com.aksol.dto.ViewFiltRequest;
import com.aksol.entity.Enquiry;

public interface EnquiryService {

	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquiresWithFilter(ViewFiltRequest filter,Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);
	
}
