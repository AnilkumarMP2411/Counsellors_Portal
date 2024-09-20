package com.aksol.service;

import com.aksol.dto.DashboardResponse;
import com.aksol.entity.Counsellor;

public interface CounsellorService {

	public Counsellor findByemailId(String emailId);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String emailId,String Pwd);
	
	public DashboardResponse getDashboardInfo(Integer counsellor);
	
	
}
