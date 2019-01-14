<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<style> 
body{
background: #EFEFEF;
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
hr{ 
border:none;
width: 500px;
height: 50px;
margin-top: 0;
border-bottom: 1px solid #1f1209;
box-shadow: 0 20px 20px -20px #333;
margin: -50px auto 10px; 
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
</style>
</head>
<body>
<f:view>
	<div>
		<h1 align="center">MENU</h1><hr>
	</div><br>
	<div align = "center">
	<h:form >
		<h:commandButton type="submit" value="BACK"
				 action="mainMenu.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="HOME"
				 action="mainMenu.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="LOGOUT"
				 action="#{actionBean.closeConnection}" styleClass = "navButtons"/>
	</h:form>
	</div><br/><br/><hr>
	<div align = "center"><br><br><br><br>
	<h:form>
		<h:commandButton type="submit" value="Import Script"
				 action="import.jsp" styleClass = "buttons"/> &nbsp &nbsp &nbsp;
		<%-- <h:commandButton type="submit" value="Import To Database"
				 action="upload.jsp" styleClass = "buttons"/> &nbsp &nbsp &nbsp; <br/><br/><br/><br/> --%>
		<h:commandButton type="submit" value="Data Analysis"
				 action="execute.jsp" styleClass = "buttons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="Export File"
				 action="#{actionBean.writeToFile}" styleClass = "buttons"/>
	</h:form>
	</div>
</f:view>
</body>
</html>