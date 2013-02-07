package com.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;



public class AppUtil extends MysqlConnectionProvider{
	public static final String DATABASE_NAME = "MIS";

	@SuppressWarnings("rawtypes")
	public static List getDropDownList(String tableName, String orderById,String dbFldId, String dbFldValue, String whrClause,int skpDefaultVal) throws SQLException, ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + tableName + " "
				+ whrClause + " ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("0", "------None-----");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),
						rs.getString(dbFldValue));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getDropDownListWithRowStatus(String tableName, String orderById,
			String dbFldId, String dbFldValue, String whrClause,
			int skpDefaultVal) throws SQLException, ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + tableName + " WHERE " + whrClause + " = 0 ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("none", "------None-----");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),
						rs.getString(dbFldValue));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}
	
	
	public static List getDropDownListWithFk(String tableName, String orderById,
			String dbFldId, String dbFldValue, String dbFldKey, String whrClause,
			int skpDefaultVal) throws SQLException, ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + tableName + " "
				+ whrClause + " ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("none", "------None-----");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),rs.getString(dbFldValue),rs.getString(dbFldKey));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}

	/*public static List getEdHeadsList() throws SQLException, ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + "ed_heads" + " "
				+ " ORDER BY " + "ED_ID" + "";
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			EdHeadBean noneItem = new EdHeadBean();

		
			while (rs.next()) {
				noneItem = new EdHeadBean(rs.getInt("ED_ID"),
						rs.getString("ED_DESCRIPTION"),0);
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}*/
	
	
	public static List getDropDownListWithMultipleCol(String tableName,
			String orderById, String dbFldId, String dbFldValue, String dbFldDesc,String dbFldKey,
			String whrClause, int skpDefaultVal) throws SQLException,
			ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + tableName + " ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
		con = MysqlConnectionProvider.getNewConnection();
		pst = con.prepareStatement(qry);
		rs = pst.executeQuery();
		SelectItem noneItem = new SelectItem("none", "------None-----","");

		if (skpDefaultVal != 1) {
		lsDDList.add(noneItem);
		}
		if(whrClause=="cmplx"){
		while (rs.next()) {
		noneItem = new SelectItem(rs.getString(dbFldId)+"-"+rs.getString(dbFldDesc),rs.getString(dbFldValue),rs.getString(dbFldDesc));
		lsDDList.add(noneItem);
		}}
		else if (whrClause=="ward") {
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId)+"-"+rs.getString(dbFldDesc),rs.getString(dbFldDesc)+"-"+rs.getString(dbFldValue),rs.getString(dbFldKey));
				lsDDList.add(noneItem);
				}
		}
		else if (whrClause=="empward") {
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),rs.getString(dbFldDesc)+"-"+rs.getString(dbFldValue),rs.getString(dbFldKey));
				lsDDList.add(noneItem);
				}
		}
		else if (whrClause=="FK") {
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId)+"-"+rs.getString(dbFldDesc),rs.getString(dbFldValue),rs.getString(dbFldKey));
				lsDDList.add(noneItem);
				}
		}
		else
		{

		while (rs.next()) {
		noneItem = new SelectItem(rs.getString(dbFldId),rs.getString(dbFldValue)+"     "+rs.getString(dbFldDesc),rs.getString(dbFldDesc));
		lsDDList.add(noneItem);
		}
		}
		} catch (SQLException sqle) {
		throw sqle;
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		throw e;
		} finally {
		new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;



	}/*public static List getYrDropDownList(String tableName,
			String orderById,
			String whrClause) throws SQLException,
			ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + tableName + " "
				+ whrClause + " ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			WrkYrBean yrBean = new WrkYrBean();
			while (rs.next()) {
				yrBean = new WrkYrBean(rs.getInt(1),rs.getString(2),rs.getDate(3),rs.getDate(4),rs.getInt(5),rs.getString(6));
				lsDDList.add(yrBean);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}*/
	
	public static List getDropDownListByGroup(String tableName,
			String orderById, String dbFldId, String dbFldValue,
			String whrClause, int skpDefaultVal) throws SQLException,
			ClassNotFoundException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT * FROM " + dbName + "." + tableName + " "
				+ whrClause + " GROUP BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("none", "------None-----");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldValue));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}



	/*public static String getIdByUK(String tblName, String returnId,
			String whereClause) throws UDException, SQLException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT " + returnId + " FROM " + dbName + "." + tblName
				+ " " + whereClause + "";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = "";

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			while (rs.next()) {
				result = rs.getString(returnId);
			}
		} catch (Exception e) {
			UDException ex = DBExceptionHandler.rollbackAndLog(con, e);
			throw ex;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return result;
	}*/

	/*public static SelectItem getValueByUK(String tblName, String returnId,String returnValue,
			String whereClause) throws UDException, SQLException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT " + returnId + "," + returnValue + " FROM " + dbName + "." + tblName
				+ " " + whereClause + "";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = "";
		SelectItem item =null;
		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			 item = new SelectItem();
			while (rs.next()) {
				item = new SelectItem(rs.getString(returnId), rs.getString(returnValue));	
			}
		} catch (Exception e) {
			UDException ex = DBExceptionHandler.rollbackAndLog(con, e);
			throw ex;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return item;
	}*/
	/*public static InputStream getImageFromDB(String tableNme, String colName,
			String whereClause) throws UDException {
		String dbName = AppUtil.DATABASE_NAME;
		String qry = "SELECT " + colName + " FROM " + dbName + "." + tableNme
				+ " " + whereClause + "";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		InputStream dbProImg = null;
		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			while (rs.next())
				dbProImg = rs.getBinaryStream(1);
		} catch (Exception e) {
			UDException ex = DBExceptionHandler.rollbackAndLog(con, e);
			throw ex;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return dbProImg;
	}*/

	public static boolean checkPasswordStd(String password) {
		boolean result = false;
		int count = 0;
		int intChar = 0;
		char temp = ' ';
		for (int i = 0; i < password.length(); i++) {
			temp = password.charAt(i);
			intChar = (int) temp;
			if ((intChar > 122 || intChar < 97)
					&& (intChar > 90 || intChar < 65))
				count++;
		}
		if (count >= 2)
			result = true;
		return result;
	}

	public static String toTitleCase(String pString) {
		String[] str = pString.split(" ");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			buffer.append(initialCap(str[i]));
			if (i != str.length - 1) {
				buffer.append(" ");
			}
		}
		return buffer.toString();
	}

	private static String initialCap(String str) {
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1).toLowerCase();
	}

	public static String StringToDate(String pDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = (Date) formatter.parse(pDate);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date StringToMntYr(String pDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("MM/yyyy");
			Date date = (Date) formatter.parse(pDate);
			return date;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String StringDateToYear(String pDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("MMM dd,yyyy");
			Date date = (Date) formatter.parse(pDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			return sdf.format(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToString(Date pDate){
		DateFormat formatter ;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(pDate);
		return s;

		}

	public static String currentDate() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}
	
	public static String currentDateMntYr() {
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}
	
	public static int getRunningNo(String id,String tableName) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int runningNo = 0;
		
		try {
			String qry = "select max("+id+") from "+DATABASE_NAME+"."+tableName+"";
			con = getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				runningNo = rs.getInt(1);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally 
		{
			releaseConnection(rs,null,pst,con);			
		}
		
		return runningNo;
	}

	public static String getCurYear() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String frmYear = null;
		String toYear = null;
		String year = null;
		try {
			String qry = "select w.YEAR_VALUE,w.YEAR_MIN_VALUE from  "+DATABASE_NAME+".wrk_year w where YEAR_FROM_DATE < CURDATE() and YEAR_TO_DATE > CURDATE()";
			con = getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				frmYear = rs.getString(1);
				toYear = rs.getString(2);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally 
		{
			releaseConnection(rs,null,pst,con);			
		}
		
		return year=frmYear+"-"+toYear+"/";
	}
	
	public static String getMnthId(String month){
		if(month.equals("JANUARY")){
			return "01";
			}else if(month.equals("FEBRUARY")){
			return "02";
			}else if(month.equals("MARCH")){
			return "03";
			}else if(month.equals("APRIL")){
			return "04";
			}else if(month.equals("MAY")){
			return "05";
			}else if(month.equals("JUNE")){
			return "06";
			}else if(month.equals("JULY")){
			return "07";
			}else if(month.equals("AUGUST")){
			return "08";
			}else if(month.equals("SEPTEMBER")){
			return "09";
			}else if(month.equals("OCTOBER")){
			return "10";
			}else if(month.equals("NOVEMBER")){
			return "11";
			}else if(month.equals("DECEMBER")){
			return "12";
			}
		return "";
	}
	
	
	
	public static String getMonth(String month){
		if(month.equals("1")){
			return "JANUARY";
			}else if(month.equals("2")){
			return "FEBRUARY";
			}else if(month.equals("3")){
			return "MARCH";
			}else if(month.equals("4")){
			return "APRIL";
			}else if(month.equals("5")){
			return "MAY";
			}else if(month.equals("6")){
			return "JUNE";
			}else if(month.equals("7")){
			return "JULY";
			}else if(month.equals("8")){
			return "AUGUST";
			}else if(month.equals("9")){
			return "SEPTEMBER";
			}else if(month.equals("10")){
			return "OCTOBER";
			}else if(month.equals("11")){
			return "NOVEMBER";
			}else if(month.equals("12")){
			return "DECEMBER";
			}
		return "";
	}
	
	
	/*public static List searchByDesc(String desc, String tbNme, String clmnNme){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List beans = new ArrayList();
		try {
			String query = "";
			if(desc.equals("")){
				query = "SELECT * FROM "+DATABASE_NAME+"."+tbNme+"";
			}else{
				query = "SELECT *  FROM "+DATABASE_NAME+"."+tbNme+" WHERE  "+clmnNme+" LIKE '%"+desc+"%'";
			}
			
			con = getNewConnection();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()){
				beans.add(new MastrBean(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(rs,null,pst,con);			
		}
		return beans;
	}*/
	
	
	
}
