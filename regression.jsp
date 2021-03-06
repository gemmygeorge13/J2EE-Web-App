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
<div>
		<h1 align="center">REGRESSION ANALYSIS</h1><hr>
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
	<div align = "center">
	
	<h:form>
	<h:panelGrid columns = "4">
	<h:panelGroup style = "vertical-align: middle; padding: 20px 20px;">
	<h:outputLabel value = "Select Predictor -- >" styleClass = "labels"/>
	</h:panelGroup>
	<h:panelGroup style = "vertical-align: middle; padding: 20px 20px;">
	<h:selectOneListbox  styleClass="listBox" value="#{actionBean.predictor}"  size = "8">
		<f:selectItems value = "#{actionBean.numColumnNames}" />
	</h:selectOneListbox>
	</h:panelGroup>
	<h:panelGroup style = "vertical-align: middle; padding: 20px 20px;">
	<h:outputLabel value = "Select Response -- >" styleClass = "labels"/>
	</h:panelGroup>
	<h:panelGroup style = "vertical-align: middle; padding: 20px 20px;">
	<h:selectOneListbox  styleClass="listBox" value="#{actionBean.response}"  size = "8">
		<f:selectItems value = "#{actionBean.numColumnNames}" />
	</h:selectOneListbox>
	</h:panelGroup>
	</h:panelGrid>
	<br>
	<div align = "center">
	<h:commandButton type="submit" value="Perfrom Regression"
				 action= "#{actionBean.generateRegressionReport}" styleClass = "buttons"/>
	</div>
	
	</h:form>
	
	</div>
	<br>
	<div align = "center" style="background-attachment: scroll; overflow: auto; height: 250px; background-repeat: repeat; padding: 5px 5px; border: 2px groove green;">
				
	<h:outputText value="Final Regression Equation: "
	rendered="#{actionBean.renderRegressionResult}" styleClass = "outputText">
	</h:outputText> &#160;
	<h:outputText value="#{actionBean.regEquation}" rendered="#{actionBean.renderRegressionResult}" styleClass = "outputText">
	</h:outputText> <br /><br />
	<h:outputText value="Regression Model" rendered="#{actionBean.renderRegressionResult}" styleClass = "outputText"></h:outputText>
	<h:panelGrid columns="5" rendered="#{actionBean.renderRegressionResult}" border="1" >
	<h:outputText value="Predictor"  styleClass = "outputText"/>
	<h:outputText value="Co-efficient"  styleClass = "outputText"/>
	<h:outputText value="Standard Error Co-efficient"  styleClass = "outputText"/>
	<h:outputText value="T-Statistic"  styleClass = "outputText"/>
	<h:outputText value="P-Value"  styleClass = "outputText"/>
	<h:outputText value="Constant"  styleClass = "outputText"/>
	<h:outputText value="#{actionBean.intercept}"  styleClass = "outputText"/>
	<h:outputText value="#{actionBean.interceptStdErr}"  styleClass = "outputText"/>
	<h:outputText value="#{actionBean.tStatistic }"  styleClass = "outputText"/>
	<h:outputText value="#{actionBean.interceptPValue }"  styleClass = "outputText"/>
	<h:outputText value="#{actionBean.predictor}"  styleClass = "outputText"/>
	<h:outputText value="#{actionBean.slope}" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.slopeStdErr}" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.tStatPred }" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.pValuePred }" styleClass = "outputText"/>
	</h:panelGrid> <br/> <br/>
	<h:panelGrid columns="2" rendered="#{actionBean.renderRegressionResult}" border="1">
	<h:outputText value="Model Standard Error:" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.stdErrorM}" styleClass = "outputText"/>
	<h:outputText value="R Square(Co-efficient of Determination)" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.rSquare}" styleClass = "outputText"/>
	<h:outputText value="Adjusted R Square(Co-efficient of Determination)" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.rSquareAdj}" styleClass = "outputText"/>
	</h:panelGrid> <br/> <br/>
	<h:outputText value="Analysis of Variance" rendered="#{actionBean.renderRegressionResult}" styleClass = "outputText"/> <br/>
	<h:panelGrid columns="6" rendered="#{actionBean.renderRegressionResult}" border="1" >
	<h:outputText value="Source" styleClass = "outputText"/>
	<h:outputText value="Degrees of Freedom(DF)" styleClass = "outputText"/>
	<h:outputText value="Sum of Squares" styleClass = "outputText"/>
	<h:outputText value="Mean of Squares" styleClass = "outputText"/>
	<h:outputText value="F-Statistic" styleClass = "outputText"/>
	<h:outputText value="P-Value" styleClass = "outputText"/>
	<h:outputText value="Regression" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.predictorDegreesFreedom}" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.regSumSquares}" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.meanSquare }" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.fValue }" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.pValue}" styleClass = "outputText"/>
	<h:outputText value="Residual Error" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.residualErrorDegreesFreedom}" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.sumSquaredErr }" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.meanSquareErr }" styleClass = "outputText"/>
	<h:outputText value="" styleClass = "outputText"/>
	<h:outputText value="" styleClass = "outputText"/>
	<h:outputText value="Total" styleClass = "outputText"/>
	<h:outputText value="#{actionBean.totalDegreesFreedom}" styleClass = "outputText"/>
	</h:panelGrid>
	
	</div>
</f:view>
</body>
</html>