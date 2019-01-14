<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data import</title>

</head>
<style>

body{
background: #EFEFEF;
}
hr{ 
border:none;
width: 500px;
height: 50px;
margin-top: 0;
border-bottom: 1px solid #1f1209;
box-shadow: 0 20px 20px -20px #333;
margin: -50px auto 10px; 
}
h1{
color: black;
font-family: baskerville;
font-variant: small-caps;
font-size: 30px;
font-weight: bold;
margin-top: 30px;
margin-bottom: 2px;
}
h2{
color: darkblue;
font-family: arial, sans-serif;
font-size: 24px;
font-weight: bold;
margin-top: 30px;
margin-bottom: 2px;
}
.loginPanel{
border:1px inset yellow;
padding-top: 10px;
padding-right: 10px;
padding-left: 10px;
padding-bottom: 10px;
border-radius: 8px;
box-shadow: 0 20px 20px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}
.userInput{
border: 1px inset;
padding: 4px 10px;
border-radius: 8px;
margin: 8px 4px;
width: 225px;
}
.labels{
color: darkblue;
font-variant: small-caps;
border-radius: 10px;
padding: 4px 20px;
font-size: 18px;
}
.buttons{
color: #fff;
font-family: baskerville;
background-color: #A9A9A9;
font-size: 18px;
font-variant: small-caps;
width: 250px;
border-radius:75%;
border: 3px solid black;
padding: 30px 20px;
cursor: pointer;
}
.buttons:hover{
background-color: #135058;
box-shadow: 0 50px 16px 0 rgba(0,0,0,0.24),0 17px 70px 0 rgba(0,0,0,0.19);
}
.buttons:active {
transform: translateX(4px);
}
.userInputDropdown{
border: 1px inset;
padding: 4px 10px;
border-radius: 8px;
margin: 8px 4px;
width: 245px;
}
.navButtons{
color: #fff;
background-color: #565656;
font-family: copperplate;
font-size: 12px;
font-variant: small-caps;
width: 75px;
border: 1px solid black;
border-radius:50%;
padding: 10px 10px;
cursor: pointer;
}
.navButtons:hover{
background-color: #206020;
box-shadow: 0 22px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}
.navButtons:active{
transform: translateY(4px);
}
.listBox{
color: darkblue;
border: 1px solid green;
border-radius: 8px;
padding: 10px 10px;
font-size: 14px;
text-align: left;
width: 300px
}
.outputText{
color: darkblue;
padding: 2px 2px;
font-size: 18px;
}

</style>
<body>
	<f:view>
	<center>
	<div align="center">
	<div>
							<hr>
					<h3>Data Import</h3>
					<hr>
	</div><br>
	<div align = "center">
	<h:form>
		<h:commandButton type="submit" value="BACK"
				 action="mainMenu.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="HOME"
				 action="mainMenu.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="LOGOUT"
				 action="#{actionBean.closeConnection}" styleClass = "navButtons"/>
	</h:form>
	</div>

		<h:form enctype="multipart/form-data">
			<h:panelGrid columns="2"
				style="background-color: white;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid"
				>

				<h:outputLabel value="Select file to upload:*" />
				<t:inputFileUpload id="fileUpload" label="File to upload"
					storage="default" value="#{uploadBean.uploadedFile}"
					required="true" size="60" requiredMessage="Please select a file" />
					<h:outputLabel value="File label:*" />
		
				<h:commandButton id="Import"
					action="#{uploadBean.processFileImport}" value="Submit" />

			</h:panelGrid>
			<br />
			<div align="center">
				<h:outputText style="color:red;" value="#{uploadBean.errorMessage}"
					rendered="#{uploadBean.messageRendered}" />
				<br />
				<h:outputLabel value="* Mandatory Field"  />
				<br />
				<h:message for="fileUpload" style="color:red;"
					errorClass="errorMessage" rendered="#{uploadBean.messageRendered}" />
				<br />
				
				<br />
			</div>
		</h:form>
	</div>
	<br />
	<div id="container3" align="center" >
		<h:form enctype="multipart/form-data">
			<h:panelGrid
				style="background-color: white;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid" rendered="#{uploadBean.messageRendered}">

							<h:outputLabel value="File Name:" />
				<h:outputText value="#{uploadBean.fileName}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="File size" />
				<h:outputText value="#{uploadBean.fileSize}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="File Content Type:" />
				<h:outputText value="#{uploadBean.fileContentType}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="Temp File Path:" />
				<h:outputText value="#{uploadBean.filePath}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>	
				<h:outputLabel value="Temp File Name:" />
				<h:outputText value="#{uploadBean.tempFileName}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="Faces Context:" />
				<h:outputText value="#{uploadBean.session}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="Number of lines" />
				<h:outputText value="#{uploadBean.length}"
					rendered="#{uploadBean.messageRendered}" />
				<h:commandButton id="export"
					action="#{uploadBean.exportPDF}" value="Export" />

			</h:panelGrid>
		</h:form>
		</center>
	</f:view>
</body>
</html>