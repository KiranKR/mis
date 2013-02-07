package com.Util;

import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="calendarBean")
@SessionScoped
public class CalendarBean {  
	

	String hourMinutes=rethrMt();
	
	String dayYear=returndayYear();
	
	public CalendarBean()
	{
		System.out.println("inside Calander Constructor");
	}
	
	
	 public String getDayYear() {
		return dayYear;
	}

	public void setDayYear(String dayYear) {
		this.dayYear = dayYear;
	}


	private Date date1;  
     
	    public Date getDate1() {  
	        return date1;  
	    }  
	  
	    public void setDate1(Date date1) {  
	        this.date1 = date1;  
	    }

		public String getHourMinutes() {
			this.hourMinutes=rethrMt();
			return hourMinutes;
		}

		public void setHourMinutes(String hourMinutes) {
			
			this.hourMinutes = hourMinutes;
		}  
		
	public	String rethrMt(){
	
		Calendar c = Calendar.getInstance();
	    Date date = new Date();
	    c.setTime(date);
	    String fullDate=c.getTime().toString().trim();
	    
		

	    
	  String hrMt=fullDate.substring(11, 13)+":"+fullDate.substring(14,16);
	  System.out.println("htMt:"+hrMt);
		
			return hrMt;
			
		}
	
	public String returndayYear()
	{
		Calendar c = Calendar.getInstance();
	    Date date = new Date();
	    c.setTime(date);
	    String fullDate=c.getTime().toString().trim();
	  
	 
	 
	    return day(fullDate.substring(0,3))+","+fullDate.substring(8,10)+"  "+month(fullDate.substring(4,7))+" "+fullDate.substring(24,28);
	}
	    
		
	    String day(String str){
	    	String day = null;
	    	if(str.equals("Mon"))
	    	{
	    		day="Monday";
	    	}
	    	else if(str.equals("Tue"))
	    	{
	    		day="Tuesday";
	    	}
	    	else if(str.equals("Wed"))
	    	{
	    		day="Wednesday";
	    	}
	    	else if(str.equals("Thu"))
	    	{
	    		day="Thursday";
	    	}
	    	else if(str.equals("Fri"))
	    	{
	    		day="Friday";
	    	}
	    	else if(str.equals("Sat"))
	    	{
	    		day="Saturday";
	    	}
	    	else if(str.equals("Sun"))
	    	{
	    		day="Sunday";
	    	}
			return day;
	    	
	    }
	    
	    String month(String str)
	    {String month=null;
	    if(str.equals("Jan"))
	    {
	    	month="January";
	    }
	    else  if(str.equals("Feb"))
	    {
	    	month="February";
	    }
	    else  if(str.equals("Mar"))
	    {
	    	month="March";
	    }
	    else  if(str.equals("Apr"))
	    {
	    	month="April";
	    }
	    else  if(str.equals("May"))
	    {
	    	month="May";
	    }
	    else  if(str.equals("Jun"))
	    {
	    	month="June";
	    }
	    else  if(str.equals("Jul"))
	    {
	    	month="July";
	    }
	    else  if(str.equals("Aug"))
	    {
	    	month="August";
	    }
	    else  if(str.equals("Sep"))
	    {
	    	month="September";
	    }
	    else  if(str.equals("Oct"))
	    {
	    	month="October";
	    }
	    else  if(str.equals("Nov"))
	    {
	    	month="November";
	    }
	    else  if(str.equals("Dec"))
	    {
	    	month="December";
	    }
	    	
	     return month;
	    }
	  
  
}  