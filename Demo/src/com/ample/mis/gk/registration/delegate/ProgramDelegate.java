package com.ample.mis.gk.registration.delegate;

import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.jsf.FacesContextUtils;

import com.ample.mis.gk.registration.bean.PendingRep;
import com.ample.mis.gk.registration.dao.ProgramDao;
import com.ample.mis.gk.registration.service.ProgramService;
import com.ample.mis.gk.registration.serviceImpl.ProgramServiceImpl;

/**
 * @author User 1
 *
 */
@Controller("delegate")
public class ProgramDelegate {

	/*@Autowired
	private ProgramService programService;
	
	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}*/

	private ProgramService programService= new ProgramServiceImpl();
	
	
	/**
	 * @param uniqueId entered in search combo
	 * @param criteria selected form drop down 
	 * @return list of reports for the first time and 
	 *         for second time it takes the uniqueId and criteria to get particular report
	 */
	public List<PendingRep> search(String uniqueId, String criteria) {
		/*ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		ProgramDao programDao = (ProgramDao) ctx.getBean("programDao");
		System.out.println(programDao);

		ApplicationContext context =   new ClassPathXmlApplicationContext("applicationContext.xml");
		ProgramService service = (ProgramService) context.getBean("programService");
		System.out.println(service);
		
		ProgramDao programDao1 = (ProgramDao) ctx.getBean("programDao");
		System.out.println(programDao1);*/
		return programService.search(uniqueId, criteria);
	}

	/**
	 * @param pendingRep
	 * @param stage
	 * @return
	 */
	public boolean addProgram(PendingRep pendingRep, String stage) {
		return programService.addProgram(pendingRep, stage);
	}

	/**
	 * @param pendingRep
	 * @return
	 */
	public int updateProgram(PendingRep pendingRep) {
		return programService.updateProgram(pendingRep);
	}
}
