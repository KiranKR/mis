package com.ample.mis.gk.registration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ample.mis.gk.registration.bean.PendingRep;

public interface ProgramService {

	public List<PendingRep> search(String uniqueId,String criteria);
	public boolean addProgram(PendingRep pendingRep, String stage);
	public int updateProgram(PendingRep pendingRep);
}
