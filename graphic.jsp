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
</style>
<body>
<f:view>
<div>
		<h1 align="center">GRAPHIC DESCRIPTIVE STATISTICS</h1><hr>
	</div><br>
	
	<div align = "center">
	<h:form>
		<h:commandButton type="submit" value="BACK"
				 action="#{actionBeanGraph.goBack}" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="HOME"
				 action="mainMenu.jsp" styleClass = "navButtons"/> &nbsp &nbsp &nbsp;
		<h:commandButton type="submit" value="LOGOUT"
				 action="#{actionBean.closeConnection}" styleClass = "navButtons"/>
	</h:form>
	</div>
	<br>
	<h:form>
	<div align = "center">
	<h:outputLabel value="Select Chart " styleClass = "labels"/>
	<h:selectOneMenu value="#{actionBeanGraph.chartType}"  styleClass = "userInputDropdown" onchange="submit()"
							valueChangeListener = "#{actionBeanGraph.getColumns}" immediate = "true">
		<f:selectItem itemValue="0" itemLabel="Select Chart" />

		<f:selectItem itemValue="1" itemLabel="ScatterPlot" />
		<f:selectItem itemValue="2" itemLabel="Histogram" />
	</h:selectOneMenu>
	

	
	
	<h:outputLabel value="Select Predictor " rendered = "#{actionBeanGraph.scatterPlotSelected }" styleClass = "labels"/>
	<h:selectOneListbox  styleClass="listBox" rendered = "#{actionBeanGraph.scatterPlotSelected }" value="#{actionBean.predictor}"  size = "8">
		<f:selectItems value = "#{actionBean.numColumnNames}" />
	</h:selectOneListbox>
	<h:outputLabel value="Select Response " rendered = "#{actionBeanGraph.scatterPlotSelected }" styleClass = "labels"/>
	<h:selectOneListbox  styleClass="listBox" rendered = "#{actionBeanGraph.scatterPlotSelected }" value="#{actionBean.response}"  size = "8">
		<f:selectItems value = "#{actionBean.numColumnNames}" />
	</h:selectOneListbox>

	<h:outputLabel value="Select Column for Histogram " rendered = "#{actionBeanGraph.histogramSelected }" styleClass = "labels"/>
	<h:selectOneListbox  styleClass="listBox" rendered = "#{actionBeanGraph.histogramSelected }" value="#{actionBean.hist}" size = "8">
		<f:selectItems value = "#{actionBean.numColumnNames}" />
	</h:selectOneListbox>
	

	</div><br><br>
	<div align = "center">
	<h:commandButton type="submit" value="Generate Chart"
				 action="#{actionBeanGraph.generateChart}" styleClass = "buttons"/>
	</div><br><br>
	<div style="background-attachment: scroll; overflow:auto;
                    		height:450px; background-repeat: repeat;" align="center" >
		<h:graphicImage url="#{actionBeanGraph.xyPath}" height="450" width="600" rendered = "#{actionBeanGraph.renderScatterPlot}" alt = "Scatter Plot"/>
		<h:graphicImage url="#{actionBeanGraph.xyPath}" height="450" width="600" rendered = "#{actionBeanGraph.renderHistogramPlot}" alt = "Histogram"/>
	</div>
	
	</h:form>

	
	
	
</f:view>
</body>
</html>