<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Analysis</title>
</head>
<style> 
body{
background: #EFEFEF;
}
hr{ 
border:none;
width: 600px;
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
h2{
color: darkblue;
font-family: arial, sans-serif;
font-size: 24px;
font-weight: bold;
margin-top: 30px;
margin-bottom: 2px;
}
</style>
<body>
<f:view>
	<div>
		<h1 align="center">NUMERIC DESCRIPTIVE STATISTICS</h1><hr>
	</div><br>
	
	<div align = "center">
	<h:form>
		<h:commandButton type="submit" value="BACK"
				 action="execute.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="HOME"
				 action="mainMenu.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="LOGOUT"
				 action="#{actionBean.closeConnection}" styleClass = "navButtons"/>
	</h:form>
	</div>
	<br>
	<h:form>
	<div align = "center">
	<h2>SELECT COLUMNS FOR ANALYSIS</h2>
	</div><br>
	<div align = "center">
	<h:panelGrid columns = "2">
	<h:panelGroup style = "vertical-align: middle; padding: 50px 50px;">
	<h:selectManyListbox  styleClass="listBox" value="#{actionBean.selectedColumnStats}"  size = "8">
		<f:selectItems value = "#{actionBean.numColumnNames}" />
	</h:selectManyListbox>
	</h:panelGroup>
	
		<h:panelGroup style = "vertical-align: middle; padding: 50px 50px;">
		<h:commandButton type="submit" value="Generate Statistics"
				 action="#{actionBean.generateStats}" styleClass = "buttons"/>
		</h:panelGroup>
	
	
	</h:panelGrid>
	
	</div>
	<br>
	<div align = "center"
		style="background-attachment: scroll; overflow: auto; height: 150px; background-repeat: repeat;">
			<t:dataTable value="#{actionBean.statsStringList}" var="row"
				rendered="#{actionBean.render}" border="2" cellspacing="2"
				cellpadding="1" columnClasses="columnClass1 border"
				headerClass="headerClass" footerClass="footerClass"
				rowClasses="rowClass2" styleClass="dataTableEx" width="900">
				<t:columns binding="#{col}" var="col" value="#{actionBean.statsColumns}">
				<f:facet name="header">
				<t:outputText styleClass="outputHeader" value="#{col}" />
				</f:facet>
					<t:outputText styleClass="outputText" value="#{row[actionBean.columnNumbers[col]]}" />
				</t:columns>
				</t:dataTable>
	</div>
	
	<% //<div align = "center">
	// <h:commandButton type="submit" value="Export Statistics"
				// action="#{actionBean.exportStats}" styleClass = "buttons"/>
	//</div>%>
	
	
	</h:form>
</f:view>
</body>
</html>