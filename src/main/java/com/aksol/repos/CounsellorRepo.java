package com.aksol.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aksol.entity.Counsellor;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{

	//select * from counsellor_tbl where email=:email;
	public Counsellor findByEmailId(String emailId);
	
	

	//select * from counsellor_tbl where email=:email and pwd=:pwd;
	public Counsellor findByEmailIdAndPwd(String emailId,String pwd);


	
}
