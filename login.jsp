<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<title>Login Page</title>
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
</style>
<body>

<f:view>
	<div>
		<h1 align="center">IDS 517 - Fall '18 g315 - EAD PROJECT</h1><hr>
	</div>
	<div align = "center"></br>
<%-- 	
	<h:form target="_blank">
			<h:commandButton type="submit" value="User's Guide"
				 action= "/Documents/Help.pdf" styleClass = "buttons"/> &nbsp &nbsp &nbsp;
			<h:commandButton type="submit" value="Programmer's Guide"
				 action= "/Documents/S18T27.png" styleClass = "buttons"/>
	</h:form> 
	--%>
	
	</div>
	</br>
		
		<h:form>
			<div align="center" >
			<br /><br />
			<h:panelGrid columns="2" styleClass = "loginPanel">
			<h:outputLabel value="Username" styleClass = "labels"/>
			<h:inputText id="userName" value="#{actionBean.dbAccessBean.userBean.userName}" styleClass = "userInput"/>
			<h:outputLabel value="Password" styleClass = "labels"/>
			<h:inputSecret id="password" value="#{actionBean.dbAccessBean.userBean.password}" styleClass = "userInput"/>
			<h:outputLabel value="Database" styleClass = "labels"/>
			<h:selectOneMenu value="#{actionBean.dbAccessBean.userBean.database}"  styleClass = "userInputDropdown">
				<f:selectItem itemValue="MySql" itemLabel="MySql" />
				<f:selectItem itemValue="Oracle" itemLabel="Oracle" />
				<f:selectItem itemValue="DB2" itemLabel="DB2" />
			</h:selectOneMenu>
			<h:outputLabel value="Host" styleClass = "labels"/>
			<h:selectOneMenu
				value="#{actionBean.dbAccessBean.userBean.host}"  styleClass = "userInputDropdown">
								<f:selectItem itemValue="131.193.209.68" itemLabel="Server 68" />
								<f:selectItem itemValue="131.193.209.69" itemLabel="Server 69" />
							
			</h:selectOneMenu>
			<h:outputLabel value="Schema" styleClass = "labels"/>
			<h:inputText id="schema" value="#{actionBean.dbAccessBean.userBean.schema}" styleClass = "userInput"/><br />
			<br /><br /><br />
			<h:commandButton type="submit" value="Login"
				 action="#{actionBean.connect}" styleClass = "buttons"/>
				 
			</h:panelGrid><br />
				<h:message for="fileUpload" style="color:red;"
					errorClass="errorMessage" rendered="#{dbAccessBean.errorMessageRendered}" />
				<br />
			
			</div>
			<br/><br/>
			<div align = "center">
			<h:commandButton value="Team Details" type="submit" styleClass = "buttons" action = "about.jsp"/>
			</div>
			<h:messages></h:messages>
		</h:form>
</f:view>
</body>
</html>