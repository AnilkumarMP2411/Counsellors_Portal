package com.aksol.service;

import java.util.List;


import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.aksol.dto.ViewFiltRequest;
import com.aksol.entity.Counsellor;
import com.aksol.entity.Enquiry;
import com.aksol.repos.CounsellorRepo;
import com.aksol.repos.EnquiryRepo;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	
	private EnquiryRepo enquiryRepo;
	
	private CounsellorRepo counsellorRepo;
	
	
		public EnquiryServiceImpl(EnquiryRepo enquiryRepo , CounsellorRepo counsellorRepo) {
			this.enquiryRepo=enquiryRepo;
			this.counsellorRepo=counsellorRepo;
		}
	
	
	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {

		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellor==null) {
			throw new Exception("No Counsellor found");
		}
		
		//associating counsellor to enquiry
		enq.setCounsellor(counsellor);
		
		Enquiry save = enquiryRepo.save(enq);
		
		if(save.getEnqId()!=null){
			return true;
		}
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		
		return enquiryRepo.getEnquiresByCounsellorId(counsellorId);
	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return enquiryRepo.findById(enqId).orElse(null);
	}
	
	@Override
	public List<Enquiry> getEnquiresWithFilter(ViewFiltRequest filter, Integer counsellorId) {
		
		
		//QBE implementation (Dynamic Query Prepration)
		
		Enquiry enq=new Enquiry(); //entity
		
		if(StringUtils.isNotEmpty(filter.getClassMode())) {
			enq.setClassMode(filter.getClassMode());
		}
		
		if(StringUtils.isNotEmpty(filter.getCourse())) {
			enq.setCourse(filter.getCourse());
		}
		
		if(StringUtils.isNotEmpty(filter.getStatus())) {
			enq.setStatus(filter.getStatus());
		}
		
		Counsellor con = counsellorRepo.findById(counsellorId).orElse(null);
		
		enq.setCounsellor(con);
		
		Example<Enquiry> of=Example.of(enq);
		
		List<Enquiry> enqList =enquiryRepo.findAll(of);
			
		return enqList;
	}

}
