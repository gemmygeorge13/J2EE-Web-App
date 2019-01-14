package edu.uic.ids517.model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="dbAccessBean", eager=true)
@SessionScoped


public class DbAccessBean {

	private static final String [] TABLE_TYPES={"TABLE","VIEW"};
	private UserBean userBean;
	private Connection connection = null;
	private Statement pstatement = null;
	private ResultSet resultSet = null;
	private String result = "";
	private boolean errorMessageRendered;
	
	
	public boolean isErrorMessageRendered() {
		return errorMessageRendered;
	}

	public void setErrorMessageRendered(boolean errorMessageRendered) {
		this.errorMessageRendered = errorMessageRendered;
	}

	public DbAccessBean(){
		
	}
	
	public DbAccessBean(UserBean userBean) {
		super();
		this.userBean = userBean;
	}
	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public Connection getConnection() {
		return connection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean connect(){
		System.out.println("UserName:" + userBean.getUserName());
		FacesMessage message;
		FacesContext context;
		String driverName;
		String url = null;
		
		switch (userBean.getDatabase()) {
		case "Oracle":
			driverName = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + userBean.getHost() + ":1521:" +userBean.getSchema();
			break;
		case "MySql":
			driverName = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" +userBean.getHost() +":3306/"+ userBean.getSchema();
		case "DB2":
			driverName = "com.ibm.db2.jcc.DB2Driver";
			url = "jdbc:db2://" +userBean.getHost() + ":50000/" + userBean.getSchema();
		default:
			driverName = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" +userBean.getHost() +":3306/"+ userBean.getSchema();
		}
		
		try {
			
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, userBean.getUserName(), userBean.getPassword());
//			System.out.println("Connection: " + connection);
			System.out.println("Connection established");
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/faces/mainMenu.jsp");
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to find class: " + e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block	 
			System.out.println("Exception while getting connection: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		errorMessageRendered=false;
		message =new FacesMessage("Invalid credentials");
	    context = FacesContext.getCurrentInstance();
	    context.addMessage(null, message);
		return false;
	}
	
	public boolean executeQuery(String query){
		FacesMessage message;
		FacesContext context;
		try {
			pstatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			System.out.println("Query is:" + query);
			
			resultSet = pstatement.executeQuery(query);
		
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in executing query");
			message =new FacesMessage("Error in executing the query");
		    context = FacesContext.getCurrentInstance();
		    context.addMessage(null, message);
			return false;
		}
	}
	private String message;
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultSet getColumnNames(String sqlQuery)
	{
		try
		{
			executeQuery(sqlQuery);
			return resultSet;
		} catch (Exception e) {
			message = "Exception occurred: " + e.getMessage();
			return resultSet = null;
		}
	}
	
	public ResultSet getTables()
	{
		try {
			DatabaseMetaData meta = (DatabaseMetaData) connection.getMetaData();
			resultSet = meta.getTables(null, null, "%", null);
			return resultSet;
		} catch (SQLException se) {
			message = "Error Code: " + se.getErrorCode() + "\n" +
					"SQL State: " + se.getSQLState() + "\n" +
					"Message :" + se.getMessage() + "\n\n" +
					"SQLException while getting tables.";
			return resultSet = null;
		} catch (Exception e) {
			message = "Exception occurred: " + e.getMessage();
			return resultSet = null;
		}
	}
	
	public int processUpdate(String sqlQuery)
	{
		System.out.println("in processupdate");
		try {       
			int count = pstatement.executeUpdate(sqlQuery);
			return count;
		} catch (SQLException se) {
			message = "Error Code: " + se.getErrorCode() + "\n" +
					"SQL State: " + se.getSQLState() + "\n" +
					"Message :" + se.getMessage() + "\n\n" +
					"SQLException while processing query.";
			return -1;
		} catch (Exception e) {
			message = "Exception occurred: " + e.getMessage();
			return -1;
		   }
	}
	
	public boolean executeUpdate(String query){
		FacesMessage message;
		FacesContext context;
		try {
			pstatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			System.out.println("Query is:" + query);
			
			pstatement.executeUpdate(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in executing query");
			message =new FacesMessage("Error in executing the query");
		    context = FacesContext.getCurrentInstance();
		    context.addMessage(null, message);
			return false;
		}
	}
	
	public ArrayList<String> getTableList(){
		getConnection();
		System.out.println("Connection is " + connection);
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet tables = databaseMetaData.getTables(null, userBean.getSchema(), 
					null, TABLE_TYPES);
			if(tables != null){
				ArrayList<String> tableList = new ArrayList<>();
				String tableName = "";
				while(tables.next()){
					tableName = tables.getString("TABLE_NAME");
					if(!userBean.getDatabase().equalsIgnoreCase("oracle")
							|| tableName.length()<4)
						tableList.add(tableName);
					else if(!tableName.substring(0,4).
							equalsIgnoreCase("BIN$"))
						tableList.add(tableName);
				}
				return tableList;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while executing query : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Method to display all the column names of a user selected table
	 * @param table
	 * @return
	 */
	public ArrayList<String> getColumnsInTable(String table){
		getConnection();
		try {
			ArrayList<String> columnList = new ArrayList<>();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet columns = databaseMetaData.getColumns(
					null, userBean.getUserName(), table, null);
			if(columns != null){
				String columnName = "";
				while(columns.next()){
					columnName = columns.getString("COLUMN_NAME");
					columnList.add(columnName);
				}
				for (String name:columnList){
					System.out.println(name);
				}
			}
			return columnList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while displaying columns :" + e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<String> getNumColumnsInTable(String table){
		try {
			ArrayList<String> numColumnList = new ArrayList<>();
			pstatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			//String queryToExecute2 =  "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + table+"';"; //+"' and DATA_TYPE IN ('float', 'decimal', 'int', 'smallint', 'double', 'tinyint', 'mediumint', 'bigint', 'numeric');";
			
			String queryToExecute =  "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + table +"' and DATA_TYPE IN ('float', 'decimal', 'int', 'smallint', 'double', 'tinyint', 'mediumint', 'bigint', 'numeric');";
			resultSet = pstatement.executeQuery(queryToExecute);
			String columnNames = "";
			System.out.println(" ---------- HERE ------");
			while(resultSet.next()) {
				columnNames = resultSet.getString("COLUMN_NAME");
				System.out.println(resultSet.getString("COLUMN_NAME"));
				numColumnList.add(columnNames);
			}
			
			return numColumnList;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in executing num column query");
		}
		
		return null;
	}
	
	public ArrayList<String> getCatColumnsInTable(String table){
		try {
			ArrayList<String> catColumnList = new ArrayList<>();
			pstatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			String queryToExecute =  "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + table +"' and DATA_TYPE NOT IN ('float', 'decimal', 'int', 'smallint', 'tinyint', 'mediumint', 'bigint', 'numeric');";
			resultSet = pstatement.executeQuery(queryToExecute);
			String columnNames = "";
			while(resultSet.next()) {
				columnNames = resultSet.getString("COLUMN_NAME");
				catColumnList.add(columnNames);
			}
			
			return catColumnList;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in executing num column query");
		}
		
		return null;
	}
	
	public ArrayList<Integer> getColumnsTypeInTable(String table){
		getConnection();
		try {
			ArrayList<Integer> columnTypeList = new ArrayList<>();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet columns = databaseMetaData.getColumns(
					null, userBean.getUserName(), table, null);
			if(columns != null){
				Integer columnType = 0;
				while(columns.next()){
					columnType = columns.getInt("DATA_TYPE");
					columnTypeList.add(columnType);
				}
				for (int type:columnTypeList){
					System.out.println(type);
				}
			}
			return columnTypeList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while displaying columns :" + e.getMessage());
		}
		
		return null;
	}
	
	public boolean closeConnection(){
		try {
			if(connection !=null && !connection.isClosed()){
				connection.close();
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.invalidateSession();
				ec.redirect(ec.getRequestContextPath() + "/faces/index.html");
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
