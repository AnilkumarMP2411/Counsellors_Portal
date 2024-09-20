package com.aksol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aksol.dto.DashboardResponse;
import com.aksol.entity.Counsellor;
import com.aksol.entity.Enquiry;
import com.aksol.repos.CounsellorRepo;
import com.aksol.repos.EnquiryRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	
	
	private CounsellorRepo counsellorRepo;
	
	private EnquiryRepo enquiryRepo;
	
	
	public CounsellorServiceImpl(CounsellorRepo counsellorRepo,EnquiryRepo enquiryRepo) {
		this.counsellorRepo=counsellorRepo;
		this.enquiryRepo=enquiryRepo;
	}
	
	
	
	@Override
	public boolean register(Counsellor counsellor) {
	Counsellor savedCounsellor = counsellorRepo.save(counsellor);
	
	if(null!=savedCounsellor.getCounsellorId()) {
		return true;
	}
		return false;
	}

	
	@Override
	public Counsellor login(String emailId, String pwd) {
		return counsellorRepo.findByEmailIdAndPwd(emailId,pwd);
		
	}

	
	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		
		DashboardResponse response=new DashboardResponse();
		
		List<Enquiry> enqList = enquiryRepo.getEnquiresByCounsellorId(counsellorId);
		
		int totalEnq = enqList.size();
		
		int enrolledEnqs = enqList.stream().filter(e->e.getStatus().equals("Enrolled"))
		                .collect(Collectors.toList())
		                .size();
		
		int lostEnqs = enqList.stream().filter(e->e.getStatus().equals("Lost"))
                                        .collect(Collectors.toList())
                                        .size();
		
		int openEnqs = enqList.stream().filter(e->e.getStatus().equals("Open"))
                                       .collect(Collectors.toList())
                                        .size();
		
		response.setTotalEnq(totalEnq);
		response.setEnrolledEnq(enrolledEnqs);
		response.setLostEnq(lostEnqs);
		response.setOpenEnq(openEnqs);
		
		return response;
	}


	@Override
	public Counsellor findByemailId(String emailId) {
		// TODO Auto-generated method stub
		Counsellor findByEmailId = counsellorRepo.findByEmailId(emailId);
		return findByEmailId;
		
	}

}
