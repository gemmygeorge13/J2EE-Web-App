package edu.uic.ids517.model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.ResultSetMetaData;

import java.io.FileWriter;


//import com.mysql.cj.jdbc.result.ResultSetMetaData;

@ManagedBean(name = "actionBean", eager = true)
@SessionScoped

public class ActionBean implements ActionListener{
	
	private DbAccessBean dbAccessBean;
	private UserBean userBean;
	private HttpSession session;

	//private UploadedFile uploadedFile;
	private String fileLabel;
	private String tableName;
	private ArrayList<String> selectedColumnNames;
	private ArrayList<String> selectedColumnStats;


	private ArrayList<String> statsString;
	
	private String uploadTableName;
	private String uploadFileType;
	

	private String response;
	private boolean tableListRendered;
	private List<String> tableViewList;
	private List<String> columnNames;
	private List<String> numColumnNames;
	private List<String> numColumnNamesAll;
	private List<String> catColumnNames;
	private List<String> catColumnNamesAll;
	private List<Integer> columnTypes;
	private List<String> selectedColumnNamesScatterPlot;
	
	private HashMap<String, Integer> columnNumbers;
	private HashMap<String, Integer> uploadColumnNumbers;
	private int rowCount;
	private String pieCat;
	private String pieNum;
	private String barCat1;
	private String barCat2;
	private String barNum;
	private String hist;
	private HashMap<String, Integer> accessLogColumnNumbers;
	
	private boolean columnListRendered;
	private boolean numColumnListRendered;
	private boolean renderTableData;
	private boolean render = false;
	private Result result;
	private ArrayList<ArrayList<String>> tableData;
	private ArrayList<ArrayList<String>> uploadFileData;
	private boolean renderUploadFile;
	private ArrayList<String> uploadFileColumnNames;
	private ArrayList<StatsBean> statsList;
	private boolean renderRegressionResult;
	private boolean renderCorrelationResult;

	private double pearsonCorr;
	private double kendallCorr;
	private double spearmanCorr;

	private double vif;
	private String createTableName;
	private String createTableColumnNames;
	private String dropTableName;
	private  ArrayList<String> metadataColumns;
	private ArrayList<String> statsColumns;
	private ArrayList<ArrayList<String>> statsStringList;
	
	private boolean renderAccessLogs;
	private ArrayList<ArrayList<String>> accessLogData;
	private ArrayList<String> accessLogColumnNames;
	
	// Declare all variables
	private String message;
	private String regEquation;
	private double slopeStdErr;
	private double tStatPred;
	private double pValuePred;
	private double fValue;
	private double pValue;
	private double stdErrorM;
	private double rSquare;
	private double rSquareAdj;
	private double intercept;
	private double interceptStdErr;
	private double tStatistic;
	private double interceptPValue;
	private double slope;
	private double predictorDegreesFreedom;
	private double residualErrorDegreesFreedom;
	private double totalDegreesFreedom;
	private double regSumSquares;
	private double sumSquaredErr;
	private double totalSumSquares;
	private double meanSquare;
	private double meanSquareErr;
	
	private String scriptFilePath;
	private FileWriter fw;
	private StringBuffer sbScriptFile;
	private ActionBeanGraph actionBeanGraph;
	
	private String content;
	
	public String getSession() {
		return session.getId();
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		System.out.print("value set");
		
		this.content = content;
		System.out.println("in content setter"+content);
	}

	public ActionBeanGraph getActionBeanGraph() {
		return actionBeanGraph;
	}

	public void setActionBeanGraph(ActionBeanGraph actionBeanGraph) {
		this.actionBeanGraph = actionBeanGraph;
	}

	public List<String> getSelectedColumnNamesScatterPlot() {
		return selectedColumnNamesScatterPlot;
	}

	public void setSelectedColumnNamesScatterPlot(List<String> selectedColumnNamesScatterPlot) {
		this.selectedColumnNamesScatterPlot = selectedColumnNamesScatterPlot;
	}

	public ArrayList<String> getSelectedColumnStats() {
		System.out.print("getter for column stats-----------");
		return selectedColumnStats;
	}

	public void setSelectedColumnStats(ArrayList<String> selectedColumnStats) {
		System.out.print("setter for stat columns-----");
		this.selectedColumnStats = selectedColumnStats;
	}
	public StringBuffer getSbScriptFile() {
		return sbScriptFile;
	}

	public void setSbScriptFile(StringBuffer sbScriptFile) {
		this.sbScriptFile = sbScriptFile;
	}

	public void appendSbScriptFile(StringBuffer sbScriptFile) {
		this.sbScriptFile = sbScriptFile.append(sbScriptFile);
	}

	
	
	public String getUploadTableName() {
		return uploadTableName;
	}

	public void setUploadTableName(String uploadTableName) {
		this.uploadTableName = uploadTableName;
	}

	public String getUploadFileType() {
		return uploadFileType;
	}

	public void setUploadFileType(String uploadFileType) {
		this.uploadFileType = uploadFileType;
	}

	public List<Integer> getColumnTypes() {
		return columnTypes;
	}

	public void setColumnTypes(List<Integer> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public ArrayList<String> getStatsString() {
		return statsString;
	}

	public void setStatsString(ArrayList<String> statsString) {
		this.statsString = statsString;
	}

	public String getPredictor() {
		return predictor;
	}

	public void setPredictor(String predictor) {
		this.predictor = predictor;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	private String predictor;
	


	
	public String getHist() {
		return hist;
	}

	public void setHist(String hist) {
		this.hist = hist;
	}

	
	public HashMap<String, Integer> getAccessLogColumnNumbers() {
		return accessLogColumnNumbers;
	}

	public void setAccessLogColumnNumbers(HashMap<String, Integer> accessLogColumnNumbers) {
		this.accessLogColumnNumbers = accessLogColumnNumbers;
	}

	public String getPieCat() {
		return pieCat;
	}

	public void setPieCat(String pieCat) {
		this.pieCat = pieCat;
	}

	public String getPieNum() {
		return pieNum;
	}

	public void setPieNum(String pieNum) {
		this.pieNum = pieNum;
	}

	public String getBarCat1() {
		return barCat1;
	}

	public void setBarCat1(String barCat1) {
		this.barCat1 = barCat1;
	}

	public String getBarCat2() {
		return barCat2;
	}

	public void setBarCat2(String barCat2) {
		this.barCat2 = barCat2;
	}

	public String getBarNum() {
		return barNum;
	}

	public void setBarNum(String barNum) {
		this.barNum = barNum;
	}

	public List<String> getCatColumnNames() {
		return catColumnNames;
	}

	public void setCatColumnNames(List<String> catColumnNames) {
		this.catColumnNames = catColumnNames;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public HashMap<String, Integer> getUploadColumnNumbers() {
		return uploadColumnNumbers;
	}

	public void setUploadColumnNumbers(HashMap<String, Integer> uploadColumnNumbers) {
		this.uploadColumnNumbers = uploadColumnNumbers;
	}

	private static Logger logFile;
	private String ipAddress;

	public HashMap<String, Integer> getColumnNumbers() {
		return columnNumbers;
	}

	public void setColumnNumbers(HashMap<String, Integer> columnNumbers) {
		this.columnNumbers = columnNumbers;
	}

	public double getPearsonCorr() {
		return pearsonCorr;
	}

	public double getKendallCorr() {
		return kendallCorr;
	}

	public double getSpearmanCorr() {
		return spearmanCorr;
	}
	
	public boolean isRenderCorrelationResult() {
		return renderCorrelationResult;
	}

	public void setRenderCorrelationResult(boolean renderCorrelationResult) {
		this.renderCorrelationResult = renderCorrelationResult;
	}
	
	public boolean isRenderAccessLogs() {
		return renderAccessLogs;
	}

	public void setRenderAccessLogs(boolean renderAccessLogs) {
		this.renderAccessLogs = renderAccessLogs;
	}

	public ArrayList<ArrayList<String>> getAccessLogData() {
		return accessLogData;
	}

	public void setAccessLogData(ArrayList<ArrayList<String>> accessLogData) {
		this.accessLogData = accessLogData;
	}

	public ArrayList<String> getAccessLogColumnNames() {
		return accessLogColumnNames;
	}

	public void setAccessLogColumnNames(ArrayList<String> accessLogColumnNames) {
		this.accessLogColumnNames = accessLogColumnNames;
	}

	public List<String> getNumColumnNames() {
		return numColumnNames;
	}

	public void setNumColumnNames(List<String> numColumnNames) {
		this.numColumnNames = numColumnNames;
	}

	public boolean isNumColumnListRendered() {
		return numColumnListRendered;
	}

	public void setNumColumnListRendered(boolean numColumnListRendered) {
		this.numColumnListRendered = numColumnListRendered;
	}

	public ArrayList<ArrayList<String>> getStatsStringList() {
		return statsStringList;
	}

	public void setStatsStringList(ArrayList<ArrayList<String>> statsStringList) {
		this.statsStringList = statsStringList;
	}

	public ArrayList<String> getStatsColumns() {
		return statsColumns;
	}

	public void setStatsColumns(ArrayList<String> statsColumns) {
		this.statsColumns = statsColumns;
	}

	public ArrayList<String> getMetadataColumns() {
		return metadataColumns;
	}

	public void setMetadataColumns(ArrayList<String> metadataColumns) {
		this.metadataColumns = metadataColumns;
	}

	public String getCreateTableName() {
		return createTableName;
	}

	public void setCreateTableName(String createTableName) {
		this.createTableName = createTableName;
	}

	public String getCreateTableColumnNames() {
		return createTableColumnNames;
	}

	public void setCreateTableColumnNames(String createTableColumnNames) {
		this.createTableColumnNames = createTableColumnNames;
	}

	public String getDropTableName() {
		return dropTableName;
	}

	public void setDropTableName(String dropTableName) {
		this.dropTableName = dropTableName;
	}

	public boolean isRenderRegressionResult() {
		return renderRegressionResult;
	}

	public void setRenderRegressionResult(boolean renderRegressionResult) {
		this.renderRegressionResult = renderRegressionResult;
	}

	public String getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}

	public ArrayList<String> getSelectedColumnNames() {
		System.out.println("Getter selected column names--------------");
		return selectedColumnNames;
	}

	public boolean isRenderTableData() {
		return renderTableData;
	}

	public void setRenderTableData(boolean renderTableData) {
		this.renderTableData = renderTableData;
	}

	public void setSelectedColumnNames(ArrayList<String> selectedColumnNames) {
		System.out.print("setter column names---------------------");
		this.selectedColumnNames = selectedColumnNames;
	}
	
	@PostConstruct
	public void init() {
		
		userBean = new UserBean();
		dbAccessBean = new DbAccessBean(userBean);
		logFile = Logger.getLogger(ActionBean.class.getName());
		FacesContext context = FacesContext.getCurrentInstance();
		sbScriptFile = new StringBuffer();
		try {

			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
			//HttpServletRequest requestSession = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			session = request.getSession();

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public DbAccessBean getDbAccessBean() {
		return dbAccessBean;
	}

	public void setDbAccessBean(DbAccessBean dbAccessBean) {
		this.dbAccessBean = dbAccessBean;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		System.out.println("set"+tableName);
		this.tableName = tableName;
	}

	public boolean isTableListRendered() {
		return tableListRendered;
	}

	public void setTableListRendered(boolean tableListRendered) {
		this.tableListRendered = tableListRendered;
	}

	public List<String> getTableViewList() {
		return tableViewList;
	}

	public void setTableViewList(List<String> tableViewList) {
		this.tableViewList = tableViewList;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public boolean isColumnListRendered() {
		return columnListRendered;
	}

	public void setColumnListRendered(boolean columnListRendered) {
		this.columnListRendered = columnListRendered;
	}

	public void setResult(Result result) {
		this.result = result;
	}


	public ArrayList<ArrayList<String>> getTableData() {
		return tableData;
	}

	public void setTableData(ArrayList<ArrayList<String>> tableData) {
		this.tableData = tableData;
	}
	
	public Result getResult() {
		return result;
	}

	public ActionBean() {
		//userBean = new UserBean();
		//dbAccessBean = new DbAccessBean(userBean);
		tableListRendered = false;
		columnListRendered = false;
		renderTableData = false;
		render = false;
		
	}

	public void connect() {
		logFile.info("Connecting to the database...");
		boolean success = dbAccessBean.connect();
		if (success) {
			logFile.info("Connection to the " + dbAccessBean.getUserBean().getSchema() + " schema successful");
			getTableList();
		}
		
		
	}

	public void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	    			
	    		   file.delete();
	    		   System.out.println("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	    			
	    		}else{
	    			
	    		   //list all the directory contents
	        	   String files[] = file.list();
	     
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	        		 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	        		
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	    		
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
	
	public void closeConnection() {
		
		
		FacesContext fc = FacesContext.getCurrentInstance();
		String path = fc.getExternalContext().getRealPath("/temp"+this.getSession());
		String SRC_FOLDER = path;
		File directory = new File(SRC_FOLDER);
		if(directory.exists()){
	           try{
	        	   
	               delete(directory);
	        	
	           }catch(IOException e){
	               e.printStackTrace();
	               System.exit(0);
	           }
		}
		dbAccessBean.closeConnection();
	}

	public void getTableList() {
		tableViewList = dbAccessBean.getTableList();
		//sbScriptFile.append("tableList \r\n");
		for (String name : tableViewList) {
			System.out.println(name);
			
		}
		if (tableViewList != null)
			tableListRendered = true;
	}

	
	
	public void getColumnsInTable() {
		System.out.println("Table: "+tableName);
		sbScriptFile.append("tableList \r\n");
		sbScriptFile.append("use "+tableName+"\r\n");
		columnNames = dbAccessBean.getColumnsInTable(tableName);
		
		if (columnNames != null) {
			columnListRendered = true;
		}
		else {
			
		}
		
		numColumnNamesAll = dbAccessBean.getNumColumnsInTable(tableName);
		System.out.println("Num Columns: "+numColumnNamesAll);
		
		if (numColumnNamesAll != null) {
			numColumnListRendered = true;
		}
		else {
			
		}
		System.out.println("Cat Columns");
		catColumnNamesAll = dbAccessBean.getCatColumnsInTable(tableName);
		if (numColumnNamesAll != null) {
			numColumnListRendered = true;
		}
		else {
			
		}
			
	}
	
	
	public boolean getTableMetaData() {
		
		render = false;
		String query;
		
		query = "select count(*) from " + tableName + ";";
		dbAccessBean.executeQuery(query);
		ResultSet resultSet = dbAccessBean.getResultSet();
		
		try {
			while(resultSet.next()) {
				if (resultSet.getObject(1) != null) {
					rowCount = Integer.parseInt(resultSet.getObject(1).toString());
				} else {	
					rowCount = 0;
			}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		metadataColumns = new ArrayList();
		metadataColumns.add("Column Name");
		metadataColumns.add("Data Type");
		metadataColumns.add("Char Length");
		metadataColumns.add("Num Scale");
		metadataColumns.add("Num Precision");
		metadataColumns.add("Nullable");
		tableData = new ArrayList();
		
		
		query = "SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, IS_NULLABLE  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + tableName +"';";
		dbAccessBean.executeQuery(query);
		resultSet = dbAccessBean.getResultSet();
		columnNumbers = new HashMap<>();
		for (int i = 0; i < 6; i++) {
			columnNumbers.put(metadataColumns.get(i), i);
		}
		try {
			while (resultSet.next()) {
				ArrayList<String> data = new ArrayList();
				for (int i = 1; i <= 6; i++) {
					if (resultSet.getObject(i) != null) {
						data.add(resultSet.getObject(i).toString());
					} else
						data.add("-");
				}
				tableData.add(data);
			}
			System.out.println("Table data is ready :" + tableData);
			renderTableData = true;
			getColumnsInTable();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean getTableDataForScatterPlot() {
		
		StringBuilder sb = new StringBuilder();
		tableData = new ArrayList();
		try {
		System.out.println("1");
		System.out.println("2");
		renderTableData=false;
		selectedColumnNamesScatterPlot = new ArrayList<String>();
		System.out.println(predictor);
		System.out.println(response);
		selectedColumnNamesScatterPlot.add(predictor);
		selectedColumnNamesScatterPlot.add(response);
		System.out.println("Here");
		String query;
		sbScriptFile.append("scatterplot \t ");
		for (int i = 0; i < selectedColumnNamesScatterPlot.size(); i++) {
			if (i > 0) {
				sb = sb.append(", ").append(selectedColumnNamesScatterPlot.get(i));
				sbScriptFile.append(selectedColumnNamesScatterPlot.get(i)+" \t");
			} else {
				sb = sb.append(selectedColumnNamesScatterPlot.get(i));
				sbScriptFile.append(selectedColumnNamesScatterPlot.get(i)+" \t");
			}
		}
		sbScriptFile.append("\r\n");
		System.out.println("column names: " + sb);
		query = "Select " + sb.toString() + " from " + tableName;
		dbAccessBean.executeQuery(query);
		ResultSet resultSet = dbAccessBean.getResultSet();
		System.out.println("Here 2 ");
			while (resultSet.next()) {
				ArrayList<String> data = new ArrayList<String>();
				for (int i = 1; i <= selectedColumnNamesScatterPlot.size(); i++) {
					if (resultSet.getObject(i) != null) {
						data.add(resultSet.getObject(i).toString());
					} else
						data.add("");
				}
				tableData.add(data);
			}
			System.out.println("Table data is ready :" + tableData);
			System.out.println("predictor in actionbean "+predictor);
			System.out.println("response in actionbean "+response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Table data is ready :" + tableData);
		return true;
	}
	

public boolean getTableDataForHistogram() {
	
	StringBuilder sb = new StringBuilder();
	String query;
	try {
	tableData = new ArrayList<>();
	System.out.println("1");
	System.out.println("2");
	sbScriptFile.append("hist \t"+hist+"\r\n");
	sb = sb.append("select ").append(hist).append(" from ").append(tableName);
	query = sb.toString();
	System.out.println(query +"----------------");
	dbAccessBean.executeQuery(query);
	ResultSet resultSet = dbAccessBean.getResultSet();
	renderTableData=false;
	
		while(resultSet.next()) {
			ArrayList<String> data = new ArrayList<>();
			for (int i = 1; i <= 1; i++) {
				if (resultSet.getObject(i) != null) {
					data.add(resultSet.getObject(i).toString());
				} else
					data.add("");
			}
			tableData.add(data);
		}
		System.out.println("Table data is ready :" + tableData);
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	
	
}
	
	@SuppressWarnings("unchecked")
	public boolean displayColumnValues() {
		render = false;
		this.renderFalse();
		StringBuilder sb = new StringBuilder();
		tableData = new ArrayList<>();
		String query;
		sbScriptFile.append("columnList \r\n");
		sbScriptFile.append("select \t");
		for (int i = 0; i < selectedColumnNames.size(); i++) {
			if (i > 0) {
				sb = sb.append(", ").append(selectedColumnNames.get(i));
				sbScriptFile.append(selectedColumnNames.get(i)+" \t");
			} else {
				sb = sb.append(selectedColumnNames.get(i));
				sbScriptFile.append(selectedColumnNames.get(i)+" \t");
			}
		}
		sbScriptFile.append("\r\n");
		System.out.println("column names: " + sb);
		
		query = "Select " + sb.toString() + " from " + tableName;
		dbAccessBean.executeQuery(query);
		ResultSet resultSet = dbAccessBean.getResultSet();
		try {
			columnNumbers = new HashMap();
			for (int i = 0; i < selectedColumnNames.size(); i++) {
				columnNumbers.put(selectedColumnNames.get(i), i);
			}
			while (resultSet.next()) {
				ArrayList<String> data = new ArrayList<>();
				for (int i = 1; i <= selectedColumnNames.size(); i++) {
					if (resultSet.getObject(i) != null) {
						data.add(resultSet.getObject(i).toString());
					} else
						data.add("");
				}
				tableData.add(data);
			}
			
			numColumnNames=new ArrayList<String>() ;
			catColumnNames=new ArrayList<String>() ;
			
			ArrayList<String> numColumnNamesAllTemp = new ArrayList();
			for (String name: numColumnNamesAll) {
				
					numColumnNamesAllTemp.add(name.toLowerCase());
					
			}
			
			for (String name: selectedColumnNames) {
				if (numColumnNamesAllTemp.contains(name.toLowerCase())) {
					numColumnNames.add(name);
					System.out.print("Num col"+name);
				}
				if (catColumnNamesAll.contains(name)) {
					catColumnNames.add(name);
					System.out.print(name+"  ---categorical selected");
				}
			}
			System.out.println("Table data is ready :" + tableData);
			renderTableData = true;
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public void updateTableColumn() {
		numColumnNames=new ArrayList<String>() ;
		catColumnNames=new ArrayList<String>() ;
		for (String name: selectedColumnNames) {
			if (numColumnNamesAll.contains(name)) {
				numColumnNames.add(name);
				System.out.print(name);
			}
			if (catColumnNamesAll.contains(name)) {
				catColumnNames.add(name);
				System.out.print(name+"  ---categorical selected");
			}
		}

	}
	
	public void renderFalse() {
		tableListRendered = false;
		columnListRendered = false;
		renderTableData = false;
		render = false;
		renderUploadFile = false;
		renderRegressionResult = false;
		renderCorrelationResult = false;
		
	}
	

	
	
	public boolean createTable() {
		FacesMessage message;
		FacesContext context;
		
		String colName[] = createTableColumnNames.split(",");
		String query = "create table "+userBean.getSchema()+"." + createTableName +" (";
		for(int i = 0; i<colName.length; i++) {
			if(i == 0) {
				query = query + colName[i] + " DECIMAL(16,2)";
			}
			else if(i == colName.length - 1) {
				query = query + ", " + colName[i] + " DECIMAL(16,2));";
			}
			else {
				query = query + ", " + colName[i] + " DECIMAL(16,2)";
			}
		}
			boolean executed = dbAccessBean.executeUpdate(query);
			if(executed) {
				message =new FacesMessage("Table Created successfully");
			    context = FacesContext.getCurrentInstance();
			    context.addMessage(null, message);
				return true;
			}
			else {
				message =new FacesMessage("Error in table creation");
			    context = FacesContext.getCurrentInstance();
			    context.addMessage(null, message);
				return false;
			}
		
	}
	
	public boolean dropTable() {
		FacesMessage message;
		FacesContext context;
		String query = "drop table "+userBean.getSchema()+"." + dropTableName;
			
			boolean executed = dbAccessBean.executeUpdate(query);
			if(executed) {
				message =new FacesMessage("Table Dropped successfully");
			    context = FacesContext.getCurrentInstance();
			    context.addMessage(null, message);
				return true;
			}
			else {
				message =new FacesMessage("Error in dropping table");
			    context = FacesContext.getCurrentInstance();
			    context.addMessage(null, message);
				return false;
			}
	}
	
	
 private boolean regRendered = false;
 
	public boolean isRegRendered() {
	return regRendered;
}

public void setRegRendered(boolean regRendered) {
	this.regRendered = regRendered;
}

	public String generateRegressionReport() {

		if (calculateRegressionVariables()) {
			System.out.println("Calculated");
			return "SUCCESS";
		} else {
			System.out.println("Reg failed");
			return "FAIL";
		}
			
	}

	public String showRegressionTables() {

		setRegRendered(true);
		return "SUCCESS";
	}
	
	public boolean calculateCorrelation() {
		renderTableData=false;
		 System.out.println("One--");
		XYSeries predSeries = new XYSeries("predictor");
		XYSeries resSeries = new XYSeries("response");
		XYSeriesCollection xyTimeSeriesCol = new XYSeriesCollection();
		XYSeries xySeries = new XYSeries("Random");
		try {
			StringBuilder sb = new StringBuilder();
			String query;
			System.out.println(predictor);
			System.out.println(response);
					sb = sb.append(predictor);sb = sb.append(" , ");
					sb = sb.append(response);
			
			System.out.println("column names: " + sb);
			sbScriptFile.append("corr "+predictor+" \t"+response+"\r\n");
			query = "Select " + sb.toString() + " from " + tableName;
			dbAccessBean.executeQuery(query);
			ResultSet resultSet = dbAccessBean.getResultSet();
			 System.out.println("Two -------------");
			if (resultSet != null) 
			{
		
				ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
				String predictorName = resultSetMetaData.getColumnTypeName(1);
				String responseName = resultSetMetaData.getColumnTypeName(2);
				List<Double> predictorList = new ArrayList<Double>();
				List<Double> responseList = new ArrayList<Double>();
				while (resultSet.next()) {
					switch (predictorName.toLowerCase()) {
					case "int":
						predictorList.add((double) resultSet.getInt(1));
						break;
					case "smallint":
						predictorList.add((double) resultSet.getInt(1));
						break;
					case "float":
						predictorList.add((double) resultSet.getFloat(1));
						break;
					case "double":
						predictorList.add((double) resultSet.getDouble(1));
						break;
					case "long":
						predictorList.add((double) resultSet.getLong(1));
						break;
					default:
						predictorList.add((double) resultSet.getDouble(1));
						break;
					}
					switch (responseName.toLowerCase()) {
					case "int":
						responseList.add((double) resultSet.getInt(2));
						break;
					case "smallint":
						responseList.add((double) resultSet.getInt(2));
						break;
					case "float":
						responseList.add((double) resultSet.getFloat(2));
						break;
					case "double":
						responseList.add((double) resultSet.getDouble(2));
						break;
					case "long":
						responseList.add((double) resultSet.getLong(2));
						break;
					default:
						responseList.add((double) resultSet.getDouble(2));
						break;
					}
				}
				double[] predictorArray = new double[predictorList.size()];
				for (int i = 0; i < predictorList.size(); i++) {
					predictorArray[i] = (double) predictorList.get(i);
					predSeries.add(i + 1, (double) predictorList.get(i));
				}
				double[] responseArray = new double[responseList.size()];
				for (int i = 0; i < responseList.size(); i++) {
					responseArray[i] = (double) responseList.get(i);
					resSeries.add(i + 1, (double) responseList.get(i));
				}
				//System.out.println("Three -------------");
		PearsonsCorrelation p = new PearsonsCorrelation();
				pearsonCorr =p.correlation(predictorArray,responseArray);
				KendallsCorrelation k = new KendallsCorrelation();
				kendallCorr = k.correlation(predictorArray,responseArray);
				SpearmansCorrelation s = new SpearmansCorrelation();
				spearmanCorr = s.correlation(predictorArray,responseArray);
	    System.out.println("FOUR --------" + pearsonCorr);
	    renderCorrelationResult = true;
	    System.out.println(sbScriptFile);
			}
		}
		catch(Exception e) {}
	   // System.out.println("Kendall's Correlation: " + new KendallsCorrelation().correlation(x,y));
		return true;
	
	}
	
	public boolean calculateRegressionVariables() {
		renderTableData=false;

		XYSeries predSeries = new XYSeries("Predictor");
		XYSeries resSeries = new XYSeries("Response");
		XYSeriesCollection xyTimeSeriesCol = new XYSeriesCollection();
		XYSeries xySeries = new XYSeries("Random");
		try {
			StringBuilder sb = new StringBuilder();
			String query;
			System.out.println(predictor);System.out.println(response);
					sb = sb.append(predictor);sb = sb.append(" , ");
					sb = sb.append(response);
			
			System.out.println("column names: " + sb);
			sbScriptFile.append("regress \t"+predictor+" \t"+response+"\r\n");
			query = "Select " + sb.toString() + " from " + tableName;
			dbAccessBean.executeQuery(query);
			ResultSet resultSet = dbAccessBean.getResultSet();
			if (resultSet != null) 
			{
				ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
				String predictorName = resultSetMetaData.getColumnTypeName(1);
				String responseName = resultSetMetaData.getColumnTypeName(2);
				List<Double> predictorList = new ArrayList<Double>();
				List<Double> responseList = new ArrayList<Double>();
				while (resultSet.next()) {
					switch (predictorName.toLowerCase()) {
					case "int":
						predictorList.add((double) resultSet.getInt(1));
						break;
					case "smallint":
						predictorList.add((double) resultSet.getInt(1));
						break;
					case "float":
						predictorList.add((double) resultSet.getFloat(1));
						break;
					case "double":
						predictorList.add((double) resultSet.getDouble(1));
						break;
					case "long":
						predictorList.add((double) resultSet.getLong(1));
						break;
					default:
						predictorList.add((double) resultSet.getDouble(1));
						break;
					}
					switch (responseName.toLowerCase()) {
					case "int":
						responseList.add((double) resultSet.getInt(2));
						break;
					case "smallint":
						responseList.add((double) resultSet.getInt(2));
						break;
					case "float":
						responseList.add((double) resultSet.getFloat(2));
						break;
					case "double":
						responseList.add((double) resultSet.getDouble(2));
						break;
					case "long":
						responseList.add((double) resultSet.getLong(2));
						break;
					default:
						responseList.add((double) resultSet.getDouble(2));
						break;
					}
				}
				double[] predictorArray = new double[predictorList.size()];
				for (int i = 0; i < predictorList.size(); i++) {
					predictorArray[i] = (double) predictorList.get(i);
					predSeries.add(i + 1, (double) predictorList.get(i));
				}
				double[] responseArray = new double[responseList.size()];
				for (int i = 0; i < responseList.size(); i++) {
					responseArray[i] = (double) responseList.get(i);
					resSeries.add(i + 1, (double) responseList.get(i));
				}
				xyTimeSeriesCol.addSeries(predSeries);
				xyTimeSeriesCol.addSeries(resSeries);
				SimpleRegression sr = new SimpleRegression();
				if (responseArray.length > predictorArray.length) {
					for (int i = 0; i < predictorArray.length; i++) {
						sr.addData(predictorArray[i], responseArray[i]);
						xySeries.add(predictorArray[i], responseArray[i]);
					}
				} else {
					for (int i = 0; i < responseArray.length; i++) {
						sr.addData(predictorArray[i], responseArray[i]);
						xySeries.add(predictorArray[i], responseArray[i]);
					}
				}
				XYSeriesCollection xySeriesVar = new XYSeriesCollection();
				xySeriesVar.addSeries(xySeries);
				int totalDF = responseArray.length - 1;
				TDistribution tDistribution = new TDistribution(totalDF);
				double intercept = sr.getIntercept();
				double interceptStandardError = sr.getInterceptStdErr();
				double tStatistic = 0;
				int predictorDF = 1;
				int residualErrorDF = totalDF - predictorDF;
				double rSquare = sr.getRSquare();
				double rSquareAdjusted = rSquare - (1 - rSquare) / (totalDF - predictorDF - 1);
				if (interceptStandardError != 0) {
					tStatistic = (double) intercept / interceptStandardError;
				}
				double interceptPValue = (double) 2 * tDistribution.cumulativeProbability(-Math.abs(tStatistic));
				double slope = sr.getSlope();
				double slopeStandardError = sr.getSlopeStdErr();
				double tStatisticpredict = 0;
				if (slopeStandardError != 0) {
					tStatisticpredict = (double) slope / slopeStandardError;
				}
				double pValuePredictor = (double) 2 * tDistribution.cumulativeProbability(-Math.abs(tStatisticpredict));
				double standardErrorModel = Math.sqrt(StatUtils.variance(responseArray))
						* (Math.sqrt(1 - rSquareAdjusted));
				double regressionSumSquares = sr.getRegressionSumSquares();
				double sumSquaredErrors = sr.getSumSquaredErrors();
				double totalSumSquares = sr.getTotalSumSquares();
				double meanSquare = 0;
				if (predictorDF != 0) {
					meanSquare = regressionSumSquares / predictorDF;
				}
				double meanSquareError = 0;
				if (residualErrorDF != 0) {
					meanSquareError = (double) (sumSquaredErrors / residualErrorDF);
				}
				double fValue = 0;
				if (meanSquareError != 0) {
					fValue = meanSquare / meanSquareError;
				}

				String regressionEquation = response + " = " + intercept + " + (" + slope + ") "
						+ predictor;
				FDistribution fDistribution = new FDistribution(predictorDF, residualErrorDF);
				double pValue = (double) (1 - fDistribution.cumulativeProbability(fValue));
				rSquareAdjusted = rSquareAdjusted * 100;
				vif = 1 / (1 - rSquare);
				rSquare = rSquare * 100;
				boolean regressionResultsStatus = setRegAnalysisVar(regressionEquation, intercept,
						interceptStandardError, tStatistic, interceptPValue, slope, slopeStandardError,
						tStatisticpredict, pValuePredictor, standardErrorModel, rSquare, rSquareAdjusted, predictorDF,
						residualErrorDF, totalDF, regressionSumSquares, sumSquaredErrors, totalSumSquares, meanSquare,
						meanSquareError, fValue, pValue);
				
				if (regressionResultsStatus) {
					renderRegressionResult = true;
					return true;
				} else {

					return false;
				}
				
			} else {

				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public double getVif() {
		return vif;
	}

	public void setVif(double vif) {
		this.vif = vif;
	}

	public void exportToExcel() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		FileOutputStream fos = null;
		FacesContext context =FacesContext.getCurrentInstance();
		String path =context.getExternalContext().getRealPath("/temp"+this.getSession());
		File dir = new File(path);
		if(!dir.exists())
		{
			new File(path).mkdirs();
		}
		String fileName = "ColumnData.csv";
		File f = new File(path + "/"+fileName);
		// convert ResultSet object to Result object
		StringBuffer sb = new StringBuffer();
		// generate temporary file for export
		try {
			fos = new FileOutputStream(f);
			for (int i = 0; i < selectedColumnNames.size(); i++) {
				sb.append(selectedColumnNames.get(i).toString() + ",");
			}
			sb.append("\n");
			System.out.println("Sb:" + sb);
			System.out.println("No of rows: " + tableData.size());
			fos.write(sb.toString().getBytes());
			for (int i = 0; i < tableData.size(); i++) {
				sb = new StringBuffer();
				for (int j = 0; j < tableData.get(0).size(); j++) {
					sb.append(tableData.get(i).get(j).toString() + ",");
				}
				sb.append("\n");
				System.out.println("Sb : " + sb);
				fos.write(sb.toString().getBytes());
			}
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println("Exception occurred:" + e.getMessage());
		}
		String mimeType = ec.getMimeType(fileName);
		FileInputStream in = null;
		byte b;
		ec.responseReset();
		ec.setResponseContentType(mimeType);
		ec.setResponseContentLength((int) f.length());
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			// use previously generated temp file as input
			in = new FileInputStream(f);
			OutputStream output = ec.getResponseOutputStream();
			while (true) {
				b = (byte) in.read();
				if (b < 0)
					break;
				output.write(b);
			}
		} catch (Exception e) {
			System.out.println("Exception occurred:" + e.getMessage());
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				System.out.println("Exception occurred:" + e.getMessage());
			}
		}
		fc.responseComplete();
	}

	

	public void generateStats() {
		
		ArrayList<String> selectedColumnStats1=selectedColumnNames;
		selectedColumnNames=selectedColumnStats;
	
		displayColumnValues();
		statsColumns = new ArrayList<String>();
		
		statsStringList = new ArrayList<>();
		statsColumns.add("Column");
		statsColumns.add("Count");
		statsColumns.add("Min");
		statsColumns.add("Max");
		statsColumns.add("Mean");
		statsColumns.add("Median");
		statsColumns.add("Variance");
		statsColumns.add("Standard Deviation");
		statsColumns.add("Q1");
		statsColumns.add("Q3");
		statsColumns.add("IQR");
		System.out.print("     --------     "+tableData);
		columnNumbers = new HashMap<>();
		for (int i = 0; i < 11; i++) {
			columnNumbers.put(statsColumns.get(i), i);
		}
		
		renderTableData=false;
		statsList = new ArrayList<StatsBean>();
		try {
			sbScriptFile.append("descriptiveStats");
		
			for (int i = 0; i < selectedColumnNames.size(); i++) {
				
				System.out.println("in loop for stats");
				double values[] = new double[tableData.size()];
				int len = 0;
				//sbScriptFile.append(selectedColumnNames.get(i)+"\t");
				System.out.println("stats column--------"+selectedColumnNames+"    *****"+tableData.size());
				for (int rowNum = 0; rowNum < tableData.size(); rowNum++) {
					if (tableData.get(rowNum).get(i) != null && !"".equals(tableData.get(rowNum).get(i))) {
						values[len] = Double.parseDouble(tableData.get(rowNum).get(i));
						len++;
					}
				}

				StatsBean stats = new StatsBean();
				stats.setCount(len);
				stats.setMin(StatUtils.min(values));
				stats.setMax(StatUtils.max(values));
				stats.setMean(StatUtils.mean(values));
				stats.setVariance(StatUtils.variance(values));
				stats.setStdDev(Math.sqrt(StatUtils.variance(values)));
				stats.setMedian(StatUtils.percentile(values, 50.0));
				stats.setQ1(StatUtils.percentile(values, 25.0));
				stats.setQ3(StatUtils.percentile(values, 75.0));
				stats.setIqr(stats.getQ3() - stats.getQ1());
				stats.setColName(selectedColumnNames.get(i));
				statsList.add(stats);
			}
			selectedColumnNames=selectedColumnStats1;
			sbScriptFile.append("\r\n");
		
			render = true;
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("Null pointer Exception occurred while generating stats");
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Exception occurred while generating stats");
		}
		
		for(int i = 0 ; i < statsList.size(); i++) {
			statsString = new ArrayList<String>();
			statsString.add(statsList.get(i).getColName());
			statsString.add(Integer.toString(statsList.get(i).getCount()));
			statsString.add(Double.toString(statsList.get(i).getMin()));
			statsString.add(Double.toString(statsList.get(i).getMax()));
			statsString.add(Double.toString(statsList.get(i).getMean()));
			statsString.add(Double.toString(statsList.get(i).getMedian()));
			statsString.add(Double.toString(statsList.get(i).getVariance()));
			statsString.add(Double.toString(statsList.get(i).getStdDev()));
			statsString.add(Double.toString(statsList.get(i).getQ1()));
			statsString.add(Double.toString(statsList.get(i).getQ3()));
			statsString.add(Double.toString(statsList.get(i).getIqr()));
			
			statsStringList.add(statsString);
		}
		System.out.print("Set value to ****---"+selectedColumnStats1);
		selectedColumnNames=selectedColumnStats1;
		updateTableColumn();
		
		
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public ArrayList<StatsBean> getStatsList() {
		return statsList;
	}

	public void setStatsList(ArrayList<StatsBean> statsList) {
		this.statsList = statsList;
	}




	// Setting Regression Analysis Variables
	public boolean setRegAnalysisVar(String regEquation, double intercept, double interceptStdErr, double tStatistic,
			double interceptPValue, double slope, double slopeStdErr, double tStatPred, double pValuePred,
			double stdErrorM, double rSquare, double rSquareAdj, double predictorDegreesFreedom,
			double residualErrorDegreesFreedom, double totalDegreesFreedom, double regSumSquares, double sumSquaredErr,
			double totalSumSquares, double meanSquare, double meanSquareErr, double fValue, double pValue) {
		try {

			this.slope = slope;
			this.slopeStdErr = slopeStdErr;
			this.tStatPred = tStatPred;
			this.pValuePred = pValuePred;
			this.fValue = fValue;
			this.pValue = pValue;
			this.stdErrorM = stdErrorM;
			this.setrSquare(rSquare);
			this.setrSquareAdj(rSquareAdj);
			this.predictorDegreesFreedom = predictorDegreesFreedom;
			this.residualErrorDegreesFreedom = residualErrorDegreesFreedom;
			this.totalDegreesFreedom = totalDegreesFreedom;
			this.regSumSquares = regSumSquares;
			this.sumSquaredErr = sumSquaredErr;
			this.totalSumSquares = totalSumSquares;
			this.meanSquare = meanSquare;
			this.meanSquareErr = meanSquareErr;
			this.regEquation = regEquation;
			this.intercept = intercept;
			this.interceptStdErr = interceptStdErr;
			this.tStatistic = tStatistic;
			this.interceptPValue = interceptPValue;
			
			
			return true;
		}

		// Exception handler
		catch (Exception error) {
			message = error.getMessage();
			return false;
		}
	}

	// Getters and setters for MathManagedBean
	public double getInterceptStdErr() {
		return interceptStdErr;
	}

	public void setInterceptStdErr(double interceptStdErr) {
		this.interceptStdErr = interceptStdErr;
	}

	public double gettStatistic() {
		return tStatistic;
	}

	public void settStatistic(double tStatistic) {
		this.tStatistic = tStatistic;
	}

	public double getInterceptPValue() {
		return interceptPValue;
	}

	public void setInterceptPValue(double interceptPValue) {
		this.interceptPValue = interceptPValue;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRegEquation() {
		return regEquation;
	}

	public void setRegEquation(String regEquation) {
		this.regEquation = regEquation;
	}

	public double getIntercept() {
		return intercept;
	}

	public void setIntercept(double intercept) {
		this.intercept = intercept;
	}

	public double getPredictorDegreesFreedom() {
		return predictorDegreesFreedom;
	}

	public void setPredictorDegreesFreedom(double predictorDegreesFreedom) {
		this.predictorDegreesFreedom = predictorDegreesFreedom;
	}

	public double getResidualErrorDegreesFreedom() {
		return residualErrorDegreesFreedom;
	}

	public void setResidualErrorDegreesFreedom(double residualErrorDegreesFreedom) {
		this.residualErrorDegreesFreedom = residualErrorDegreesFreedom;
	}

	public double getTotalDegreesFreedom() {
		return totalDegreesFreedom;
	}

	public void setTotalDegreesFreedom(double totalDegreesFreedom) {
		this.totalDegreesFreedom = totalDegreesFreedom;
	}

	public double getpValuePred() {
		return pValuePred;
	}

	public void setpValuePred(double pValuePred) {
		this.pValuePred = pValuePred;
	}

	public double getStdErrorM() {
		return stdErrorM;
	}

	public void setStdErrorM(double stdErrorM) {
		this.stdErrorM = stdErrorM;
	}

	public double getRegSumSquares() {
		return regSumSquares;
	}

	public void setRegSumSquares(double regSumSquares) {
		this.regSumSquares = regSumSquares;
	}

	public double getSumSquaredErr() {
		return sumSquaredErr;
	}

	public void setSumSquaredErr(double sumSquaredErr) {
		this.sumSquaredErr = sumSquaredErr;
	}

	public double getTotalSumSquares() {
		return totalSumSquares;
	}

	public void setTotalSumSquares(double totalSumSquares) {
		this.totalSumSquares = totalSumSquares;
	}

	public double getMeanSquare() {
		return meanSquare;
	}

	public void setMeanSquare(double meanSquare) {
		this.meanSquare = meanSquare;
	}

	public double getMeanSquareErr() {
		return meanSquareErr;
	}

	public void setMeanSquareErr(double meanSquareErr) {
		this.meanSquareErr = meanSquareErr;
	}

	public double getfValue() {
		return fValue;
	}

	public void setfValue(double fValue) {
		this.fValue = fValue;
	}

	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
	}

	public double getSlopeStdErr() {
		return slopeStdErr;
	}

	public void setSlopeStdErr(double slopeStdErr) {
		this.slopeStdErr = slopeStdErr;
	}

	public double gettStatPred() {
		return tStatPred;
	}

	public void settStatPred(double tStatPred) {
		this.tStatPred = tStatPred;
	}

	public double getrSquare() {
		return rSquare;
	}

	public void setrSquare(double rSquare) {
		this.rSquare = rSquare;
	}

	public double getrSquareAdj() {
		return rSquareAdj;
	}

	public void setrSquareAdj(double rSquareAdj) {
		this.rSquareAdj = rSquareAdj;
	}

	private boolean authenticationFailed;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	// @ManagedProperty("#{dbAccessBean}")
	// private DbAccessBean dbAccessBean;

	List<File> fileList = new ArrayList<>();

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	private ArrayList<String> list = new ArrayList<String>();

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public boolean isAuthenticationFailed() {
		return authenticationFailed;
	}

	public void setAuthenticationFailed(boolean authenticationFailed) {
		this.authenticationFailed = authenticationFailed;
	}

	private String fileNameSelected;

	public String getFileNameSelected() {
		return fileNameSelected;
	}

	public void setFileNameSelected(String fileNameSelected) {
		this.fileNameSelected = fileNameSelected;
	}

	private String fileExecuteMessage = "";

	public String getFileExecuteMessage() {
		return fileExecuteMessage;
	}

	public void setFileExecuteMessage(String fileExecuteMessage) {
		this.fileExecuteMessage = fileExecuteMessage;
	}

	public String getFileExecuteMessage1() {
		return fileExecuteMessage1;
	}

	public void setFileExecuteMessage1(String fileExecuteMessage1) {
		this.fileExecuteMessage1 = fileExecuteMessage1;
	}

	public String getFileExecuteMessage2() {
		return fileExecuteMessage2;
	}

	public void setFileExecuteMessage2(String fileExecuteMessage2) {
		this.fileExecuteMessage2 = fileExecuteMessage2;
	}

	public String getFileExecuteMessage3() {
		return fileExecuteMessage3;
	}

	public void setFileExecuteMessage3(String fileExecuteMessage3) {
		this.fileExecuteMessage3 = fileExecuteMessage3;
	}

	private String fileExecuteMessage1 = "";
	private String fileExecuteMessage2 = "";
	private String fileExecuteMessage3 = "";
	private int uploadedRowsCount = 0;

	
	
	public int getUploadedRowsCount() {
		return uploadedRowsCount;
	}

	public void setUploadedRowsCount(int uploadedRowsCount) {
		this.uploadedRowsCount = uploadedRowsCount;
	}


	public ArrayList<ArrayList<String>> getUploadFileData() {
		return uploadFileData;
	}

	public void setUploadFileData(ArrayList<ArrayList<String>> uploadFileData) {
		this.uploadFileData = uploadFileData;
	}

	public boolean isRenderUploadFile() {
		return renderUploadFile;
	}

	public void setRenderUploadFile(boolean renderUploadFile) {
		this.renderUploadFile = renderUploadFile;
	}

	public ArrayList<String> getUploadFileColumnNames() {
		return uploadFileColumnNames;
	}

	public void setUploadFileColumnNames(ArrayList<String> uploadFileColumnNames) {
		this.uploadFileColumnNames = uploadFileColumnNames;
	}


	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		// TODO Auto-generated method stub
		
	}
	

	
	public void writeToFile()
	{
		{
		    try {
		    	sbScriptFile.append("Export.txt\r\n");
		    	sbScriptFile.append("exit");
		        String content = sbScriptFile.toString();		        		      

				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				FileOutputStream fos;
				String path = fc.getExternalContext().getRealPath("/temp"+this.getSession());
				File directory = new File(path);
				if (!directory.exists()) {
					new File(path).mkdirs();
				}
				String fileName = "Export.txt";
				File f = new File(path+fileName);
				// convert ResultSet object to Result object
				StringBuffer sb = new StringBuffer();
				// generate temporary file for export
				
					fos = new FileOutputStream(f);
					sb.append(content);
						fos.write(sb.toString().getBytes());
					
					fos.flush();
					fos.close();
			
				String mimeType = ec.getMimeType(fileName);
				FileInputStream in = null;
				byte b;
				ec.responseReset();
				ec.setResponseContentType(mimeType);
				ec.setResponseContentLength((int) f.length());
				ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				try {
					// use previously generated temp file as input
					in = new FileInputStream(f);
					OutputStream output = ec.getResponseOutputStream();
					while (true) {
						b = (byte) in.read();
						if (b < 0)
							break;
						output.write(b);
					}

				} catch (Exception e) {
					System.out.println("Exception occurred:" + e.getMessage());
				} finally {
					try {
						in.close();
					} catch (Exception e) {
						System.out.println("Exception occurred:" + e.getMessage());
					}
				}
				fc.responseComplete();
			

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	public void readFile() {
		String filePath=null;
				}
	
	public void writeToPDF () throws DocumentException, MalformedURLException, IOException
	{
		System.out.print("PDF writing");
		System.out.println(content);
		//System.out.println(s);
		  try {		    			     		        		      
		    	//System.out.println("entered");
		    	//System.out.println(s);
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				FileOutputStream fos;

				String path = fc.getExternalContext().getRealPath("/temp"+this.getSession());
				File directory = new File(path);
				if (!directory.exists()) {
					new File(path).mkdirs();
				}
				
				
				 actionBeanGraph = new ActionBeanGraph();
				 actionBeanGraph.setActionBean(this);
								
				Document document = new Document();
				File f = new File(path + "/export.pdf");
				System.out.println(path);
		        PdfWriter.getInstance(document, new FileOutputStream(f));
		        document.open();
		        String imageType = "";
		        PdfPTable table;
				Image img;
				PdfPCell[] cells;
		        String sentences[]=content.split("\r\n");
		        System.out.println("Sample       "+sentences.length);
		        System.out.println("First      "+sentences[0]);
		        Paragraph para;
		        for (int i=0;i<sentences.length;i++)
		        {
		        	System.out.println("Inside first loop");
		        	String sentence[]=sentences[i].split("\t");
		        	for(int j=0;j<sentence.length;j++)
		        	{
		        		if(sentence[0].equals("tableList"))
		        		{
		        			this.getTableList();
		        			System.out.println("Was here for table list");
		        			break;
		        		}
		        		else if(sentence[0].equals("import"))
		        		{
		        			System.out.println("Was here for import");
		        			break;
		        		}
		        		else if(sentence[0].equals("use"))
		        		{
		        			System.out.println("Was here for use");
		        			this.setTableName(sentence[1]);
		        			break;
		        		}
		        		else if(sentence[0].equals("columnList"))
		        		{
		        			System.out.println("Was here for columnlist");
		        			this.getColumnsInTable();
		        			break;
		        		}
		        		else if(sentence[0].equals("select"))
		        		{
		        			System.out.println("Was here for select "+sentence.length);
		        			selectedColumnNames= new ArrayList();
		        			//if (j!=0) {
		        			for(int z=1; z<sentence.length; z++)
		        			{
		        			System.out.println(sentence[z]);
		        			selectedColumnNames.add(sentence[z]);		        			
		        			
		        			if(z==(sentence.length-1)){
		        				//this.updateTableColumn();
		        				this.displayColumnValues();
		        				
		        			}
		        			
		        			}
		        			
/*		        			if(z==(sentence.length)){
		        				this.displayColumnValues();
		        			}*/
		        			
		        		}
		        		else if(sentence[0].equals("descriptiveStats"))
		        		{
		    		        //Adding Table to document 
		        			para = new Paragraph("\nDescriptive Stats\n");
		        			para.setAlignment(Element.ALIGN_CENTER);		        			
		        			document.add(para);
		    		        //document.add(new Paragraph("\n"));
		    		        
		    		        //for(int z=1; z<sentence.length; z++)
		    		        //	numColumnNames.add(sentence[z]);
		    		        
		    		        
		    		        //selectedColumnStats = (ArrayList)selectedColumnNames;
		        			selectedColumnStats = (ArrayList)numColumnNames;
		    		        this.generateStats();
		    		        table = new PdfPTable(11);     
		    		        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    		        table.addCell("Col"); 
		    		        table.addCell("Count"); 
		    		        table.addCell("Min");
		    		        table.addCell("Max"); 
		    		        table.addCell("Mean"); 
		    		        table.addCell("Median"); 
		    		        table.addCell("Var");
		    		        table.addCell("SD"); 
		    		        table.addCell("Q1"); 
		    		        table.addCell("Q3");
		    		        table.addCell("IQR"); 
		    		  	   
		    		        table.setHeaderRows(1);
		    		  	    cells = table.getRow(0).getCells(); 
		    			    for (int k=0;k<cells.length;k++){
		    			      cells[k].setBackgroundColor(BaseColor.GRAY);
		    			    }
		    		       
		    			    System.out.println(statsStringList);
		    		        for(int k=0; k<statsStringList.size(); k++)
		    		        {
		    			        ArrayList<String> tempArray = statsStringList.get(k);
		    			        for(int p=0; p<tempArray.size(); p++)
		    			        {
		    			        	table.addCell(tempArray.get(p)); 
		    			        }
		    		        }
		    		        document.add(new Paragraph("\n"));
		    		        document.add(table);
		    		        
		        			
		        		}
		        		else if(sentence[j].equals("hist"))
		        		{
		        			System.out.println("Was here for histogram");
		        			this.setHist(sentence[1]);
		        			System.out.println(hist);
		    		       
		    		        actionBeanGraph.createHistogramDiagram();
		    		        imageType = "histogram";
		    		        
		    		        img = Image.getInstance(path + "/"+imageType+".png");
		    				img.scaleToFit(500f, 500f);
		    				document.add(new Paragraph(""));
		    				document.add(img);	
		        		}
		        		else if(sentence[j].equals("scatterplot"))
		        		{
		        			System.out.println("Was here for scatterplot");
		        			this.setPredictor(sentence[1]);
		    		        this.setResponse(sentence[2]);
		    		        System.out.println(predictor);
		    		        System.out.println(response);
		    		        actionBeanGraph.createScatterplotChart();
		    		        imageType = "ScatterPlot";
		    		        //columnNames.remove(0);
		    		        //selectedColumnNames.remove(0);
		    		    		        
		    		        img = Image.getInstance(path + "/"+imageType+".png");
		    		        img.scaleToFit(500f, 500f);
		    		        document.add(new Paragraph(""));	
		    		        //document.add(new Paragraph("\n"+sentence+"\n"));
		    		        document.add(img);
		        		}
		        		else if(sentence[j].equals("corr"))
		        		{
		        			System.out.println("Was here for correlation");
		        			para = new Paragraph("\nCorelation\n\n");
		        			para.setAlignment(Element.ALIGN_CENTER);		        			
		        			document.add(para);
		        			this.setPredictor(sentence[1]);
		    		        this.setResponse(sentence[2]);
		    		        
		    		        this.calculateCorrelation();
		    		        table = new PdfPTable(3);     
		    		        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    		        table.addCell("Pearson's Correlation"); 
		    		        table.addCell("Spearman's Correlation"); 
		    		        table.addCell("Kendall's Correlation");
		    		  	    table.setHeaderRows(1);
		    		  	    cells = table.getRow(0).getCells(); 
		    			    for (int k=0;k<cells.length;k++){
		    			      cells[k].setBackgroundColor(BaseColor.GRAY);
		    			    }
		    		        //Adding cells to the table 
		     
		    		        table.addCell(String.valueOf(pearsonCorr)); 
		    		        table.addCell(String.valueOf(spearmanCorr)); 
		    		        table.addCell(String.valueOf(kendallCorr)); 

		    		        //Adding Table to document 
		    		        //document.add(new Paragraph("\n"+sentence+"\n"));
		    		        //document.add(new Paragraph("Correlation"));	
		    		        
		    		        document.add(table);
		        		}
		        		else if(sentence[j].equals("regress"))
		        		{
		        			System.out.println("Was here for regression");
		        			para = new Paragraph("\nRegression\n");
		        			para.setAlignment(Element.ALIGN_CENTER);		        			
		        			document.add(para);
		        			this.setPredictor(sentence[1]);
		    		        this.setResponse(sentence[2]);
		    		        
		    		        this.generateRegressionReport();
		    		        table = new PdfPTable(5);     
		    		        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    		        table.addCell("Predictor"); 
		    		        table.addCell("Co-efficient"); 
		    		        table.addCell("Standard Error Co-efficient");
		    		        table.addCell("T-Statistic");
		    		        table.addCell("P-Value");
		    		  	    
		    		        table.setHeaderRows(1);
		    		  	    cells = table.getRow(0).getCells(); 
		    			    for (int k=0;k<cells.length;k++){
		    			      cells[k].setBackgroundColor(BaseColor.GRAY);
		    			    }
		    		  	    
		    		  	    table.addCell("Constant"); 
		    		        table.addCell(String.valueOf(intercept));
		    		        table.addCell(String.valueOf(interceptStdErr)); 
		    		        table.addCell(String.valueOf(tStatistic));
		    		        table.addCell(String.valueOf(interceptPValue));
		    		        
		    		        table.addCell(String.valueOf(predictor)); 
		    		        table.addCell(String.valueOf(slope)); 
		    		        table.addCell(String.valueOf(slopeStdErr));
		    		        table.addCell(String.valueOf(tStatPred));
		    		        table.addCell(String.valueOf(pValuePred));
		    		        document.add(new Paragraph("\n\n"));
		    		        document.add(table);
		    		        
		    		        table = new PdfPTable(3);     
		    		        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    		        table.addCell("Model Std Er"); 
		    		        table.addCell("R-Square"); 
		    		        table.addCell("Adjusted RSq"); 
		    		        	        
		    		  	    table.setHeaderRows(1);
		    		  	    cells = table.getRow(0).getCells(); 
		    			    for (int k=0;k<cells.length;k++){
		    			      cells[k].setBackgroundColor(BaseColor.GRAY);
		    			    }
		    		  	    
		    		  	    table.addCell(String.valueOf(stdErrorM)); 
		    		        table.addCell(String.valueOf(rSquare));
		    		        table.addCell(String.valueOf(rSquareAdj)); 
		    		        
		    		        document.add(new Paragraph("\n"+"\n"));
		    		        document.add(table);
		    		        
		    		        
		    		        table = new PdfPTable(6);     
		    		        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    		        table.addCell("Source"); 
		    		        table.addCell("Degrees of Freedom"); 
		    		        table.addCell("Sum of Squares"); 
		    		        table.addCell("Mean of Squares");
		    		        table.addCell("F-Statistic"); 
		    		        table.addCell("P-Value"); 
		    		        	        
		    		        	        
		    		  	    table.setHeaderRows(1);
		    		  	    cells = table.getRow(0).getCells(); 
		    			    for (int k=0;k<cells.length;k++){
		    			      cells[k].setBackgroundColor(BaseColor.GRAY);
		    			    }
		    		  	    
		    		  	    table.addCell("Regression"); 
		    		        table.addCell(String.valueOf(predictorDegreesFreedom));
		    		        table.addCell(String.valueOf(regSumSquares)); 
		    		        table.addCell(String.valueOf(meanSquare));
		    		        table.addCell(String.valueOf(fValue));
		    		        table.addCell(String.valueOf(pValue));
		    		        
		    		        table.addCell("Residual Error"); 
		    		        table.addCell(String.valueOf(residualErrorDegreesFreedom));
		    		        table.addCell(String.valueOf(sumSquaredErr)); 
		    		        table.addCell(String.valueOf(meanSquareErr));
		    		        table.addCell("N.A.");
		    		        table.addCell("N.A.");
		    		        
		    		        table.addCell("Total"); 
		    		        table.addCell(String.valueOf(totalDegreesFreedom));
		    		        table.addCell(""); 
		    		        table.addCell("");
		    		        table.addCell("");
		    		        table.addCell("");
		    		        document.add(new Paragraph("\n"));
		    		        document.add(table);
		        		}
		        		else if(sentence[0].equals("exit"))
		        		{
		        			break;
		        		}
		        		else
		        		{
		        			System.out.print("Something else");
		        		}
		        		
		        		//System.out.println(sentence[j]);
		        	}
		        	if (sentences[i].equals("exit"))
		        	{
		        		break;
		        	}
		        	System.out.println(sentences[i]);
		        }
		       
		        //Hist
		        
		        
		       
	
		        		
				// Scatter 
				
				//columnNames = new ArrayList();
				//columnNames.add("Population");
				
				//selectedColumnNames= new ArrayList();
				//selectedColumnNames.add("Population");
				
				//this.getColumnNames().add("Population");
		        
		        
		        //Corr
		        //this.setPredictor("Population");
		        //this.setResponse("SurfaceArea");

		        
		       
		        //Regression
		        
		        
		      //  document.add(new Paragraph("Descriptive Stats"));	
		       // document.add(new Paragraph("\n"));
		        
		    //    this.setPredictor("Population");
		      //  this.setResponse("SurfaceArea");
		       
		        
		       
		        
		        document.close();
		        System.out.println("Done");
		      
		        

				//String mimeType = ec.getMimeType("export.pdf");
				FileInputStream in = null;
				int b;
				ec.responseReset();
				ec.setResponseContentType("application/pdf");
				ec.setResponseContentLength((int) f.length());
				ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "export.pdf" + "\"");
				try {
					// use previously generated temp file as input
					in = new FileInputStream(f);
					OutputStream output = ec.getResponseOutputStream();
					while (true) {
						b = (int) in.read();
						if (b < 0)
							break;
						output.write(b);
					}

				} catch (Exception e) {
					System.out.println("Exception occurred:" + e.getMessage());
				} finally {
					try {
						in.close();
					} catch (Exception e) {
						System.out.println("Exception occurred:" + e.getMessage());
					}
				}
				fc.responseComplete();
			

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	
	}
