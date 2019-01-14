package edu.uic.ids517.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import edu.uic.ids517.model.UserBean;
import edu.uic.ids517.model.DbAccessBean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@ManagedBean(name = "uploadBean")
@SessionScoped
public class UploadBean {
	@ManagedProperty("#{actionBean}")
	private ActionBean actionBean;
	private UploadedFile uploadedFile;
	public ActionBean getActionBean() {
		return actionBean;
	}

	public void setActionBean(ActionBean actionBean) {
		this.actionBean = actionBean;
	}

	//private String fileLabel;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private int numberRows;
	private int numberColumns;
	private String uploadedFileContents;
	private boolean fileImport;
	private boolean fileImportError;
	private String filePath;
	private String tempFileName;
	private FacesContext facesContext;
	private String datasetLabel;
	private String fileType;
	private String fileFormat;
	private String fileheaderRowFormat;
	private String relativeURL;
	private HttpSession session;

	private ActionBeanGraph actionBeanGraph;
	private String value[];
	private int length;

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] value) {
		this.value = value;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public File getFileContents() {
		return fileContents;
	}

	public void setFileContents(File fileContents) {
		this.fileContents = fileContents;
	}

	private String errorMessage;
	private boolean messageRendered;
	
	public DbAccessBean getDbAccessBean() {
		return dbAccessBean;
	}

	public void setDbAccessBean(DbAccessBean dbAccessBean) {
		this.dbAccessBean = dbAccessBean;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	private DbAccessBean dbAccessBean;
    private UserBean userBean;
    String s2="N/A";
	
	String status = "";
	File fileContents = null;

	@PostConstruct
	public void init() {
		Map<String, Object> m = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		//dataAccess = (DataAccess) m.get("dataAccess");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession();
		//actionBean = new ActionBean();
		//actionBeanGraph = new ActionBeanGraph();
	}
	
	public String processFileImport() throws DocumentException
	{
		if(uploadedFile== null){
			return "FAIL";
		}
		errorMessage="";
		uploadedFileContents = null;
		facesContext = FacesContext.getCurrentInstance();
		filePath = facesContext.getExternalContext().getRealPath("/temp"+actionBean.getSession());
		
		File directory = new File(filePath);
		if (!directory.exists()) {
			new File(filePath).mkdirs();
		}
		
		FileOutputStream fos = null;
		int n = 0;
		fileImport = false;
		fileImportError = true;
		try {
			fileName = uploadedFile.getName();
			fileSize = uploadedFile.getSize();
			String value1[]=fileName.split("\\\\");
			int l=value1.length-1;
			fileContentType = uploadedFile.getContentType();
			tempFileName = filePath + "/" +value1[l];
			
			fileContents = new File(tempFileName);
			System.out.println(" ---------------- "+value1);
			fos = new FileOutputStream(fileContents);
			fos.write(uploadedFile.getBytes());
			fos.close();
		
			Scanner s = new Scanner(fileContents);
			s2="";
			while(s.hasNext()) {
				String s1=s.nextLine();
				s2=s2+"\r\n"+s1;
				System.out.println("---------------"+s1);
				//System.out.print(s.useDelimiter("\t"));
				n++;
			}
			actionBean.setContent(s2);
			//actionBean.writeToPDF(s2);
			length=n;
			System.out.print(n+"------number of lines");
			s.close();
		 
	
			fileImport = true; 

		} catch (IOException e) { 
			e.printStackTrace();
			return "FAIL";
		}
		errorMessage = "File Upload Successful";
		messageRendered = true;
		return "SUCCESS"; 
	}
	
	public void exportPDF() throws DocumentException, MalformedURLException, IOException 
	{
		actionBean.setContent(s2);
		actionBean.writeToPDF();
		
	}


	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


//	public String getFileLabel() {
	//	return fileLabel;
	//}


//	public void setFileLabel(String fileLabel) {
	//	this.fileLabel = fileLabel;
	//}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public long getFileSize() {
		return fileSize;
	}

	public String getRelativeURL() {
		return relativeURL;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public int getNumberRows() {
		return numberRows;
	}


	public void setNumberRows(int numberRows) {
		this.numberRows = numberRows;
	}


	public int getNumberColumns() {
		return numberColumns;
	}


	public void setNumberColumns(int numberColumns) {
		this.numberColumns = numberColumns;
	}


	public String getUploadedFileContents() {
		return uploadedFileContents;
	}


	public void setUploadedFileContents(String uploadedFileContents) {
		this.uploadedFileContents = uploadedFileContents;
	}


	public boolean isFileImport() {
		return fileImport;
	}


	public void setFileImport(boolean fileImport) {
		this.fileImport = fileImport;
	}

	public void setRelativeURL(String relativeURL) {
		this.relativeURL = relativeURL;
	}

	public boolean isFileImportError() {
		return fileImportError;
	}


	public void setFileImportError(boolean fileImportError) {
		this.fileImportError = fileImportError;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getTempFileName() {
		return tempFileName;
	}


	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}


	public FacesContext getFacesContext() {
		return facesContext;
	}


	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}


	public String getDatasetLabel() {
		return datasetLabel;
	}


	public void setDatasetLabel(String datasetLabel) {
		this.datasetLabel = datasetLabel;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getFileFormat() {
		return fileFormat;
	}


	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}


	public String getFileheaderRowFormat() {
		return fileheaderRowFormat;
	}


	public void setFileheaderRowFormat(String fileheaderRowFormat) {
		this.fileheaderRowFormat = fileheaderRowFormat;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isMessageRendered() {
		return messageRendered;
	}

	public void setMessageRendered(boolean messageRendered) {
		this.messageRendered = messageRendered;
	}
}

