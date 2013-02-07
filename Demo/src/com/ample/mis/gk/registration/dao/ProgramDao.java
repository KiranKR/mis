package com.ample.mis.gk.registration.dao;

import java.util.List;

import com.ample.mis.gk.registration.bean.PendingRep;

public interface ProgramDao {
	
	
	public List<PendingRep> search(String uniqueId,String criteria);
	public boolean addProgram(PendingRep pendingRep, String stage);
	public int updateProgram(PendingRep pendingRep);
	
	

}
