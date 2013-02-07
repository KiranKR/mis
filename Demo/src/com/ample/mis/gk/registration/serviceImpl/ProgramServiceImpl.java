package com.ample.mis.gk.registration.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ample.mis.gk.registration.bean.PendingRep;
import com.ample.mis.gk.registration.dao.ProgramDao;
import com.ample.mis.gk.registration.daoImpl.ProgramDaoImpl;
import com.ample.mis.gk.registration.service.ProgramService;

@Service("programService")
public class ProgramServiceImpl implements ProgramService{

	/*@Autowired
	private ProgramDao programDao;
	
	
	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}*/

	private ProgramDao programDao = new ProgramDaoImpl();
	
	public List<PendingRep> search(String uniqueId, String criteria) {
		return programDao.search(uniqueId, criteria);
	}

	public boolean addProgram(PendingRep pendingRep, String stage) {
		return programDao.addProgram(pendingRep, stage);
	}

	public int updateProgram(PendingRep pendingRep) {
		return programDao.updateProgram(pendingRep);
	}

}
