package com.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.faces.application.ViewExpiredException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class SessionFilter implements Filter { 
	
	private ArrayList<String> urlList; 
	private String timeOutPage = "IntermediateSessionPage.faces" ;
	private String logOutPage ="pages/login.jsf";
	private String errorPage ="pages/ErrMessage.jsf";
	
	public String getTimeOutPage() {
		return timeOutPage;
	}

	public void setTimeOutPage(String timeOutPage) {
		this.timeOutPage = timeOutPage;
	}


	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getLogOutPage() {
		return logOutPage;
	}

	public void setLogOutPage(String logOutPage) {
		this.logOutPage = logOutPage;
	}

	public void destroy() { 
	} 
	
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) { 
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) res; 
	
		String timeoutUrl = request.getContextPath() + "/" + getLogOutPage();
		String errorOutUrl = request.getContextPath() + "/" + getErrorPage();
		try {
		String url = request.getServletPath(); 
		boolean allowedRequest = false; 
		
		if(urlList.contains(url)) { 
			allowedRequest = true; 
		} 
		if (!allowedRequest) 
		{ 
			HttpSession session = request.getSession(false); 
			if (null == session) 
			{ 
				
				System.out.println("Session is invalid! redirecting to timeoutpage : " + timeoutUrl);
			/*	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);	*/
				response.sendRedirect(timeoutUrl);
				return;
			} 
		}
		
		
			chain.doFilter(req, res);
		} catch (IOException e) {
			System.out.println("*************************************IOException");
			e.printStackTrace();
			try {
				response.sendRedirect(timeoutUrl);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}catch (ViewExpiredException e) {
			System.out.println("*************************************ViewExpiredException");
			e.printStackTrace();
			try {
				response.sendRedirect(timeoutUrl);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}catch (Exception e) {
			System.out.println("*************************************Exception");
			e.printStackTrace();
			try {
				//response.sendRedirect(errorOutUrl);
				response.sendRedirect(timeoutUrl);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}  
	} 
	
	public void init(FilterConfig config) throws ServletException { 
		String urls = config.getInitParameter("avoid-urls"); 
		StringTokenizer token = new StringTokenizer(urls, ","); 
		urlList = new ArrayList<String>(); 
		while (token.hasMoreTokens()) { 
			urlList.add(token.nextToken()); 
		} 
	} 
}
